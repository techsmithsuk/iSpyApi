package com.techswitch.ispy.controllers;

import com.techswitch.ispy.config.IntegrationTestConfig;
import com.techswitch.ispy.services.NukeService;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"testDataSource","testSigner","testLoginConfig"})
@ContextConfiguration(classes = IntegrationTestConfig.class)
public class NukeControllerTest {

    @Autowired
    private Jdbi jdbi;

    @Autowired
    private NukeService nukeService;
    

    @Test
    public void truncateAllRowsInAllTables_doesNotDeleteTables(){

        assertEquals(4, (long) countTables());
        nukeService.truncateAllRowsInAllTables();
        assertEquals(4, (long) countTables());
        assertTrue(countRows("suspects") == 0);

        nukeService.truncateAllRowsInAllTables();
        assertTrue(countRows("suspects") == 0);
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







}
