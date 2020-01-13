package com.techswitch.ispy.services;

import com.techswitch.ispy.controllers.SuspectsController;
import com.techswitch.ispy.models.Suspect;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SuspectsControllerServiceTest {

    private static SuspectsService suspectsService;
    private static SuspectsController suspectsController;
    private static Jdbi jdbi;


    @BeforeAll
    static void init() {
        suspectsService = new SuspectsService("jdbc:h2:~/test;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE");
        suspectsController = new SuspectsController(suspectsService);

        jdbi = suspectsService.jdbi;

        jdbi.withHandle(handle -> handle.execute("CREATE TABLE all_suspects (\n" +
                " \tid serial  primary key, \n" +
                " \tname varchar(100),\n" +
                " \timage_url varchar(500)\n" +
                " );"));
        jdbi.withHandle(handle -> handle.execute("INSERT INTO all_suspects(name,image_url) values('Harry Potter','https://www.fbi.gov/wanted/additional/cesar-munguia/@@images/image/thumb');"));
    }

    @AfterAll
    static void dropTable() {
        jdbi.withHandle(handle -> handle.execute("DROP TABLE all_suspects;"));
    }

    @Test
    void getListofSuspects() {
        List<Suspect> allSuspects = suspectsController.getSuspectList(1);

        assertThat(allSuspects.get(0).getName()).isEqualTo("Harry Potter");
        assertThat(allSuspects.get(0).getImageUrl()).isEqualTo("https://www.fbi.gov/wanted/additional/cesar-munguia/@@images/image/thumb");
    }

    @Test
    public void suspectController() {
        assertThat(suspectsController.suspect()).isEqualTo("{\"name\":\"BaddieMcBad\"}");
    }

}