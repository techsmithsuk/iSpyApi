package com.techswitch.ispy;

import com.techswitch.ispy.models.Suspect;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SuspectModelTest {

    @Test
    public void suspectHasName() {

        Suspect suspect = new Suspect();
        assertThat(suspect.getName()).isNotEqualTo("");
    }

}
