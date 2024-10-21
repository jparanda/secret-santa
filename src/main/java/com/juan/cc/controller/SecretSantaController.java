package com.juan.cc.controller;

import com.juan.cc.service.SecretSantaService;
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

    @PostMapping("/assign")
    public Map<String, String> assignSecretSanta(@RequestBody SecretSantaConfigRequest secretSantaConfigRequest) {
        return secretSantaService.assignSecretSanta(secretSantaConfigRequest);

    }
}
