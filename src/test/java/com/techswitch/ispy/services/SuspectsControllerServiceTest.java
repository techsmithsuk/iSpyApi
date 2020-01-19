package com.techswitch.ispy.services;

import com.techswitch.ispy.config.IntegrationTestConfig;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("testDataSource")
@ContextConfiguration(classes = IntegrationTestConfig.class)
class SuspectsControllerServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Jdbi jdbi;

    @Test
    void getListOfSuspects() throws Exception {

        mockMvc.perform(get("http://localhost/suspects?page=1&pageSize=10"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("[0].name").value("Harry Potter"))
                .andExpect(jsonPath("[0].imageUrl").value("https://www.fbi.gov/wanted/additional/cesar-munguia/@@images/image/thumb"))
                .andReturn();
    }

    @Test
    void getSuspectById_givenValidId_thenReturnSuspect() throws Exception {
        int id = 532;
        Long idFromDatabase = addFakeSuspect(id);
        mockMvc.perform(get("http://localhost/suspects/" + idFromDatabase))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Harry Potter"))
                .andExpect(jsonPath("$.imageUrl").value("https://www.fbi.gov/wanted/additional/cesar-munguia/@@images/image/thumb"))
                .andReturn();
    }

    private Long addFakeSuspect(int id) {
       return jdbi.withHandle(handle ->
                handle.createQuery("INSERT INTO all_suspects (id, name, image_url) " +
                        "VALUES (:id, 'Harry Potter', 'https://www.fbi.gov/wanted/additional/cesar-munguia/@@images/image/thumb') RETURNING id")
                        .bind("id", id)
                        .mapTo(Long.class)
                        .one());
    }

    @Test
    void getSuspectById_givenInvalidId_thenReturnBadRequest() throws Exception {
        int id = 1234;
        mockMvc.perform(get("http://localhost/suspects/" + id))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.Error").value("Suspect with id: " + id  + " not found"))
                .andReturn();
    }

    @Test
    void replaceTags(){
        String textWithTags = "<p>hewrldgfsklfjghdflg</p>";
        String str = textWithTags.replaceAll("<.*?>", "");
        assertFalse(str.contains("<p>"));
        assertFalse(str.contains("</p>"));
    }
}