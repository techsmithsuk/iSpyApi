package com.techswitch.ispy.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techswitch.ispy.models.request.SuspectFbiRequestModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.*;

@Service
public class FbiDataService {

    public List<SuspectFbiRequestModel> getSuspectsFromFbiApi() throws IOException {
        int currentPage = 1;
        List<SuspectFbiRequestModel> suspects = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode items = getJsonFromUrl(getFbiApiUrl(currentPage), "items");
        while (items.size() > 0) {
            for (JsonNode node : items) {
                suspects.add(objectMapper.readValue(node.toString(), SuspectFbiRequestModel.class));
            }
            items = getJsonFromUrl(getFbiApiUrl(++currentPage), "items");
        }
        return suspects;
    }

    public JsonNode getJsonFromUrl(String fbiApiUrl, String objectNameInJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(fbiApiUrl, String.class);
        return mapper.readTree(response.getBody()).get(objectNameInJson);
    }

    public String getFbiApiUrl(int currentPage) {
        return UriComponentsBuilder
                .fromUriString("https://api.fbi.gov/@wanted")
                .queryParam("page", currentPage)
                .build().toUriString();
    }
}
