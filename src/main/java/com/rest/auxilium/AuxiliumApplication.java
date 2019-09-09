package com.rest.auxilium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages={"com.rest.auxilium"})
public class AuxiliumApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuxiliumApplication.class, args);
	}

}
