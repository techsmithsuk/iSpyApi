package com.techswitch.ispy.controllers;

import com.techswitch.ispy.config.IntegrationTestConfig;
import com.techswitch.ispy.services.LoginService;
import com.techswitch.ispy.services.NukeService;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"testDataSource","testSigner","testLoginConfig"})
@ContextConfiguration(classes = IntegrationTestConfig.class)
public class NukeControllerTest {

    @Autowired
    private Jdbi jdbi;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private NukeService nukeService;
    @Autowired
    private LoginService loginService;

    @Test
    public void truncateAllRowsInAllTables_doesNotDeleteTables(){

        assertEquals(4, (long) countTables());
        nukeService.truncateAllRowsInAllTables();
        assertEquals(4, (long) countTables());
        assertTrue(countRows("suspects") == 0);

        nukeService.truncateAllRowsInAllTables();
        assertTrue(countRows("suspects") == 0);
    }

    @Test
    public void controllerReturnsSuccessWhenValidTokenPassed() throws Exception {

        String token = loginService.getToken();

        mockMvc.perform(get("http://localhost:8080/admin/are-you-sure")
            .contentType(MediaType.APPLICATION_JSON)
            .header("token", token))
            .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void controllerReturnsFailureWhenInvalidTokenPassed() throws Exception {

        String badToken = "badToken";

        mockMvc.perform(get("http://localhost:8080/admin/are-you-sure")
            .contentType(MediaType.APPLICATION_JSON)
            .header("token", badToken))
            .andDo(print()).andExpect(status().isBadRequest());
    }

    private Long countTables(){

        String SQL =    "SELECT COUNT(*) " +
                        "FROM information_schema.tables " +
                        "WHERE table_schema = 'public';";

        return jdbi.withHandle(handle -> handle.createQuery(SQL).mapTo(Long.class).one());
    }

    private Long countRows(String table){
        String SQL = "SELECT COUNT(*) FROM " + table + ";";
        return jdbi.withHandle(handle -> handle.createQuery(SQL).mapTo(Long.class).one());
    }

    @Test
    public void itReturnsTrue(){

        assertTrue(nukeService.returnsTrue());
    }

    @Test
    public void itReturnsFalse(){

        assertFalse(nukeService.returnsTrue());
    }
}
