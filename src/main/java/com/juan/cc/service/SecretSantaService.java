package com.juan.cc.service;

import com.juan.cc.controller.SecretSantaConfigRequest;

import java.util.Map;

public interface SecretSantaService {

    Map<String, String> assignSecretSanta(SecretSantaConfigRequest secretSantaConfigRequest);
}
