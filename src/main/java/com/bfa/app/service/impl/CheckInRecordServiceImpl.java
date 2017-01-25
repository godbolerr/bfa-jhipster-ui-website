package com.bfa.app.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfa.app.domain.CheckInRecord;
import com.bfa.app.repository.CheckInRecordRepository;
import com.bfa.app.service.CheckInRecordService;
import com.bfa.app.service.dto.CheckInRecordDTO;
import com.bfa.app.service.dto.InventoryDTO;
import com.bfa.app.service.mapper.CheckInRecordMapper;

/**
 * Service Implementation for managing CheckInRecord.
 */
@Service
@Transactional
public class CheckInRecordServiceImpl implements CheckInRecordService {

	private final Logger log = LoggerFactory.getLogger(CheckInRecordServiceImpl.class);

	@Inject
	private CheckInRecordRepository checkInRecordRepository;

	@Inject
	private CheckInRecordMapper checkInRecordMapper;

	@Autowired
	@Qualifier("myRestTemplate")
	private OAuth2RestOperations restTemplate;

	/**
	 * Save a checkInRecord.
	 *
	 * @param checkInRecordDTO
	 *            the entity to save
	 * @return the persisted entity
	 */
	public CheckInRecordDTO save(CheckInRecordDTO checkInRecordDTO) {
		log.debug("Request to save CheckInRecord : {}", checkInRecordDTO);
		// CheckInRecord checkInRecord =
		// checkInRecordMapper.checkInRecordDTOToCheckInRecord(checkInRecordDTO);
		// checkInRecord = checkInRecordRepository.save(checkInRecord);
		// CheckInRecordDTO result =
		// checkInRecordMapper.checkInRecordToCheckInRecordDTO(checkInRecord);
		//

		JSONObject checkInRequest = new JSONObject();
		try {
			checkInRequest.put("bookingId", checkInRecordDTO.getBookingId());
			checkInRequest.put("flightNumber", checkInRecordDTO.getFlightNumber());
			checkInRequest.put("flightDate", checkInRecordDTO.getFlightDate());
			checkInRequest.put("firstName", "firstName");
			checkInRequest.put("lastName", "lastName");
			checkInRequest.put("seatNumber", "28C");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(checkInRequest.toString(), headers);

		ResponseEntity<CheckInRecordDTO> checkInRecordDtoResponse = restTemplate.exchange(
				"http://10.142.129.23:11000/checkinms/api/check-in-records", HttpMethod.POST, entity,
				new ParameterizedTypeReference<CheckInRecordDTO>() {
				});

		CheckInRecordDTO checkInRecordDto = checkInRecordDtoResponse.getBody();

		checkInRecordDto.setSeatNumber(checkInRecordDto.getSeatNumber() + ":" + checkInRecordDto.getId());

		log.debug(checkInRecordDto.toString());

		return checkInRecordDto;
	}

	/**
	 * Get all the checkInRecords.
	 * 
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public List<CheckInRecordDTO> findAll() {
		log.debug("Request to get all CheckInRecords");
		List<CheckInRecordDTO> result = checkInRecordRepository.findAll().stream()
				.map(checkInRecordMapper::checkInRecordToCheckInRecordDTO)
				.collect(Collectors.toCollection(LinkedList::new));

		return result;
	}

	/**
	 * Get one checkInRecord by id.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	@Transactional(readOnly = true)
	public CheckInRecordDTO findOne(Long id) {
		log.debug("Request to get CheckInRecord : {}", id);
		CheckInRecord checkInRecord = checkInRecordRepository.findOne(id);
		CheckInRecordDTO checkInRecordDTO = checkInRecordMapper.checkInRecordToCheckInRecordDTO(checkInRecord);
		return checkInRecordDTO;
	}

	/**
	 * Delete the checkInRecord by id.
	 *
	 * @param id
	 *            the id of the entity
	 */
	public void delete(Long id) {
		log.debug("Request to delete CheckInRecord : {}", id);
		checkInRecordRepository.delete(id);
	}
}
