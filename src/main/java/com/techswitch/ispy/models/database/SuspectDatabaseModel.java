package com.techswitch.ispy.models.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SuspectDatabaseModel {

    private Long id;
    @JsonProperty("dates_of_birth_used")
    private List<String> datesOfBirthUsed;
    private String hair;
    private String eyes;
    @JsonProperty("height_min")
    private Integer heightMin;
    @JsonProperty("height_max")
    private Integer heightMax;
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


    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getPublication() {
        return publication;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getDatesOfBirthUsed() {
        return datesOfBirthUsed;
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

    public Integer getHeightMax() {
        return heightMax;
    }

    public void setHeightMax(Integer heightMax) {
        this.heightMax = heightMax;
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

    private String getTextWithoutTags(String text){
        if (text != null) {
            text = text.replaceAll("<[^>]+>", "");
            text = text.replaceAll("\r\n", "");
        }
        return text;
    }

    private Timestamp formatTimestamp(String timestamp) throws ParseException {
        SimpleDateFormat datetimeFormatter = new SimpleDateFormat(
                "yyyy-mm-dd'T'hh:mm:ssZ");
        Date parsedTimestamp = null;
        try {
            parsedTimestamp = datetimeFormatter.parse(timestamp);
        } catch (ParseException e) {
            datetimeFormatter = new SimpleDateFormat("yyyy-mm-dd'T'hh:mm:ss");
            parsedTimestamp = datetimeFormatter.parse(timestamp);
        }
        return new Timestamp(parsedTimestamp.getTime());
    }

    public String getDatesOfBirthUsedAsSingleString(){
        if(datesOfBirthUsed == null){
            return null;
        }
        return datesOfBirthUsed.toString();
    }


















    //    private Long id;
//    private String posterUrl;
//    private String weight;
//    private String rewardText;
//    private String hair;
//    private String datesOfBirthUsed;
//    private String nationality;
//    private String aliases;
//    private String race;
//    private String fbiPublicationTimestamp;
//    private String name;
//    private String eyes;
//    private String details;
//    private String sex;
//    private String fbiSuspectUrl;
//    private String lastModifiedTimestamp;
//    private String heightMin;
//    private String warningMessage;
//    private String fbiJsonUrl;
//    private String imageUrl;
//    private String fbiJsonId;
//
//
//    public String getFbiJsonId() {
//        return fbiJsonId;
//    }
//
//    public void setFbiJsonId(String fbiJsonId) {
//        this.fbiJsonId = fbiJsonId;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getPosterUrl() {
//        return posterUrl;
//    }
//
//    public void setPosterUrl(String posterUrl) {
//        this.posterUrl = posterUrl;
//    }
//
//    public String getWeight() {
//        return weight;
//    }
//
//    public void setWeight(String weight) {
//        this.weight = weight;
//    }
//
//    public String getRewardText() {
//        return rewardText;
//    }
//
//    public void setRewardText(String rewardText) {
//        this.rewardText = rewardText;
//    }
//
//    public String getHair() {
//        return hair;
//    }
//
//    public void setHair(String hair) {
//        this.hair = hair;
//    }
//
//    public String getDatesOfBirthUsed() {
//        return datesOfBirthUsed;
//    }
//
//    public void setDatesOfBirthUsed(String datesOfBirthUsed) {
//        this.datesOfBirthUsed = datesOfBirthUsed;
//    }
//
//    public String getNationality() {
//        return nationality;
//    }
//
//    public void setNationality(String nationality) {
//        this.nationality = nationality;
//    }
//
//    public String getAliases() {
//        return aliases;
//    }
//
//    public void setAliases(String aliases) {
//        this.aliases = aliases;
//    }
//
//    public String getRace() {
//        return race;
//    }
//
//    public void setRace(String race) {
//        this.race = race;
//    }
//
//    public String getFbiPublicationTimestamp() {
//        return fbiPublicationTimestamp;
//    }
//
//    public void setFbiPublicationTimestamp(String fbiPublicationTimestamp) {
//        this.fbiPublicationTimestamp = fbiPublicationTimestamp;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getEyes() {
//        return eyes;
//    }
//
//    public void setEyes(String eyes) {
//        this.eyes = eyes;
//    }
//
//    public String getDetails() {
//        return details;
//    }
//
//    public void setDetails(String details) {
//        this.details = details;
//    }
//
//    public String getSex() {
//        return sex;
//    }
//
//    public void setSex(String sex) {
//        this.sex = sex;
//    }
//
//    public String getFbiSuspectUrl() {
//        return fbiSuspectUrl;
//    }
//
//    public void setFbiSuspectUrl(String fbiSuspectUrl) {
//        this.fbiSuspectUrl = fbiSuspectUrl;
//    }
//
//    public String getLastModifiedTimestamp() {
//        return lastModifiedTimestamp;
//    }
//
//    public void setLastModifiedTimestamp(String lastModifiedTimestamp) {
//        this.lastModifiedTimestamp = lastModifiedTimestamp;
//    }
//
//    public String getHeightMin() {
//        return heightMin;
//    }
//
//    public void setHeightMin(String heightMin) {
//        this.heightMin = heightMin;
//    }
//
//    public String getWarningMessage() {
//        return warningMessage;
//    }
//
//    public void setWarningMessage(String warningMessage) {
//        this.warningMessage = warningMessage;
//    }
//
//    public String getFbiJsonUrl() {
//        return fbiJsonUrl;
//    }
//
//    public void setFbiJsonUrl(String fbiJsonUrl) {
//        this.fbiJsonUrl = fbiJsonUrl;
//    }
//
//    public String getImageUrl() {
//        return imageUrl;
//    }
//
//    public void setImageUrl(String imageUrl) {
//        this.imageUrl = imageUrl;
//    }
}
