package com.techswitch.ispy.models.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SuspectFbiRequestModel {

    private String title;
    @JsonProperty("dates_of_birth_used")
    private List<String> datesOfBirthUsed;
    private String hair;
    private String eyes;
    @JsonProperty("height_min")
    private Integer heightMin;
    private String weight;
    private String sex;
    private String race;
    private String nationality;
    @JsonProperty("scars_and_marks")
    private String scarsAndMarks;
    @JsonProperty("reward_text")
    private String rewardText;
    private String caution;
    private String details;
    @JsonProperty("warning_message")
    private String warningMessage;
    private List<Map<String, String>> images;
    private String uid;
    private String modified;
    private String publication;


    public SuspectFbiRequestModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public Timestamp getModifiedAsTimestamp() throws ParseException {
        return formatTimestamp(modified);
    }

    public Timestamp getPublicationAsTimestamp() throws ParseException {
        return formatTimestamp(publication);
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<Map<String, String>> getImages() {
        return images;
    }

    public void setImages(List<Map<String, String>> images) {
        this.images = images;
    }

    public void setDatesOfBirthUsed(List<String> datesOfBirthUsed) {
        this.datesOfBirthUsed = datesOfBirthUsed;
    }

    public String getHair() {
        return hair;
    }

    public void setHair(String hair) {
        this.hair = hair;
    }

    public String getEyes() {
        return eyes;
    }

    public void setEyes(String eyes) {
        this.eyes = eyes;
    }

    public Integer getHeightMin() {
        return heightMin;
    }

    public void setHeightMin(Integer heightMin) {
        this.heightMin = heightMin;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getScarsAndMarks() {
        return scarsAndMarks;
    }

    public void setScarsAndMarks(String scarsAndMarks) {
        this.scarsAndMarks = scarsAndMarks;
    }

    public String getRewardText() {
        return rewardText;
    }

    public void setRewardText(String rewardText) {
        this.rewardText = rewardText;
    }

    public String getCaution() {
        return caution;
    }

    public void setCaution(String caution) {
        this.caution = getTextWithoutTags(caution);
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = getTextWithoutTags(details);
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }

    private String getTextWithoutTags(String text) {
        if (text != null) {
            text = text.replaceAll("<[^>]+>", "");
            text = text.replaceAll("\r\n", "");
        }
        return text;
    }

    public Timestamp formatTimestamp(String timestamp) throws ParseException {
        SimpleDateFormat formatterWithoutTimezone = new SimpleDateFormat("yyyy-mm-dd'T'hh:mm:ss");
        Date parsedTimestamp = null;

        try {
            parsedTimestamp = formatterWithoutTimezone.parse(timestamp);

        } catch (ParseException e) {
            SimpleDateFormat formatterWithTimeZone = new SimpleDateFormat("yyyy-mm-dd'T'hh:mm:ssTZD");
            parsedTimestamp = formatterWithTimeZone.parse(timestamp);
        }
        return new Timestamp(parsedTimestamp.getTime());
    }

    public String getDatesOfBirthUsedAsSingleString() {
        if (datesOfBirthUsed == null) {
            return null;
        }
        return datesOfBirthUsed.get(0);
    }
}
