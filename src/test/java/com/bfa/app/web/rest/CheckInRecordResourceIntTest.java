package com.bfa.app.web.rest;

import com.bfa.app.BfalegacyApp;

import com.bfa.app.domain.CheckInRecord;
import com.bfa.app.repository.CheckInRecordRepository;
import com.bfa.app.service.CheckInRecordService;
import com.bfa.app.service.dto.CheckInRecordDTO;
import com.bfa.app.service.mapper.CheckInRecordMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CheckInRecordResource REST controller.
 *
 * @see CheckInRecordResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BfalegacyApp.class)
public class CheckInRecordResourceIntTest {

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SEAT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SEAT_NUMBER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CHECK_IN_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CHECK_IN_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_FLIGHT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_FLIGHT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_FLIGHT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_FLIGHT_DATE = "BBBBBBBBBB";

    private static final Integer DEFAULT_BOOKING_ID = 1;
    private static final Integer UPDATED_BOOKING_ID = 2;

    @Inject
    private CheckInRecordRepository checkInRecordRepository;

    @Inject
    private CheckInRecordMapper checkInRecordMapper;

    @Inject
    private CheckInRecordService checkInRecordService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCheckInRecordMockMvc;

    private CheckInRecord checkInRecord;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CheckInRecordResource checkInRecordResource = new CheckInRecordResource();
        ReflectionTestUtils.setField(checkInRecordResource, "checkInRecordService", checkInRecordService);
        this.restCheckInRecordMockMvc = MockMvcBuilders.standaloneSetup(checkInRecordResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckInRecord createEntity(EntityManager em) {
        CheckInRecord checkInRecord = new CheckInRecord()
                .lastName(DEFAULT_LAST_NAME)
                .firstName(DEFAULT_FIRST_NAME)
                .seatNumber(DEFAULT_SEAT_NUMBER)
                .checkInTime(DEFAULT_CHECK_IN_TIME)
                .flightNumber(DEFAULT_FLIGHT_NUMBER)
                .flightDate(DEFAULT_FLIGHT_DATE)
                .bookingId(DEFAULT_BOOKING_ID);
        return checkInRecord;
    }

    @Before
    public void initTest() {
        checkInRecord = createEntity(em);
    }

    @Test
    @Transactional
    public void createCheckInRecord() throws Exception {
        int databaseSizeBeforeCreate = checkInRecordRepository.findAll().size();

        // Create the CheckInRecord
        CheckInRecordDTO checkInRecordDTO = checkInRecordMapper.checkInRecordToCheckInRecordDTO(checkInRecord);

        restCheckInRecordMockMvc.perform(post("/api/check-in-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkInRecordDTO)))
            .andExpect(status().isCreated());

        // Validate the CheckInRecord in the database
        List<CheckInRecord> checkInRecordList = checkInRecordRepository.findAll();
        assertThat(checkInRecordList).hasSize(databaseSizeBeforeCreate + 1);
        CheckInRecord testCheckInRecord = checkInRecordList.get(checkInRecordList.size() - 1);
        assertThat(testCheckInRecord.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCheckInRecord.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCheckInRecord.getSeatNumber()).isEqualTo(DEFAULT_SEAT_NUMBER);
        assertThat(testCheckInRecord.getCheckInTime()).isEqualTo(DEFAULT_CHECK_IN_TIME);
        assertThat(testCheckInRecord.getFlightNumber()).isEqualTo(DEFAULT_FLIGHT_NUMBER);
        assertThat(testCheckInRecord.getFlightDate()).isEqualTo(DEFAULT_FLIGHT_DATE);
        assertThat(testCheckInRecord.getBookingId()).isEqualTo(DEFAULT_BOOKING_ID);
    }

    @Test
    @Transactional
    public void createCheckInRecordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = checkInRecordRepository.findAll().size();

        // Create the CheckInRecord with an existing ID
        CheckInRecord existingCheckInRecord = new CheckInRecord();
        existingCheckInRecord.setId(1L);
        CheckInRecordDTO existingCheckInRecordDTO = checkInRecordMapper.checkInRecordToCheckInRecordDTO(existingCheckInRecord);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCheckInRecordMockMvc.perform(post("/api/check-in-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCheckInRecordDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CheckInRecord> checkInRecordList = checkInRecordRepository.findAll();
        assertThat(checkInRecordList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCheckInRecords() throws Exception {
        // Initialize the database
        checkInRecordRepository.saveAndFlush(checkInRecord);

        // Get all the checkInRecordList
        restCheckInRecordMockMvc.perform(get("/api/check-in-records?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkInRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].seatNumber").value(hasItem(DEFAULT_SEAT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].checkInTime").value(hasItem(DEFAULT_CHECK_IN_TIME.toString())))
            .andExpect(jsonPath("$.[*].flightNumber").value(hasItem(DEFAULT_FLIGHT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].flightDate").value(hasItem(DEFAULT_FLIGHT_DATE.toString())))
            .andExpect(jsonPath("$.[*].bookingId").value(hasItem(DEFAULT_BOOKING_ID)));
    }

    @Test
    @Transactional
    public void getCheckInRecord() throws Exception {
        // Initialize the database
        checkInRecordRepository.saveAndFlush(checkInRecord);

        // Get the checkInRecord
        restCheckInRecordMockMvc.perform(get("/api/check-in-records/{id}", checkInRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(checkInRecord.getId().intValue()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.seatNumber").value(DEFAULT_SEAT_NUMBER.toString()))
            .andExpect(jsonPath("$.checkInTime").value(DEFAULT_CHECK_IN_TIME.toString()))
            .andExpect(jsonPath("$.flightNumber").value(DEFAULT_FLIGHT_NUMBER.toString()))
            .andExpect(jsonPath("$.flightDate").value(DEFAULT_FLIGHT_DATE.toString()))
            .andExpect(jsonPath("$.bookingId").value(DEFAULT_BOOKING_ID));
    }

    @Test
    @Transactional
    public void getNonExistingCheckInRecord() throws Exception {
        // Get the checkInRecord
        restCheckInRecordMockMvc.perform(get("/api/check-in-records/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCheckInRecord() throws Exception {
        // Initialize the database
        checkInRecordRepository.saveAndFlush(checkInRecord);
        int databaseSizeBeforeUpdate = checkInRecordRepository.findAll().size();

        // Update the checkInRecord
        CheckInRecord updatedCheckInRecord = checkInRecordRepository.findOne(checkInRecord.getId());
        updatedCheckInRecord
                .lastName(UPDATED_LAST_NAME)
                .firstName(UPDATED_FIRST_NAME)
                .seatNumber(UPDATED_SEAT_NUMBER)
                .checkInTime(UPDATED_CHECK_IN_TIME)
                .flightNumber(UPDATED_FLIGHT_NUMBER)
                .flightDate(UPDATED_FLIGHT_DATE)
                .bookingId(UPDATED_BOOKING_ID);
        CheckInRecordDTO checkInRecordDTO = checkInRecordMapper.checkInRecordToCheckInRecordDTO(updatedCheckInRecord);

        restCheckInRecordMockMvc.perform(put("/api/check-in-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkInRecordDTO)))
            .andExpect(status().isOk());

        // Validate the CheckInRecord in the database
        List<CheckInRecord> checkInRecordList = checkInRecordRepository.findAll();
        assertThat(checkInRecordList).hasSize(databaseSizeBeforeUpdate);
        CheckInRecord testCheckInRecord = checkInRecordList.get(checkInRecordList.size() - 1);
        assertThat(testCheckInRecord.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCheckInRecord.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCheckInRecord.getSeatNumber()).isEqualTo(UPDATED_SEAT_NUMBER);
        assertThat(testCheckInRecord.getCheckInTime()).isEqualTo(UPDATED_CHECK_IN_TIME);
        assertThat(testCheckInRecord.getFlightNumber()).isEqualTo(UPDATED_FLIGHT_NUMBER);
        assertThat(testCheckInRecord.getFlightDate()).isEqualTo(UPDATED_FLIGHT_DATE);
        assertThat(testCheckInRecord.getBookingId()).isEqualTo(UPDATED_BOOKING_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingCheckInRecord() throws Exception {
        int databaseSizeBeforeUpdate = checkInRecordRepository.findAll().size();

        // Create the CheckInRecord
        CheckInRecordDTO checkInRecordDTO = checkInRecordMapper.checkInRecordToCheckInRecordDTO(checkInRecord);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCheckInRecordMockMvc.perform(put("/api/check-in-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkInRecordDTO)))
            .andExpect(status().isCreated());

        // Validate the CheckInRecord in the database
        List<CheckInRecord> checkInRecordList = checkInRecordRepository.findAll();
        assertThat(checkInRecordList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCheckInRecord() throws Exception {
        // Initialize the database
        checkInRecordRepository.saveAndFlush(checkInRecord);
        int databaseSizeBeforeDelete = checkInRecordRepository.findAll().size();

        // Get the checkInRecord
        restCheckInRecordMockMvc.perform(delete("/api/check-in-records/{id}", checkInRecord.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CheckInRecord> checkInRecordList = checkInRecordRepository.findAll();
        assertThat(checkInRecordList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
