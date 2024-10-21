package com.juan.cc.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PastAssignments {

    private String person;
    private List<String> pastReceivers;
}
