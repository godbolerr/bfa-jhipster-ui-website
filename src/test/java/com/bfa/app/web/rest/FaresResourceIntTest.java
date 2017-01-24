package com.bfa.app.web.rest;

import com.bfa.app.BfalegacyApp;

import com.bfa.app.domain.Fares;
import com.bfa.app.repository.FaresRepository;
import com.bfa.app.service.FaresService;
import com.bfa.app.service.dto.FaresDTO;
import com.bfa.app.service.mapper.FaresMapper;

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
 * Test class for the FaresResource REST controller.
 *
 * @see FaresResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BfalegacyApp.class)
public class FaresResourceIntTest {

    private static final String DEFAULT_FLIGHT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_FLIGHT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_FLIGHT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_FLIGHT_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_FARE = "AAAAAAAAAA";
    private static final String UPDATED_FARE = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY = "BBBBBBBBBB";

    @Inject
    private FaresRepository faresRepository;

    @Inject
    private FaresMapper faresMapper;

    @Inject
    private FaresService faresService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restFaresMockMvc;

    private Fares fares;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FaresResource faresResource = new FaresResource();
        ReflectionTestUtils.setField(faresResource, "faresService", faresService);
        this.restFaresMockMvc = MockMvcBuilders.standaloneSetup(faresResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fares createEntity(EntityManager em) {
        Fares fares = new Fares()
                .flightNumber(DEFAULT_FLIGHT_NUMBER)
                .flightDate(DEFAULT_FLIGHT_DATE)
                .fare(DEFAULT_FARE)
                .currency(DEFAULT_CURRENCY);
        return fares;
    }

    @Before
    public void initTest() {
        fares = createEntity(em);
    }

    @Test
    @Transactional
    public void createFares() throws Exception {
        int databaseSizeBeforeCreate = faresRepository.findAll().size();

        // Create the Fares
        FaresDTO faresDTO = faresMapper.faresToFaresDTO(fares);

        restFaresMockMvc.perform(post("/api/fares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faresDTO)))
            .andExpect(status().isCreated());

        // Validate the Fares in the database
        List<Fares> faresList = faresRepository.findAll();
        assertThat(faresList).hasSize(databaseSizeBeforeCreate + 1);
        Fares testFares = faresList.get(faresList.size() - 1);
        assertThat(testFares.getFlightNumber()).isEqualTo(DEFAULT_FLIGHT_NUMBER);
        assertThat(testFares.getFlightDate()).isEqualTo(DEFAULT_FLIGHT_DATE);
        assertThat(testFares.getFare()).isEqualTo(DEFAULT_FARE);
        assertThat(testFares.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
    }

    @Test
    @Transactional
    public void createFaresWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = faresRepository.findAll().size();

        // Create the Fares with an existing ID
        Fares existingFares = new Fares();
        existingFares.setId(1L);
        FaresDTO existingFaresDTO = faresMapper.faresToFaresDTO(existingFares);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFaresMockMvc.perform(post("/api/fares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingFaresDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Fares> faresList = faresRepository.findAll();
        assertThat(faresList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFares() throws Exception {
        // Initialize the database
        faresRepository.saveAndFlush(fares);

        // Get all the faresList
        restFaresMockMvc.perform(get("/api/fares?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fares.getId().intValue())))
            .andExpect(jsonPath("$.[*].flightNumber").value(hasItem(DEFAULT_FLIGHT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].flightDate").value(hasItem(DEFAULT_FLIGHT_DATE.toString())))
            .andExpect(jsonPath("$.[*].fare").value(hasItem(DEFAULT_FARE.toString())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())));
    }

    @Test
    @Transactional
    public void getFares() throws Exception {
        // Initialize the database
        faresRepository.saveAndFlush(fares);

        // Get the fares
        restFaresMockMvc.perform(get("/api/fares/{id}", fares.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fares.getId().intValue()))
            .andExpect(jsonPath("$.flightNumber").value(DEFAULT_FLIGHT_NUMBER.toString()))
            .andExpect(jsonPath("$.flightDate").value(DEFAULT_FLIGHT_DATE.toString()))
            .andExpect(jsonPath("$.fare").value(DEFAULT_FARE.toString()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFares() throws Exception {
        // Get the fares
        restFaresMockMvc.perform(get("/api/fares/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFares() throws Exception {
        // Initialize the database
        faresRepository.saveAndFlush(fares);
        int databaseSizeBeforeUpdate = faresRepository.findAll().size();

        // Update the fares
        Fares updatedFares = faresRepository.findOne(fares.getId());
        updatedFares
                .flightNumber(UPDATED_FLIGHT_NUMBER)
                .flightDate(UPDATED_FLIGHT_DATE)
                .fare(UPDATED_FARE)
                .currency(UPDATED_CURRENCY);
        FaresDTO faresDTO = faresMapper.faresToFaresDTO(updatedFares);

        restFaresMockMvc.perform(put("/api/fares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faresDTO)))
            .andExpect(status().isOk());

        // Validate the Fares in the database
        List<Fares> faresList = faresRepository.findAll();
        assertThat(faresList).hasSize(databaseSizeBeforeUpdate);
        Fares testFares = faresList.get(faresList.size() - 1);
        assertThat(testFares.getFlightNumber()).isEqualTo(UPDATED_FLIGHT_NUMBER);
        assertThat(testFares.getFlightDate()).isEqualTo(UPDATED_FLIGHT_DATE);
        assertThat(testFares.getFare()).isEqualTo(UPDATED_FARE);
        assertThat(testFares.getCurrency()).isEqualTo(UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    public void updateNonExistingFares() throws Exception {
        int databaseSizeBeforeUpdate = faresRepository.findAll().size();

        // Create the Fares
        FaresDTO faresDTO = faresMapper.faresToFaresDTO(fares);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFaresMockMvc.perform(put("/api/fares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faresDTO)))
            .andExpect(status().isCreated());

        // Validate the Fares in the database
        List<Fares> faresList = faresRepository.findAll();
        assertThat(faresList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFares() throws Exception {
        // Initialize the database
        faresRepository.saveAndFlush(fares);
        int databaseSizeBeforeDelete = faresRepository.findAll().size();

        // Get the fares
        restFaresMockMvc.perform(delete("/api/fares/{id}", fares.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Fares> faresList = faresRepository.findAll();
        assertThat(faresList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
