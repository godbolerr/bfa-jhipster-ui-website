package com.bfa.app.web.rest;

import com.bfa.app.BfalegacyApp;

import com.bfa.app.domain.BookingRecord;
import com.bfa.app.repository.BookingRecordRepository;
import com.bfa.app.service.BookingRecordService;
import com.bfa.app.service.dto.BookingRecordDTO;
import com.bfa.app.service.mapper.BookingRecordMapper;

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
 * Test class for the BookingRecordResource REST controller.
 *
 * @see BookingRecordResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BfalegacyApp.class)
public class BookingRecordResourceIntTest {

    private static final String DEFAULT_FLIGHT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_FLIGHT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGIN = "AAAAAAAAAA";
    private static final String UPDATED_ORIGIN = "BBBBBBBBBB";

    private static final String DEFAULT_DESTINATION = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATION = "BBBBBBBBBB";

    private static final String DEFAULT_FLIGHT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_FLIGHT_DATE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BOOKING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BOOKING_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_FARE = "AAAAAAAAAA";
    private static final String UPDATED_FARE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Inject
    private BookingRecordRepository bookingRecordRepository;

    @Inject
    private BookingRecordMapper bookingRecordMapper;

    @Inject
    private BookingRecordService bookingRecordService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restBookingRecordMockMvc;

