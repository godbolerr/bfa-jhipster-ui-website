package com.bfa.app.web.rest;

import com.bfa.app.BfalegacyApp;

import com.bfa.app.domain.SearchFares;
import com.bfa.app.repository.SearchFaresRepository;
import com.bfa.app.service.SearchFaresService;
import com.bfa.app.service.dto.SearchFaresDTO;
import com.bfa.app.service.mapper.SearchFaresMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SearchFaresResource REST controller.
 *
 * @see SearchFaresResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BfalegacyApp.class)
public class SearchFaresResourceIntTest {

    private static final String DEFAULT_FARE = "AAAAAAAAAA";
    private static final String UPDATED_FARE = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY = "BBBBBBBBBB";

    @Inject
    private SearchFaresRepository searchFaresRepository;

    @Inject
    private SearchFaresMapper searchFaresMapper;

    @Inject
    private SearchFaresService searchFaresService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSearchFaresMockMvc;

    private SearchFares searchFares;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SearchFaresResource searchFaresResource = new SearchFaresResource();
        ReflectionTestUtils.setField(searchFaresResource, "searchFaresService", searchFaresService);
        this.restSearchFaresMockMvc = MockMvcBuilders.standaloneSetup(searchFaresResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SearchFares createEntity(EntityManager em) {
        SearchFares searchFares = new SearchFares()
                .fare(DEFAULT_FARE)
                .currency(DEFAULT_CURRENCY);
        return searchFares;
    }

    @Before
    public void initTest() {
        searchFares = createEntity(em);
    }

    @Test
    @Transactional
    public void createSearchFares() throws Exception {
        int databaseSizeBeforeCreate = searchFaresRepository.findAll().size();

        // Create the SearchFares
        SearchFaresDTO searchFaresDTO = searchFaresMapper.searchFaresToSearchFaresDTO(searchFares);

        restSearchFaresMockMvc.perform(post("/api/search-fares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(searchFaresDTO)))
            .andExpect(status().isCreated());

        // Validate the SearchFares in the database
        List<SearchFares> searchFaresList = searchFaresRepository.findAll();
        assertThat(searchFaresList).hasSize(databaseSizeBeforeCreate + 1);
        SearchFares testSearchFares = searchFaresList.get(searchFaresList.size() - 1);
        assertThat(testSearchFares.getFare()).isEqualTo(DEFAULT_FARE);
        assertThat(testSearchFares.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
    }

    @Test
    @Transactional
    public void createSearchFaresWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = searchFaresRepository.findAll().size();

        // Create the SearchFares with an existing ID
        SearchFares existingSearchFares = new SearchFares();
        existingSearchFares.setId(1L);
        SearchFaresDTO existingSearchFaresDTO = searchFaresMapper.searchFaresToSearchFaresDTO(existingSearchFares);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSearchFaresMockMvc.perform(post("/api/search-fares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSearchFaresDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SearchFares> searchFaresList = searchFaresRepository.findAll();
        assertThat(searchFaresList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSearchFares() throws Exception {
        // Initialize the database
        searchFaresRepository.saveAndFlush(searchFares);

        // Get all the searchFaresList
        restSearchFaresMockMvc.perform(get("/api/search-fares?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(searchFares.getId().intValue())))
            .andExpect(jsonPath("$.[*].fare").value(hasItem(DEFAULT_FARE.toString())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())));
    }

    @Test
    @Transactional
    public void getSearchFares() throws Exception {
        // Initialize the database
        searchFaresRepository.saveAndFlush(searchFares);

        // Get the searchFares
        restSearchFaresMockMvc.perform(get("/api/search-fares/{id}", searchFares.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(searchFares.getId().intValue()))
            .andExpect(jsonPath("$.fare").value(DEFAULT_FARE.toString()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSearchFares() throws Exception {
        // Get the searchFares
        restSearchFaresMockMvc.perform(get("/api/search-fares/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSearchFares() throws Exception {
        // Initialize the database
        searchFaresRepository.saveAndFlush(searchFares);
        int databaseSizeBeforeUpdate = searchFaresRepository.findAll().size();

        // Update the searchFares
        SearchFares updatedSearchFares = searchFaresRepository.findOne(searchFares.getId());
        updatedSearchFares
                .fare(UPDATED_FARE)
                .currency(UPDATED_CURRENCY);
        SearchFaresDTO searchFaresDTO = searchFaresMapper.searchFaresToSearchFaresDTO(updatedSearchFares);

        restSearchFaresMockMvc.perform(put("/api/search-fares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(searchFaresDTO)))
            .andExpect(status().isOk());

        // Validate the SearchFares in the database
        List<SearchFares> searchFaresList = searchFaresRepository.findAll();
        assertThat(searchFaresList).hasSize(databaseSizeBeforeUpdate);
        SearchFares testSearchFares = searchFaresList.get(searchFaresList.size() - 1);
        assertThat(testSearchFares.getFare()).isEqualTo(UPDATED_FARE);
        assertThat(testSearchFares.getCurrency()).isEqualTo(UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    public void updateNonExistingSearchFares() throws Exception {
        int databaseSizeBeforeUpdate = searchFaresRepository.findAll().size();

        // Create the SearchFares
        SearchFaresDTO searchFaresDTO = searchFaresMapper.searchFaresToSearchFaresDTO(searchFares);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSearchFaresMockMvc.perform(put("/api/search-fares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(searchFaresDTO)))
            .andExpect(status().isCreated());

        // Validate the SearchFares in the database
        List<SearchFares> searchFaresList = searchFaresRepository.findAll();
        assertThat(searchFaresList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSearchFares() throws Exception {
        // Initialize the database
        searchFaresRepository.saveAndFlush(searchFares);
        int databaseSizeBeforeDelete = searchFaresRepository.findAll().size();

        // Get the searchFares
        restSearchFaresMockMvc.perform(delete("/api/search-fares/{id}", searchFares.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SearchFares> searchFaresList = searchFaresRepository.findAll();
        assertThat(searchFaresList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
