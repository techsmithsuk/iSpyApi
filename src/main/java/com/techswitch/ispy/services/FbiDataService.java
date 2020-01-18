package com.techswitch.ispy.services;

import com.fasterxml.jackson.databind.JsonNode;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FbiDataService {


    private Jdbi jdbi;

    @Autowired
    public FbiDataService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public void saveDataToDatabase(JsonNode items) {
        for (JsonNode node : items){
            System.out.println(node.toString());
        }
    }
}
