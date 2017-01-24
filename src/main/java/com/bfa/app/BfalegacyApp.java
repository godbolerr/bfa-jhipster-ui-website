package com.bfa.app;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.MetricFilterAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.MetricRepositoryAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import com.bfa.app.config.Constants;
import com.bfa.app.config.DefaultProfileUtil;
import com.bfa.app.config.JHipsterProperties;
import com.bfa.app.service.SearchFlightService;
import com.bfa.app.service.dto.SearchFlightDTO;

@ComponentScan
@EnableAutoConfiguration(exclude = { MetricFilterAutoConfiguration.class, MetricRepositoryAutoConfiguration.class })
@EnableConfigurationProperties({ JHipsterProperties.class, LiquibaseProperties.class })
public class BfalegacyApp {

    private static final Logger log = LoggerFactory.getLogger(BfalegacyApp.class);

    @Inject
    private Environment env;
    
    @Inject
    private SearchFlightService searchFlightService;

    /**
     * Initializes bfalegacy.
     * <p>
     * Spring profiles can be configured with a program arguments --spring.profiles.active=your-active-profile
     * <p>
     * You can find more information on how profiles work with JHipster on <a href="http://jhipster.github.io/profiles/">http://jhipster.github.io/profiles/</a>.
     */
    @PostConstruct
    public void initApplication() {
        log.info("Running with Spring profile(s) : {}", Arrays.toString(env.getActiveProfiles()));
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(Constants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(Constants.SPRING_PROFILE_PRODUCTION)) {
            log.error("You have misconfigured your application! It should not run " +
                "with both the 'dev' and 'prod' profiles at the same time.");
        }
        if (activeProfiles.contains(Constants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(Constants.SPRING_PROFILE_CLOUD)) {
            log.error("You have misconfigured your application! It should not" +
                "run with both the 'dev' and 'cloud' profiles at the same time.");
        }
        
        // Initialize flight database 
        
        
        List<SearchFlightDTO> flights = new ArrayList<SearchFlightDTO>();
        SearchFlightDTO sfdto = new SearchFlightDTO();
        
        sfdto.setOrigin("NYC");
        sfdto.setDestination("SFO");
        sfdto.setFare(121L);
        sfdto.setFlightDate("2017-01-01");
        sfdto.setInventory(100L);
        sfdto.setFlightNumber("SF121");
        
        flights.add(sfdto);
        
		searchFlightService.init(flights);
        
    }

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments
     * @throws UnknownHostException if the local host name could not be resolved into an address
     */
    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(BfalegacyApp.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        log.info("\n----------------------------------------------------------\n\t" +
                "Application '{}' is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:{}\n\t" +
                "External: \thttp://{}:{}\n----------------------------------------------------------",
            env.getProperty("spring.application.name"),
            env.getProperty("server.port"),
            InetAddress.getLocalHost().getHostAddress(),
            env.getProperty("server.port"));

    }
    
    
	@Bean
	public Queue queue() {
		return new ActiveMQQueue("bfa.queue");
	}
	    
//	  @Bean
//	    public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
//	                                                    DefaultJmsListenerContainerFactoryConfigurer configurer) {
//	        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//	        // This provides all boot's default to this factory, including the message converter
//	        configurer.configure(factory, connectionFactory);
//	        // You could still override some of Boot's default if necessary.
//	        return factory;
//	    }

//	    @Bean // Serialize message content to json using TextMessage
//	    public MessageConverter jacksonJmsMessageConverter() {
//	        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
//	        converter.setTargetType(MessageType.TEXT);
//	        converter.setTypeIdPropertyName("_type");
//	        return converter;
//	    }
}
