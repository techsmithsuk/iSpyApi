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

@RestController
public class FbiDataController {


    private static final String FBI_URL = "https://api.fbi.gov/@wanted?pageSize=30&page=";
    private FbiDataService fbiDataService;

    @Autowired
    public FbiDataController(FbiDataService fbiDataService) {
        this.fbiDataService = fbiDataService;
    }

    @RequestMapping(value = "/admin/fetch-fbi-data")
    public ResponseEntity fetchFbiData() throws IOException {
        int currentPage = 1;
        JsonNode items = getJsonFromUrl(FBI_URL + currentPage);

        while (items.size() > 0){
            fbiDataService.saveDataToDatabase(items);
            items = getJsonFromUrl(FBI_URL + (++currentPage));
        }
        return ResponseEntity.ok().build();
    }

    private JsonNode getJsonFromUrl(String fbiApiUrl) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(fbiApiUrl, String.class);
        JsonNode items = mapper.readTree(response.getBody()).get("items");
        return items;
    }
}
