package com.bfa.app.web.rest;

import com.bfa.app.BfalegacyApp;

import com.bfa.app.domain.SearchFlight;
import com.bfa.app.repository.SearchFlightRepository;
import com.bfa.app.service.SearchFlightService;
import com.bfa.app.service.dto.SearchFlightDTO;
import com.bfa.app.service.mapper.SearchFlightMapper;

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
 * Test class for the SearchFlightResource REST controller.
 *
 * @see SearchFlightResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BfalegacyApp.class)
public class SearchFlightResourceIntTest {

    private static final String DEFAULT_FLIGHT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_FLIGHT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGIN = "AAAAAAAAAA";
    private static final String UPDATED_ORIGIN = "BBBBBBBBBB";

    private static final String DEFAULT_DESTINATION = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATION = "BBBBBBBBBB";

    private static final String DEFAULT_FLIGHT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_FLIGHT_DATE = "BBBBBBBBBB";

    @Inject
    private SearchFlightRepository searchFlightRepository;

    @Inject
    private SearchFlightMapper searchFlightMapper;

    @Inject
    private SearchFlightService searchFlightService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSearchFlightMockMvc;

    private SearchFlight searchFlight;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SearchFlightResource searchFlightResource = new SearchFlightResource();
        ReflectionTestUtils.setField(searchFlightResource, "searchFlightService", searchFlightService);
        this.restSearchFlightMockMvc = MockMvcBuilders.standaloneSetup(searchFlightResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SearchFlight createEntity(EntityManager em) {
        SearchFlight searchFlight = new SearchFlight()
                .flightNumber(DEFAULT_FLIGHT_NUMBER)
                .origin(DEFAULT_ORIGIN)
                .destination(DEFAULT_DESTINATION)
                .flightDate(DEFAULT_FLIGHT_DATE);
        return searchFlight;
    }

    @Before
    public void initTest() {
        searchFlight = createEntity(em);
    }

    @Test
    @Transactional
    public void createSearchFlight() throws Exception {
        int databaseSizeBeforeCreate = searchFlightRepository.findAll().size();

        // Create the SearchFlight
        SearchFlightDTO searchFlightDTO = searchFlightMapper.searchFlightToSearchFlightDTO(searchFlight);

        restSearchFlightMockMvc.perform(post("/api/search-flights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(searchFlightDTO)))
            .andExpect(status().isCreated());

        // Validate the SearchFlight in the database
        List<SearchFlight> searchFlightList = searchFlightRepository.findAll();
        assertThat(searchFlightList).hasSize(databaseSizeBeforeCreate + 1);
        SearchFlight testSearchFlight = searchFlightList.get(searchFlightList.size() - 1);
        assertThat(testSearchFlight.getFlightNumber()).isEqualTo(DEFAULT_FLIGHT_NUMBER);
        assertThat(testSearchFlight.getOrigin()).isEqualTo(DEFAULT_ORIGIN);
        assertThat(testSearchFlight.getDestination()).isEqualTo(DEFAULT_DESTINATION);
        assertThat(testSearchFlight.getFlightDate()).isEqualTo(DEFAULT_FLIGHT_DATE);
    }

    @Test
    @Transactional
    public void createSearchFlightWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = searchFlightRepository.findAll().size();

        // Create the SearchFlight with an existing ID
        SearchFlight existingSearchFlight = new SearchFlight();
        existingSearchFlight.setId(1L);
        SearchFlightDTO existingSearchFlightDTO = searchFlightMapper.searchFlightToSearchFlightDTO(existingSearchFlight);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSearchFlightMockMvc.perform(post("/api/search-flights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSearchFlightDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SearchFlight> searchFlightList = searchFlightRepository.findAll();
        assertThat(searchFlightList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSearchFlights() throws Exception {
        // Initialize the database
        searchFlightRepository.saveAndFlush(searchFlight);

        // Get all the searchFlightList
        restSearchFlightMockMvc.perform(get("/api/search-flights?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(searchFlight.getId().intValue())))
            .andExpect(jsonPath("$.[*].flightNumber").value(hasItem(DEFAULT_FLIGHT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].origin").value(hasItem(DEFAULT_ORIGIN.toString())))
            .andExpect(jsonPath("$.[*].destination").value(hasItem(DEFAULT_DESTINATION.toString())))
            .andExpect(jsonPath("$.[*].flightDate").value(hasItem(DEFAULT_FLIGHT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getSearchFlight() throws Exception {
        // Initialize the database
        searchFlightRepository.saveAndFlush(searchFlight);

        // Get the searchFlight
        restSearchFlightMockMvc.perform(get("/api/search-flights/{id}", searchFlight.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(searchFlight.getId().intValue()))
            .andExpect(jsonPath("$.flightNumber").value(DEFAULT_FLIGHT_NUMBER.toString()))
            .andExpect(jsonPath("$.origin").value(DEFAULT_ORIGIN.toString()))
            .andExpect(jsonPath("$.destination").value(DEFAULT_DESTINATION.toString()))
            .andExpect(jsonPath("$.flightDate").value(DEFAULT_FLIGHT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSearchFlight() throws Exception {
        // Get the searchFlight
        restSearchFlightMockMvc.perform(get("/api/search-flights/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSearchFlight() throws Exception {
        // Initialize the database
        searchFlightRepository.saveAndFlush(searchFlight);
        int databaseSizeBeforeUpdate = searchFlightRepository.findAll().size();

        // Update the searchFlight
        SearchFlight updatedSearchFlight = searchFlightRepository.findOne(searchFlight.getId());
        updatedSearchFlight
                .flightNumber(UPDATED_FLIGHT_NUMBER)
                .origin(UPDATED_ORIGIN)
                .destination(UPDATED_DESTINATION)
                .flightDate(UPDATED_FLIGHT_DATE);
        SearchFlightDTO searchFlightDTO = searchFlightMapper.searchFlightToSearchFlightDTO(updatedSearchFlight);

        restSearchFlightMockMvc.perform(put("/api/search-flights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(searchFlightDTO)))
            .andExpect(status().isOk());

        // Validate the SearchFlight in the database
        List<SearchFlight> searchFlightList = searchFlightRepository.findAll();
        assertThat(searchFlightList).hasSize(databaseSizeBeforeUpdate);
        SearchFlight testSearchFlight = searchFlightList.get(searchFlightList.size() - 1);
        assertThat(testSearchFlight.getFlightNumber()).isEqualTo(UPDATED_FLIGHT_NUMBER);
        assertThat(testSearchFlight.getOrigin()).isEqualTo(UPDATED_ORIGIN);
        assertThat(testSearchFlight.getDestination()).isEqualTo(UPDATED_DESTINATION);
        assertThat(testSearchFlight.getFlightDate()).isEqualTo(UPDATED_FLIGHT_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingSearchFlight() throws Exception {
        int databaseSizeBeforeUpdate = searchFlightRepository.findAll().size();

        // Create the SearchFlight
        SearchFlightDTO searchFlightDTO = searchFlightMapper.searchFlightToSearchFlightDTO(searchFlight);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSearchFlightMockMvc.perform(put("/api/search-flights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(searchFlightDTO)))
            .andExpect(status().isCreated());

        // Validate the SearchFlight in the database
        List<SearchFlight> searchFlightList = searchFlightRepository.findAll();
        assertThat(searchFlightList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSearchFlight() throws Exception {
        // Initialize the database
        searchFlightRepository.saveAndFlush(searchFlight);
        int databaseSizeBeforeDelete = searchFlightRepository.findAll().size();

        // Get the searchFlight
        restSearchFlightMockMvc.perform(delete("/api/search-flights/{id}", searchFlight.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SearchFlight> searchFlightList = searchFlightRepository.findAll();
        assertThat(searchFlightList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
