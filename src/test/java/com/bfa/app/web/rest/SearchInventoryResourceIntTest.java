package com.bfa.app.web.rest;

import com.bfa.app.BfalegacyApp;

import com.bfa.app.domain.SearchInventory;
import com.bfa.app.repository.SearchInventoryRepository;
import com.bfa.app.service.SearchInventoryService;
import com.bfa.app.service.dto.SearchInventoryDTO;
import com.bfa.app.service.mapper.SearchInventoryMapper;

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
 * Test class for the SearchInventoryResource REST controller.
 *
 * @see SearchInventoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BfalegacyApp.class)
public class SearchInventoryResourceIntTest {

    private static final Integer DEFAULT_COUNT = 1;
    private static final Integer UPDATED_COUNT = 2;

    @Inject
    private SearchInventoryRepository searchInventoryRepository;

    @Inject
    private SearchInventoryMapper searchInventoryMapper;

    @Inject
    private SearchInventoryService searchInventoryService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSearchInventoryMockMvc;

    private SearchInventory searchInventory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SearchInventoryResource searchInventoryResource = new SearchInventoryResource();
        ReflectionTestUtils.setField(searchInventoryResource, "searchInventoryService", searchInventoryService);
        this.restSearchInventoryMockMvc = MockMvcBuilders.standaloneSetup(searchInventoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SearchInventory createEntity(EntityManager em) {
        SearchInventory searchInventory = new SearchInventory()
                .count(DEFAULT_COUNT);
        return searchInventory;
    }

    @Before
    public void initTest() {
        searchInventory = createEntity(em);
    }

    @Test
    @Transactional
    public void createSearchInventory() throws Exception {
        int databaseSizeBeforeCreate = searchInventoryRepository.findAll().size();

        // Create the SearchInventory
        SearchInventoryDTO searchInventoryDTO = searchInventoryMapper.searchInventoryToSearchInventoryDTO(searchInventory);

        restSearchInventoryMockMvc.perform(post("/api/search-inventories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(searchInventoryDTO)))
            .andExpect(status().isCreated());

        // Validate the SearchInventory in the database
        List<SearchInventory> searchInventoryList = searchInventoryRepository.findAll();
        assertThat(searchInventoryList).hasSize(databaseSizeBeforeCreate + 1);
        SearchInventory testSearchInventory = searchInventoryList.get(searchInventoryList.size() - 1);
        assertThat(testSearchInventory.getCount()).isEqualTo(DEFAULT_COUNT);
    }

    @Test
    @Transactional
    public void createSearchInventoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = searchInventoryRepository.findAll().size();

        // Create the SearchInventory with an existing ID
        SearchInventory existingSearchInventory = new SearchInventory();
        existingSearchInventory.setId(1L);
        SearchInventoryDTO existingSearchInventoryDTO = searchInventoryMapper.searchInventoryToSearchInventoryDTO(existingSearchInventory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSearchInventoryMockMvc.perform(post("/api/search-inventories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSearchInventoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SearchInventory> searchInventoryList = searchInventoryRepository.findAll();
        assertThat(searchInventoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSearchInventories() throws Exception {
        // Initialize the database
        searchInventoryRepository.saveAndFlush(searchInventory);

        // Get all the searchInventoryList
        restSearchInventoryMockMvc.perform(get("/api/search-inventories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(searchInventory.getId().intValue())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)));
    }

    @Test
    @Transactional
    public void getSearchInventory() throws Exception {
        // Initialize the database
        searchInventoryRepository.saveAndFlush(searchInventory);

        // Get the searchInventory
        restSearchInventoryMockMvc.perform(get("/api/search-inventories/{id}", searchInventory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(searchInventory.getId().intValue()))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT));
    }

    @Test
    @Transactional
    public void getNonExistingSearchInventory() throws Exception {
        // Get the searchInventory
        restSearchInventoryMockMvc.perform(get("/api/search-inventories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSearchInventory() throws Exception {
        // Initialize the database
        searchInventoryRepository.saveAndFlush(searchInventory);
        int databaseSizeBeforeUpdate = searchInventoryRepository.findAll().size();

        // Update the searchInventory
        SearchInventory updatedSearchInventory = searchInventoryRepository.findOne(searchInventory.getId());
        updatedSearchInventory
                .count(UPDATED_COUNT);
        SearchInventoryDTO searchInventoryDTO = searchInventoryMapper.searchInventoryToSearchInventoryDTO(updatedSearchInventory);

        restSearchInventoryMockMvc.perform(put("/api/search-inventories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(searchInventoryDTO)))
            .andExpect(status().isOk());

        // Validate the SearchInventory in the database
        List<SearchInventory> searchInventoryList = searchInventoryRepository.findAll();
        assertThat(searchInventoryList).hasSize(databaseSizeBeforeUpdate);
        SearchInventory testSearchInventory = searchInventoryList.get(searchInventoryList.size() - 1);
        assertThat(testSearchInventory.getCount()).isEqualTo(UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingSearchInventory() throws Exception {
        int databaseSizeBeforeUpdate = searchInventoryRepository.findAll().size();

        // Create the SearchInventory
        SearchInventoryDTO searchInventoryDTO = searchInventoryMapper.searchInventoryToSearchInventoryDTO(searchInventory);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSearchInventoryMockMvc.perform(put("/api/search-inventories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(searchInventoryDTO)))
            .andExpect(status().isCreated());

        // Validate the SearchInventory in the database
        List<SearchInventory> searchInventoryList = searchInventoryRepository.findAll();
        assertThat(searchInventoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSearchInventory() throws Exception {
        // Initialize the database
        searchInventoryRepository.saveAndFlush(searchInventory);
        int databaseSizeBeforeDelete = searchInventoryRepository.findAll().size();

        // Get the searchInventory
        restSearchInventoryMockMvc.perform(delete("/api/search-inventories/{id}", searchInventory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SearchInventory> searchInventoryList = searchInventoryRepository.findAll();
        assertThat(searchInventoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
