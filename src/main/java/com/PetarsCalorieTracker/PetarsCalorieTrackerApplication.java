package com.PetarsCalorieTracker;

import com.PetarsCalorieTracker.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/* http://localhost:portNumber */

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class PetarsCalorieTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetarsCalorieTrackerApplication.class, args);
	}

}
