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

import java.util.Collections;
import java.util.List;

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

    @BeforeEach
    private void createSuspects() {
        addFakeSuspect(123, "IamAbigBouy");
        addFakeSuspect(1234, "thisisAtest");
    }

    @AfterEach
    private void deleteSuspects() {
        deleteFakeSuspect(123);
        deleteFakeSuspect(1234);
    }

    private Long addFakeSuspect(int id, String uid) {
        return jdbi.withHandle(handle ->
                handle.createQuery("INSERT INTO suspects (id, title, date_of_birth, hair, eyes, height, weight, sex, race, nationality, scars_and_marks, reward_text, caution, details, warning_message, fbi_uid, modified, publication) " +
                        "VALUES (:id, 'JUVON JULIAN SEARLES', 'October 9, 1980', 'black', 'brown','69', '260 to 280 pounds', 'Male', 'black', 'American', 'Searles has scars on his head and chest.','£10000', 'Dangerous', 'Missing person', 'SHOULD BE CONSIDERED ARMED AND DANGEROUS', :uid, '2020-01-17 19:32:50.000000', '2019-01-12 08:24:00.000000') RETURNING id;")
                        .bind("id", id)
                        .bind("uid", uid)
                        .mapTo(Long.class)
                        .one());
    }

    private void deleteFakeSuspect(int id) {
        jdbi.withHandle(handle -> handle.createUpdate("DELETE FROM suspects WHERE id = :id")
                .bind("id", id)
                .execute());
    }

    @Test
    void getListOfSuspects() throws Exception {

        mockMvc.perform(get("http://localhost:8080/suspects?page=1&pageSize=10"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("[0].fbiUid").value("IamAbigBouy"))
                .andExpect(jsonPath("[1].fbiUid").value("thisisAtest"))
                .andExpect(jsonPath("[1].dateOfBirth").value("October 9, 1980"))

                .andReturn();
    }

    @Test
    void getSuspectById_givenValidId_thenReturnSuspect() throws Exception {
        mockMvc.perform(get("http://localhost:8080/suspects/123"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(123))
                .andExpect(jsonPath("$.title").value("JUVON JULIAN SEARLES"))
                .andReturn();
    }

    @Test
    void getSuspectById_givenInvalidId_thenReturnBadRequest() throws Exception {

        int id = 9999;
        mockMvc.perform(get("http://localhost:8080/suspects/" + id))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.Error").value("Suspect with id: " + id + " not found"))
                .andReturn();
    }

    @Test
    void replaceTags() {
        String textWithTags = "<p>hewrldgfsklfjghdflg</p>";
        String str = textWithTags.replaceAll("<.*?>", "");
        assertFalse(str.contains("<p>"));
        assertFalse(str.contains("</p>"));
    }
}