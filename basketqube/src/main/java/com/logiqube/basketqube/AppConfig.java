package com.logiqube.basketqube;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:messages.properties")
public class AppConfig {

//	@Autowired
//	Environment env;
	
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
