package com.techswitch.ispy.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techswitch.ispy.config.IntegrationTestConfig;
import com.techswitch.ispy.models.request.SuspectFbiRequestModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
    public void getSuspectsFromFbiApi_givenJsonNode_thenReturnListOfSuspects() throws IOException {
        File jsonResponse = new File("src/test/java/com/techswitch/ispy/services/data/ItemsSuspectJson.json");
        File emptyJsonResponse = new File("src/test/java/com/techswitch/ispy/services/data/EmptyList.json");
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        JsonNode emptyListJsonNode = objectMapper.readTree(emptyJsonResponse);

        FbiDataService fbiDataService1 = Mockito.spy(new FbiDataService());
        Mockito.doReturn(jsonNode).when(fbiDataService1).getJsonFromUrl("https://api.fbi.gov/@wanted?page=1", "items");
        Mockito.doReturn(emptyListJsonNode).when(fbiDataService1).getJsonFromUrl("https://api.fbi.gov/@wanted?page=2", "items");

        List<SuspectFbiRequestModel> suspectsFromFbiApi = fbiDataService1.getSuspectsFromFbiApi();

        assertTrue(suspectsFromFbiApi.size() == 2);
        assertTrue(suspectsFromFbiApi.get(0).getUid().equals("1aa0a21720cb42678a32087c3c8fc3db"));
        assertTrue(suspectsFromFbiApi.get(1).getUid().equals("2611092a-4356-46c3-a573-5081ee8771bf"));
    }
}

