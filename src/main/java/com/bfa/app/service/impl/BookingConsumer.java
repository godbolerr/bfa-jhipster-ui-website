package com.bfa.app.service.impl;

import javax.inject.Inject;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.bfa.app.service.SearchFlightService;
import com.bfa.app.service.dto.SearchFlightDTO;

@Component
public class BookingConsumer {
	
	@Inject
	SearchFlightService sfService;

	@JmsListener(destination = "${jms.queue.destination}")
	public void receiveQueue(String text) {
		System.out.println(text);
		
		// Parse this text into json.
		SearchFlightDTO dto = new SearchFlightDTO();
		
		String inventory= null;
		
		try {
			JSONObject obj = new JSONObject(text);
			String flightNumber = obj.getString("FLIGHT_NUMBER");
			dto.setFlightNumber(flightNumber);
			String flightDate = obj.getString("FLIGHT_DATE");
			
			inventory = obj.getString("INVENTORY");
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//TODO Get the flight and update its fare.
		//TODO Not done right now, to be done in future
		sfService.find(dto);
	}
}
