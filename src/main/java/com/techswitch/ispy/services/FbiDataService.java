package com.techswitch.ispy.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.techswitch.ispy.models.database.SuspectDatabaseModel;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class FbiDataService {


    private Jdbi jdbi;
    private final String UPDATE_SUSPECT_QUERY_SCRIPT = "INSERT INTO suspects (poster_url, weight, reward_text, hair, dates_of_birth_used, nationality, aliases, race, fbi_publication_timestamp, name, eyes, details, sex, fbi_suspect_url, last_modified_timestamp, height_min, warning_message, fbi_json_url, image_url, fbi_json_id) \n" +
            "values (:posterUrl, :weight, :rewardText, :hair, :datesOfBirthUsed, :nationality, :aliases, :race, :fbiPublicationTimestamp, :name, :eyes, :details, :sex, :fbiSuspectUrl, :lastModifiedTimestamp, :heightMin, :warningMessage, :fbiJsonUrl, :imageUrl, :fbiJsonId) RETURNING id;";

    private final String UPDATE_SUSPECT_PHOTO_URLS = "INSERT INTO suspect_photo_urls (suspectId, original, thumb, large, caption) VALUES (:suspectId, :original, :thumb, :large, :caption);";

    @Autowired
    public FbiDataService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public void saveDataToDatabase(JsonNode items) throws ParseException {
        Long suspectId = null;
        for (JsonNode node : items) {
            try (Handle handle = jdbi.open()) {

                Optional<Long> suspectByFbiJsonId = getSuspectByFbiId(node, handle);

                if(!suspectByFbiJsonId.isPresent()) {
                    suspectId = handle.createQuery(UPDATE_SUSPECT_QUERY_SCRIPT)
                            .bind("posterUrl", getPosterUrl(node))
                            .bind("weight", node.path("weight").textValue())
                            .bind("rewardText", node.path("reward_text").textValue())
                            .bind("hair", node.path("hair").textValue())
                            .bind("datesOfBirthUsed", getValuesAsStringFromArray(node, "dates_of_birth_used"))
                            .bind("nationality", node.path("nationality").textValue())
                            .bind("aliases", getValuesAsStringFromArray(node, "aliases"))
                            .bind("race", node.path("race_raw").textValue())
                            .bind("fbiPublicationTimestamp", getTimestampFromNode(node, "publication"))
                            .bind("name", node.path("title").textValue())
                            .bind("eyes", node.path("eyes_raw").textValue())
                            .bind("details", getDetails(node))
                            .bind("sex", node.path("sex").textValue())
                            .bind("fbiSuspectUrl", node.path("url").textValue())
                            .bind("lastModifiedTimestamp", getTimestampFromNode(node, "modified"))
                            .bind("heightMin", node.path("height_min").textValue())
                            .bind("warningMessage", node.path("warning_message").textValue())
                            .bind("fbiJsonUrl", node.path("@id").textValue())
                            .bind("imageUrl", getImageUrl(node))
                            .bind("fbiJsonId", node.path("uid").textValue())
                            .mapTo(Long.class)
                            .one();
                }

                if(suspectByFbiJsonId.isPresent()){
                    suspectId = suspectByFbiJsonId.get();
                }
                for (JsonNode photo : node.path("images")) {
                    if (photo.size() > 0) {
                        Long finalSuspectId = suspectId;
                        handle.createUpdate(UPDATE_SUSPECT_PHOTO_URLS)
                                .bind("suspectId", finalSuspectId)
                                .bind("original", photo.path("original").textValue())
                                .bind("thumb", photo.path("thumb").textValue())
                                .bind("large", photo.path("large").textValue())
                                .bind("caption", photo.path("caption").textValue())
                                .execute();
                    }
                }
            }
        }
    }

    private Optional<Long> getSuspectByFbiId(JsonNode node, Handle handle) {
        return handle.createQuery("SELECT id FROM suspects WHERE fbi_json_id = :fbiJsonId;")
                .bind("fbiJsonId",node.path("uid").textValue())
                .mapTo(Long.class)
                .findOne();
    }

    private Timestamp getTimestampFromNode(JsonNode node, String jsonPath) throws ParseException {
        SimpleDateFormat datetimeFormatter = new SimpleDateFormat(
                "yyyy-mm-dd'T'hh:mm:ssZ");
        Date parsedTimestamp = null;
        try {
            parsedTimestamp = datetimeFormatter.parse(node.path(jsonPath).textValue());
        } catch (ParseException e) {
            datetimeFormatter = new SimpleDateFormat("yyyy-mm-dd'T'hh:mm:ss");
            parsedTimestamp = datetimeFormatter.parse(node.path(jsonPath).textValue());
        }
        return new Timestamp(parsedTimestamp.getTime());
    }

    private String getImageUrl(JsonNode node) {
        JsonNode imagesArray = node.path("images");
        if (imagesArray.size() > 0) {
            return imagesArray.get(0).path("large").textValue();
        }
        return null;
    }

    private String getDetails(JsonNode node) {
        String details;
        details = node.path("details").textValue();
        if (details == null || details.length() == 0) {
            details = node.path("caution").textValue();
        }
        if (details != null) {
            details = details.replaceAll("<[^>]+>", "");
            details = details.replaceAll("\r\n", "");
        }

        return details;
    }


    private String getValuesAsStringFromArray(JsonNode node, String nodePath) {
        JsonNode array = node.path(nodePath);
        StringBuilder stringBuilder = new StringBuilder();
        if (array.size() > 0) {
            for (JsonNode value : array) {
                if (stringBuilder.length() == 0) {
                    stringBuilder.append(value.asText());
                } else {
                    stringBuilder.append(", " + value.asText());
                }
            }
        }
        return stringBuilder.toString();
    }

    private String getPosterUrl(JsonNode node) {
        JsonNode files = node.path("files");
        if (files.size() > 0) {
            for (JsonNode posterNode : files) {
                if (posterNode.path("name").equals("English")) {
                    return posterNode.path("url").textValue();
                }
            }
        }
        return null;
    }
}
