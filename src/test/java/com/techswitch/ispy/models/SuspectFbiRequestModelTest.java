package com.techswitch.ispy.models;

import com.techswitch.ispy.models.request.SuspectFbiRequestModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.sql.Timestamp;
import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SuspectFbiRequestModelTest {

        SuspectFbiRequestModel suspect;


    @Test
    public void getDatesOfBirthUsedAsSingleString_givenNullDate_thenReturnNull(){
        suspect = new SuspectFbiRequestModel();
        suspect.setDatesOfBirthUsed(null);
        assertThat(suspect.getDatesOfBirthUsedAsSingleString()).isNull();
    }


    @ParameterizedTest
    @ValueSource(strings = { "2020-01-17T19:32:50+00:00", "2020-01-17T19:32:50"})
    public void formatTimeStamp_givenValidTimeStamp_thenReturnTimeStamp(String timestamp) throws ParseException {
        suspect = new SuspectFbiRequestModel();
        assertThat(suspect.formatTimestamp(timestamp).equals(Timestamp.valueOf("2020-01-17 19:32:50")));
    }


    @Test
    public void formatTimeStamp_givenInvalidTimestamp_thenThrowParseException() throws ParseException {
        suspect = new SuspectFbiRequestModel();

        assertThatThrownBy(() -> {
            suspect.formatTimestamp("200-01-1");
        }).isInstanceOf(IllegalArgumentException.class);

    }
}
