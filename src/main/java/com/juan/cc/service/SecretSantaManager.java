package com.juan.cc.service;

import com.juan.cc.controller.SecretSantaConfigRequest;
import com.juan.cc.exceptions.NoValidReceiverException;
import com.juan.cc.model.FamilyRestriction;
import com.juan.cc.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
        return restrictions.parallelStream() // Use parallel stream for potential performance improvement
                .collect(Collectors.toMap(
                        restriction -> new Person(restriction.getPerson()),
                        restriction -> restriction.getImmediateFamilyMembers().parallelStream() // Also parallelize family member processing
                                .map(Person::new)
                                .collect(Collectors.toList())
                ));
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

            if (!previousAssignments.isEmpty()) {
                List<Person> pastReceivers = previousAssignments.getOrDefault(santa, new ArrayList<>())
                        .stream()
                        .limit(3)
                        .toList();
                invalidReceivers.addAll(pastReceivers);
            }

            List<Person> validReceivers = new ArrayList<>(receiversPool);
            validReceivers.removeAll(invalidReceivers);

            if (validReceivers.isEmpty()) {
                throw new NoValidReceiverException(String.format("No valid receivers available for [%s] invalid receivers [%s] " +
                        "and total participants [%s]", santa.getName(), invalidReceivers.size(), receiversPool.size()));
            }

            Person receiver = validReceivers.get(random.nextInt(validReceivers.size()));
            assignments.put(santa, receiver);
            receiversPool.remove(receiver);
        }

        return assignments;
    }
}
