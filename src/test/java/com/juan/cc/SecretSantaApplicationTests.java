package com.juan.cc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juan.cc.controller.SecretSantaConfigRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;

@SpringBootTest
class SecretSantaApplicationTests {

	private static final String SECRET_SANTA_CONFIG_FIRST_YEAR = "secretSantaConfig_year1.json";

	@Test
	void contextLoads() {
	}


	private SecretSantaConfigRequest readSecretSantaConfigRequest() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();

		// Load JSON file from classloader
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(SECRET_SANTA_CONFIG_FIRST_YEAR);

		// Check if the inputStream is null
		if (inputStream == null) {
			throw new IOException(String.format("File not found %s ", SECRET_SANTA_CONFIG_FIRST_YEAR));
		}

		// Read the JSON input stream and convert it to a Participant object
		SecretSantaConfigRequest secretSantaConfigRequest = objectMapper.readValue(inputStream,
				SecretSantaConfigRequest.class);

		return secretSantaConfigRequest;

	}

}
