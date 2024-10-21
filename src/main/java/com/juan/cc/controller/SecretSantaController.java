package com.juan.cc.controller;

import com.juan.cc.service.SecretSantaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/secret-santa")
public class SecretSantaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecretSantaController.class);

    private final SecretSantaService secretSantaService;

    public SecretSantaController(SecretSantaService secretSantaService) {
        this.secretSantaService = secretSantaService;
    }

    @Operation(summary = "Assign Secret Santa", description = "Assigns Secret Santa for participants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assignment successful"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Server error")})
    @PostMapping("/assign")
    public Map<String, String> assignSecretSanta(@RequestBody SecretSantaConfigRequest secretSantaConfigRequest) {
        return secretSantaService.assignSecretSanta(secretSantaConfigRequest);

    }
}
