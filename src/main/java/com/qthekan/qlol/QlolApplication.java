package com.qthekan.qlol;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QlolApplication {
	
	org.slf4j.Logger gLog = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) {
		SpringApplication.run(QlolApplication.class, args);
	}

}
