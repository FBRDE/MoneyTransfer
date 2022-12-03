package com.openclassrooms.moneytransfer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MoneyTransferApplication {
	private static final Logger logger = LogManager.getLogger("MoneyTransferApplication");
	public static void main(String[] args) {
		SpringApplication.run(MoneyTransferApplication.class, args);
		logger.info("Initializing");
	}

}