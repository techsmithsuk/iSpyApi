package com.techswitch.ispy.models;

import com.techswitch.ispy.models.request.SuspectFbiRequestModel;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SuspectFbiRequestModelTest {

    @Test
    public void getDatesOfBirthUsedAsSingleString_givenNullDate_thenReturnNull(){
        SuspectFbiRequestModel suspect = new SuspectFbiRequestModel();
        suspect.setDatesOfBirthUsed(null);
        assertThat(suspect.getDatesOfBirthUsedAsSingleString()).isNull();
    }
}
