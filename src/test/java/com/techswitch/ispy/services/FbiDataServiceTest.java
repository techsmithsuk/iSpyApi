package com.techswitch.ispy.services;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techswitch.ispy.config.IntegrationTestConfig;
import com.techswitch.ispy.models.database.SuspectDatabaseModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("testDataSource")
@ContextConfiguration(classes = IntegrationTestConfig.class)
public class FbiDataServiceTest {


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FbiDataService fbiDataService;

    @MockBean
    private FbiDataService fbiDataServiceMocked;
//    @Test
//    public void saveDataToDatabase_givenValidJson_returnNumberOfRowsAdded() throws IOException, IOException, ParseException {
//        List<JsonNode> listOfItems = new ArrayList<>();
//        File from = new File("src/test/java/com/techswitch/ispy/services/data/SuspectJson.json");
//        JsonNode items = objectMapper.readTree(from);
//        listOfItems.add(items);
//        System.err.println(listOfItems.get(0));
//
//        Integer numberOfRowsAdded = fbiDataService.saveDataToDatabase(listOfItems);
//
//        assertTrue(numberOfRowsAdded == 1);
//    }
//    @Test
//    public void getSuspectsFromFbiApi_given


    @Test
    public void getFbiAdiUrl_givenPositivePageNumber_theReturnUrl() {
       String expected = "https://api.fbi.gov/@wanted?page=99999";
       assertTrue(fbiDataService.getFbiApiUrl(99999).equals(expected));
    }
    @Test
    public void getFbiApiUrl_givenNegativePageNumber_theReturnUrl() {
        String expected = "https://api.fbi.gov/@wanted?page=-1";
        assertTrue(fbiDataService.getFbiApiUrl(-1).equals(expected));
    }

    @Test
    public void getJsonFromUrl() throws IOException {
        File from = new File("src/test/java/com/techswitch/ispy/services/data/items_SuspectJson.json");
        JsonNode suspectFromJson = getJsonFromUrl("src/test/java/com/techswitch/ispy/services/data/items_SuspectJson.json", "items");

        System.out.println(suspectFromJson);



//        SuspectDatabaseModel suspectFromJson = objectMapper.readValue(from, SuspectDatabaseModel.class);
//        ResponseEntity responseEntity = ResponseEntity.ok().body(suspectFromJson);
//        ResponseEntity<String> response = "src/test/java/com/techswitch/ispy/services/data/SuspectJson.json";
//        fbiDataService.getJsonFromUrl(from, "items" );




    }




    public JsonNode getJsonFromUrl(String fbiApiUrl, String objectNameInJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(fbiApiUrl, String.class);
        return mapper.readTree(response.getBody()).get(objectNameInJson);
    }



}




