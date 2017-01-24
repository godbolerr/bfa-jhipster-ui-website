package com.bfa.app.service.impl;

import javax.inject.Inject;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfa.app.service.BookingRecordService;
import com.bfa.app.service.InventoryService;
import com.bfa.app.service.JmsProducer;
import com.bfa.app.service.dto.BookingRecordDTO;
import com.bfa.app.service.dto.InventoryDTO;
import com.bfa.app.service.dto.PassengerDTO;
import com.bfa.app.service.mapper.BookingRecordMapper;

/**
 * Service Implementation for managing BookingRecord.
 */
@Service
@Transactional
public class BookingRecordServiceImpl implements BookingRecordService {

	private final Logger log = LoggerFactory.getLogger(BookingRecordServiceImpl.class);
	
	@Inject
	private InventoryService invService;
	
	@Autowired
	@Qualifier("myRestTemplate")
	private OAuth2RestOperations restTemplate;
	
	@Inject
	private BookingRecordMapper bookingRecordMapper;
	
	
	

	@Autowired
	JmsProducer producer;
	/**
	 * Save a bookingRecord.
	 *
	 * @param bookingRecordDTO
	 *            the entity to save
	 * @return the persisted entity
	 */
	public BookingRecordDTO save(BookingRecordDTO bookingRecordDTO) {
		
		
		
		log.debug("Request to save BookingRecord : {}", bookingRecordDTO);

		
		JSONObject request = new JSONObject();
		try {
			request.put("origin", bookingRecordDTO.getOrigin());
			request.put("destination",bookingRecordDTO.getDestination());
			request.put("flightDate",bookingRecordDTO.getFlightDate());
			request.put("flightNumber",bookingRecordDTO.getFlightNumber());
			request.put("fare",bookingRecordDTO.getFare());
			request.put("status",bookingRecordDTO.getStatus());
			request.put("bookingDate","2017-01-01");
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);
		

		ResponseEntity<BookingRecordDTO> response = restTemplate.exchange(
				"http://localhost:11000/bookms/api/booking-records", HttpMethod.POST, entity,
				new ParameterizedTypeReference<BookingRecordDTO>() {
				});
		
		BookingRecordDTO bdto = response.getBody();
		
		if ( bdto != null ) {
		
//		BookingRecord bookingRecord = bookingRecordMapper.bookingRecordDTOToBookingRecord(bookingRecordDTO);

//		// populate passenger separately. Take care of mapper issue for one to
//		// many.
//		if (bookingRecordDTO.getPdto() != null) {
//			Passenger pas = new Passenger();
//			pas.setFirstName(bookingRecordDTO.getPdto().getFirstName());
//			pas.setLastName(bookingRecordDTO.getPdto().getLastName());
//			pas.setGender(bookingRecordDTO.getPdto().getGender());
//			bookingRecord.addBookPsr(pas);
//		}
		
		// Less 1 for the inventory.
		
			InventoryDTO invDto = invService.findByFlightNumberAndFlightDate(bdto.getFlightNumber(), bdto.getFlightDate());
			
			if ( invDto != null ) {
				invDto.setAvailable(invDto.getAvailable() - 1);
				invService.save(invDto);
			}
			
		}
		
//		// Send message to search for updating its inventory.....
//		
//		Map<String, Object> bookingDetails = new HashMap<String, Object>();
//		bookingDetails.put("FLIGHT_NUMBER", bookingRecord.getFlightNumber());
//		bookingDetails.put("FLIGHT_DATE", bookingRecord.getFlightDate());
//		bookingDetails.put("NEW_INVENTORY", invDto.getAvailable());
//		
//		producer.send(bookingDetails.toString());
		
		//BookingRecordDTO result = bookingRecordMapper.bookingRecordToBookingRecordDTO(bdto);
		return bdto;
	}

	/**
	 * Get all the bookingRecords.
	 * 
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<BookingRecordDTO> findAll(Pageable pageable) {
		return null;
		
//		log.debug("Request to get all BookingRecords");
//		Page<BookingRecord> result = bookingRecordRepository.findAll(pageable);
//		return result.map(bookingRecord -> bookingRecordMapper.bookingRecordToBookingRecordDTO(bookingRecord));
	}

	/**
	 * Get one bookingRecord by id.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	@Transactional(readOnly = true)
	public BookingRecordDTO findOne(Long id) {
		log.debug("Request to get BookingRecord : {}", id);
		PassengerDTO pdto = new PassengerDTO();
//		BookingRecord bookingRecord = bookingRecordRepository.findOne(id);
//		
//		if ( bookingRecord != null ) {
//			Set<Passenger> psr = bookingRecord.getBookPsrs(); 
//			if ( psr != null && psr.size() == 1 ){
//				Passenger[] pasArray = psr.toArray(new Passenger[psr.size()] );
//				pdto.setFirstName(pasArray[0].getFirstName());
//				pdto.setLastName(pasArray[0].getLastName());
//				pdto.setGender(pasArray[0].getGender());
//				
//			}
//		}
//		BookingRecordDTO bookingRecordDTO = bookingRecordMapper.bookingRecordToBookingRecordDTO(bookingRecord);
//		if ( pdto != null ) {
//			bookingRecordDTO.setPdto(pdto);
//		}
		return null;
	}

	/**
	 * Delete the bookingRecord by id.
	 *
	 * @param id
	 *            the id of the entity
	 */
	public void delete(Long id) {
		log.debug("Request to delete BookingRecord : {}", id);
	}
}
