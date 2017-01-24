package com.bfa.app.web.rest;



import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.test.context.junit4.SpringRunner;

import com.bfa.app.BfalegacyApp;

/**
 * Test class for the FaresResource REST controller.
 *
 * @see FaresResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BfalegacyApp.class)
public class Outh2Test {
	
	
	@Autowired
	@Qualifier("myRestTemplate")
	private OAuth2RestOperations restTemplate;



    @Before
    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        FaresResource faresResource = new FaresResource();
//        ReflectionTestUtils.setField(faresResource, "faresService", faresService);
//        this.restFaresMockMvc = MockMvcBuilders.standaloneSetup(faresResource)
//            .setCustomArgumentResolvers(pageableArgumentResolver)
//            .setMessageConverters(jacksonMessageConverter).build();
    }



    @Test
    public void getUsers() throws Exception {
    	
    	//System.out.println("Fetching users with rest template : " + restTemplate );
    	
    	Object obj = restTemplate.getForObject("http://localhost:11000/uaa/api/users", Object.class);
    	System.out.println("User list " + obj);
    }

}
