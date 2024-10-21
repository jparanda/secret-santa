package com.juan.cc.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juan.cc.controller.SecretSantaConfigRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SecretSantaManagerTest {

    private static final String SECRET_SANTA_CONFIG_FIRST_YEAR = "secretSantaConfig_year1.json";
    private static final String SECRET_SANTA_CONFIG_SECOND_YEAR = "secretSantaConfig_year2.json";


    @Test
    void assignSecretSanta_first_year_no_pastAssignments() throws IOException {
        // Arrange
        SecretSantaConfigRequest secretSantaConfigRequest = readSecretSantaConfigRequest();
        SecretSantaManager secretSantaManager = new SecretSantaManager();

        // Act
        Map<String, String> assignmentsResponse = secretSantaManager.assignSecretSanta(secretSantaConfigRequest);

        // Verifies-Assertions
        Assertions.assertNotNull(assignmentsResponse);

    }

    @Test
    void assignSecretSanta_happy_path() throws IOException {
        // Arrange
        SecretSantaConfigRequest secretSantaConfigRequest = readSecretSantaConfigRequest();
        SecretSantaManager secretSantaManager = new SecretSantaManager();

        // Act
        Map<String, String> assignmentsResponse = secretSantaManager.assignSecretSanta(secretSantaConfigRequest);

        // Verifies-Assertions
        Assertions.assertNotNull(assignmentsResponse);

    }

    private SecretSantaConfigRequest readSecretSantaConfigRequest() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Load JSON file from classloader
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(SECRET_SANTA_CONFIG_SECOND_YEAR);

        // Check if the inputStream is null
        if (inputStream == null) {
            throw new IOException(String.format("File not found %s ", SECRET_SANTA_CONFIG_SECOND_YEAR));
        }

        // Read the JSON input stream and convert it to a Participant object
        SecretSantaConfigRequest secretSantaConfigRequest = objectMapper.readValue(inputStream,
                SecretSantaConfigRequest.class);


        return secretSantaConfigRequest;

    }
}