    private BookingRecord bookingRecord;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BookingRecordResource bookingRecordResource = new BookingRecordResource();
        ReflectionTestUtils.setField(bookingRecordResource, "bookingRecordService", bookingRecordService);
        this.restBookingRecordMockMvc = MockMvcBuilders.standaloneSetup(bookingRecordResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookingRecord createEntity(EntityManager em) {
        BookingRecord bookingRecord = new BookingRecord()
                .flightNumber(DEFAULT_FLIGHT_NUMBER)
                .origin(DEFAULT_ORIGIN)
                .destination(DEFAULT_DESTINATION)
                .flightDate(DEFAULT_FLIGHT_DATE)
                .bookingDate(DEFAULT_BOOKING_DATE)
                .fare(DEFAULT_FARE)
                .status(DEFAULT_STATUS);
        return bookingRecord;
    }

    @Before
    public void initTest() {
        bookingRecord = createEntity(em);
    }

    @Test
    @Transactional
    public void createBookingRecord() throws Exception {
        int databaseSizeBeforeCreate = bookingRecordRepository.findAll().size();

        // Create the BookingRecord
        BookingRecordDTO bookingRecordDTO = bookingRecordMapper.bookingRecordToBookingRecordDTO(bookingRecord);

        restBookingRecordMockMvc.perform(post("/api/booking-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookingRecordDTO)))
            .andExpect(status().isCreated());

        // Validate the BookingRecord in the database
        List<BookingRecord> bookingRecordList = bookingRecordRepository.findAll();
        assertThat(bookingRecordList).hasSize(databaseSizeBeforeCreate + 1);
        BookingRecord testBookingRecord = bookingRecordList.get(bookingRecordList.size() - 1);
        assertThat(testBookingRecord.getFlightNumber()).isEqualTo(DEFAULT_FLIGHT_NUMBER);
        assertThat(testBookingRecord.getOrigin()).isEqualTo(DEFAULT_ORIGIN);
        assertThat(testBookingRecord.getDestination()).isEqualTo(DEFAULT_DESTINATION);
        assertThat(testBookingRecord.getFlightDate()).isEqualTo(DEFAULT_FLIGHT_DATE);
        assertThat(testBookingRecord.getBookingDate()).isEqualTo(DEFAULT_BOOKING_DATE);
        assertThat(testBookingRecord.getFare()).isEqualTo(DEFAULT_FARE);
        assertThat(testBookingRecord.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createBookingRecordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookingRecordRepository.findAll().size();

        // Create the BookingRecord with an existing ID
        BookingRecord existingBookingRecord = new BookingRecord();
        existingBookingRecord.setId(1L);
        BookingRecordDTO existingBookingRecordDTO = bookingRecordMapper.bookingRecordToBookingRecordDTO(existingBookingRecord);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookingRecordMockMvc.perform(post("/api/booking-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingBookingRecordDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BookingRecord> bookingRecordList = bookingRecordRepository.findAll();
        assertThat(bookingRecordList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBookingRecords() throws Exception {
        // Initialize the database
        bookingRecordRepository.saveAndFlush(bookingRecord);

        // Get all the bookingRecordList
        restBookingRecordMockMvc.perform(get("/api/booking-records?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookingRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].flightNumber").value(hasItem(DEFAULT_FLIGHT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].origin").value(hasItem(DEFAULT_ORIGIN.toString())))
            .andExpect(jsonPath("$.[*].destination").value(hasItem(DEFAULT_DESTINATION.toString())))
            .andExpect(jsonPath("$.[*].flightDate").value(hasItem(DEFAULT_FLIGHT_DATE.toString())))
            .andExpect(jsonPath("$.[*].bookingDate").value(hasItem(DEFAULT_BOOKING_DATE.toString())))
            .andExpect(jsonPath("$.[*].fare").value(hasItem(DEFAULT_FARE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getBookingRecord() throws Exception {
        // Initialize the database
        bookingRecordRepository.saveAndFlush(bookingRecord);

        // Get the bookingRecord
        restBookingRecordMockMvc.perform(get("/api/booking-records/{id}", bookingRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bookingRecord.getId().intValue()))
            .andExpect(jsonPath("$.flightNumber").value(DEFAULT_FLIGHT_NUMBER.toString()))
            .andExpect(jsonPath("$.origin").value(DEFAULT_ORIGIN.toString()))
            .andExpect(jsonPath("$.destination").value(DEFAULT_DESTINATION.toString()))
            .andExpect(jsonPath("$.flightDate").value(DEFAULT_FLIGHT_DATE.toString()))
            .andExpect(jsonPath("$.bookingDate").value(DEFAULT_BOOKING_DATE.toString()))
            .andExpect(jsonPath("$.fare").value(DEFAULT_FARE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBookingRecord() throws Exception {
        // Get the bookingRecord
        restBookingRecordMockMvc.perform(get("/api/booking-records/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBookingRecord() throws Exception {
        // Initialize the database
        bookingRecordRepository.saveAndFlush(bookingRecord);
        int databaseSizeBeforeUpdate = bookingRecordRepository.findAll().size();

        // Update the bookingRecord
        BookingRecord updatedBookingRecord = bookingRecordRepository.findOne(bookingRecord.getId());
        updatedBookingRecord
                .flightNumber(UPDATED_FLIGHT_NUMBER)
                .origin(UPDATED_ORIGIN)
                .destination(UPDATED_DESTINATION)
                .flightDate(UPDATED_FLIGHT_DATE)
                .bookingDate(UPDATED_BOOKING_DATE)
                .fare(UPDATED_FARE)
                .status(UPDATED_STATUS);
        BookingRecordDTO bookingRecordDTO = bookingRecordMapper.bookingRecordToBookingRecordDTO(updatedBookingRecord);

        restBookingRecordMockMvc.perform(put("/api/booking-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookingRecordDTO)))
            .andExpect(status().isOk());

        // Validate the BookingRecord in the database
        List<BookingRecord> bookingRecordList = bookingRecordRepository.findAll();
        assertThat(bookingRecordList).hasSize(databaseSizeBeforeUpdate);
        BookingRecord testBookingRecord = bookingRecordList.get(bookingRecordList.size() - 1);
        assertThat(testBookingRecord.getFlightNumber()).isEqualTo(UPDATED_FLIGHT_NUMBER);
        assertThat(testBookingRecord.getOrigin()).isEqualTo(UPDATED_ORIGIN);
        assertThat(testBookingRecord.getDestination()).isEqualTo(UPDATED_DESTINATION);
        assertThat(testBookingRecord.getFlightDate()).isEqualTo(UPDATED_FLIGHT_DATE);
        assertThat(testBookingRecord.getBookingDate()).isEqualTo(UPDATED_BOOKING_DATE);
        assertThat(testBookingRecord.getFare()).isEqualTo(UPDATED_FARE);
        assertThat(testBookingRecord.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingBookingRecord() throws Exception {
        int databaseSizeBeforeUpdate = bookingRecordRepository.findAll().size();

        // Create the BookingRecord
        BookingRecordDTO bookingRecordDTO = bookingRecordMapper.bookingRecordToBookingRecordDTO(bookingRecord);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBookingRecordMockMvc.perform(put("/api/booking-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookingRecordDTO)))
            .andExpect(status().isCreated());

        // Validate the BookingRecord in the database
        List<BookingRecord> bookingRecordList = bookingRecordRepository.findAll();
        assertThat(bookingRecordList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBookingRecord() throws Exception {
        // Initialize the database
        bookingRecordRepository.saveAndFlush(bookingRecord);
        int databaseSizeBeforeDelete = bookingRecordRepository.findAll().size();

        // Get the bookingRecord
        restBookingRecordMockMvc.perform(delete("/api/booking-records/{id}", bookingRecord.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BookingRecord> bookingRecordList = bookingRecordRepository.findAll();
        assertThat(bookingRecordList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
