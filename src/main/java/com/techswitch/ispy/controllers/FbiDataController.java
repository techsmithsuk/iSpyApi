package com.techswitch.ispy.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techswitch.ispy.services.FbiDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;

@RestController
public class FbiDataController {


    private static final String FBI_URL = "https://api.fbi.gov/@wanted?pageSize=30&page=";
    private FbiDataService fbiDataService;

    @Autowired
    public FbiDataController(FbiDataService fbiDataService) {
        this.fbiDataService = fbiDataService;
    }

    @RequestMapping(value = "/admin/fetch-fbi-data")
    public ResponseEntity fetchFbiData() throws IOException, ParseException {
        int currentPage = 1;
        int rowsOfSuspectsAdded = 0;
        JsonNode items = getJsonFromUrl(FBI_URL + currentPage);

        while (items.size() > 0) {
            rowsOfSuspectsAdded += fbiDataService.saveDataToDatabase(items);
            items = getJsonFromUrl(FBI_URL + (++currentPage));
        }
        if (rowsOfSuspectsAdded == 0) {
            return ResponseEntity.ok().body(Collections.singletonMap("Success", "No data has been added. Database Up to date."));
        }
            return ResponseEntity.ok().body(Collections.singletonMap("Success", rowsOfSuspectsAdded + " rows has been added to database."));
    }

    private JsonNode getJsonFromUrl(String fbiApiUrl) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(fbiApiUrl, String.class);
        return mapper.readTree(response.getBody()).get("items");
    }
}
