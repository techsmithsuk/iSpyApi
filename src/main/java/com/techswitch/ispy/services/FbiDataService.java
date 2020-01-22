package com.techswitch.ispy.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techswitch.ispy.models.request.SuspectFbiRequestModel;
import org.springframework.http.*;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.*;

@Service
public class FbiDataService {

    RestTemplate restTemplate = new RestTemplate();
    ObjectMapper mapper = new ObjectMapper();

    public List<SuspectFbiRequestModel> getSuspectsFromFbiApi() throws IOException {
        int currentPage = 1;
        List<SuspectFbiRequestModel> suspects = new ArrayList<>();
        System.out.println("[METHOD - GET-SUSPECTS-FROM-FBI-API]: starting to get suspects from fbi api");
        JsonNode items = getJsonFromUrl(getFbiApiUrl(currentPage), "items");
        while (items.size() > 0) {
            for (JsonNode node : items) {
                suspects.add(mapper.readValue(node.toString(), SuspectFbiRequestModel.class));
            }
            items = getJsonFromUrl(getFbiApiUrl(++currentPage), "items");
        }
        System.out.println("[METHOD - GET-SUSPECTS-FROM-FBI-API]: FINISHED to get suspects from fbi api");
        return suspects;
    }

    public JsonNode getJsonFromUrl(String fbiApiUrl, String objectNameInJson) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> response = restTemplate.exchange(fbiApiUrl, HttpMethod.GET,entity, String.class);
        JsonNode node = mapper.readTree(response.getBody()).get(objectNameInJson);
        return node;
    }

    public String getFbiApiUrl(int currentPage) {
        return UriComponentsBuilder
                .fromUriString("https://api.fbi.gov/@wanted")
                .queryParam("page", currentPage)
                .build().toUriString();
    }
}
