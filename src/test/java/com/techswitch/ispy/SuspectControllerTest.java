package com.techswitch.ispy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techswitch.ispy.controllers.SuspectController;
import com.techswitch.ispy.models.Suspect;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SuspectControllerTest {

    @Test
    public void suspectController() throws JsonProcessingException {
        SuspectController suspectController = new SuspectController();
        assertThat(suspectController.suspect()).isEqualTo("{\"name\":\"BaddieMcBad\"}");
    }
}

