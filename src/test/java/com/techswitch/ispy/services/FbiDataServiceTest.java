package com.techswitch.ispy.services;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techswitch.ispy.config.IntegrationTestConfig;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
    public void saveDataToDatabase_givenValidJson_returnNumberOfRowsAdded() throws IOException, ParseException {

        File from = new File("src/test/java/com/techswitch/ispy/services/data/SuspectJson.json");
        JsonNode items = objectMapper.readTree(from);

        Integer numberOfRowsAdded = fbiDataService.saveDataToDatabase(items);

        assertTrue(numberOfRowsAdded == 1);
    }
}
