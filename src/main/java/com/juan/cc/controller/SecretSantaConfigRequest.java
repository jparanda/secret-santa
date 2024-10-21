package com.juan.cc.controller;

import com.juan.cc.model.FamilyRestriction;
import com.juan.cc.model.Person;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class SecretSantaConfigRequest {

    private List<Person> participants;
    private List<FamilyRestriction> familyRestrictions;
    private Map<Integer, Map<String, String>> pastAssignments;
}
