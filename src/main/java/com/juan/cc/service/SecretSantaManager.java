package com.juan.cc.service;

import com.juan.cc.controller.SecretSantaConfigRequest;
import com.juan.cc.exceptions.NoValidReceiverException;
import com.juan.cc.model.FamilyRestriction;
import com.juan.cc.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class SecretSantaManager implements SecretSantaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecretSantaManager.class);

    @Override
    public Map<String, String> assignSecretSanta(SecretSantaConfigRequest secretSantaConfigRequest) {
        List<Person> participants = secretSantaConfigRequest.getParticipants();

        Map<Person, List<Person>> familyMap = convertFamilyRestrictions(secretSantaConfigRequest.getFamilyRestrictions());
        Map<Person, List<Person>> previousAssignmentsMap = convertPastAssignments(secretSantaConfigRequest.getPastAssignments());

        Map<Person, Person> assignments = assignSecretSanta(participants, familyMap, previousAssignmentsMap);

        Map<String, String> response = new HashMap<>();
        assignments.forEach((santa, receiver) -> response.put(santa.getName(), receiver.getName()));

        return response;
    }

    /**
     * This method convert family restriction list to Map
     * to handle these data easier
     */
    private Map<Person, List<Person>> convertFamilyRestrictions(List<FamilyRestriction> restrictions) {
        Map<Person, List<Person>> familyMap = new HashMap<>();
        for (FamilyRestriction restriction : restrictions) {
            List<Person> familyMembers = new ArrayList<>();
            for (String memberName : restriction.getImmediateFamilyMembers()) {
                familyMembers.add(new Person(memberName));
            }
            familyMap.put(new Person(restriction.getPerson()), familyMembers);
        }
        return familyMap;
    }

    /**
     * This method convert past assignments restriction list to Map
     * to handle these data easier
     */
    private Map<Person, List<Person>> convertPastAssignments(Map<Integer, Map<String, String>> pastAssignments) {
        Map<Person, List<Person>> pastAssignmentsMap = new HashMap<>();
        for (Map<String, String> yearlyAssignments : pastAssignments.values()) {
            for (Map.Entry<String, String> entry : yearlyAssignments.entrySet()) {
                Person santa = new Person(entry.getKey());
                Person receiver = new Person(entry.getValue());

                pastAssignmentsMap.computeIfAbsent(santa, k -> new ArrayList<>()).add(receiver);
            }
        }
        return pastAssignmentsMap;
    }


    private Map<Person, Person> assignSecretSanta(List<Person> participants, Map<Person, List<Person>> familyMap,
                                                  Map<Person, List<Person>> previousAssignments) {
        List<Person> receiversPool = new ArrayList<>(participants);
        Map<Person, Person> assignments = new HashMap<>();
        Random random = new Random();

        for (Person santa : participants) {
            List<Person> invalidReceivers = new ArrayList<>(familyMap.getOrDefault(santa, new ArrayList<>()));
            invalidReceivers.add(santa);

            if(!previousAssignments.isEmpty()) {
                List<Person> pastReceivers = previousAssignments.getOrDefault(santa, new ArrayList<>())
                        .stream()
                        .limit(3)
                        .toList();
                invalidReceivers.addAll(pastReceivers);
            }

            List<Person> validReceivers = new ArrayList<>(receiversPool);
            validReceivers.removeAll(invalidReceivers);

            if (validReceivers.isEmpty()) {
                throw new NoValidReceiverException(String.format("No valid receivers available for %s", santa.getName()));
            }

            Person receiver = validReceivers.get(random.nextInt(validReceivers.size()));
            assignments.put(santa, receiver);
            receiversPool.remove(receiver);
        }

        return assignments;
    }
}
