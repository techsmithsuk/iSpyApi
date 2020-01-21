package com.techswitch.ispy.services;

import com.techswitch.ispy.Filter;
import com.techswitch.ispy.models.database.SuspectDatabaseModel;
import com.techswitch.ispy.models.request.SuspectFbiRequestModel;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SuspectsService {

    private Jdbi jdbi;
    private final String UPDATE_SUSPECT_QUERY_SCRIPT = "INSERT INTO suspects (title, date_of_birth, hair, eyes, height, weight, sex, race, nationality, scars_and_marks, reward_text, caution, details, warning_message, fbi_uid, modified, publication) \n" +
            "values (:title, :dateOfBirth, :hair, :eyes, :height, :weight, :sex, :race, :nationality, :scarsAndMarks, :rewardText, :caution, :details, :warningMessage, :fbiUid, :modified, :publication) ON CONFLICT (fbi_uid) DO NOTHING RETURNING id;";

    private final String UPDATE_SUSPECT_PHOTO_SCRIPT = "INSERT INTO suspect_photo_urls (suspectId, original, thumb, large, caption) VALUES (:suspectId, :original, :thumb, :large, :caption);";

    @Autowired
    public SuspectsService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public List<SuspectDatabaseModel> getAllSuspects(Filter filter) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM suspects LIMIT :limit OFFSET :offset")
                .bind("limit", filter.getPageSize())
                .bind("offset", filter.getOffset())
                .mapToBean(SuspectDatabaseModel.class)
                .list());
    }

    public Optional<SuspectDatabaseModel> getSuspectById(int id) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM suspects WHERE id = :id ")
                .bind("id", id)
                .mapToBean(SuspectDatabaseModel.class)
                .findOne());
    }

    public int addSuspectsAndReturnNumberOfInsertedSuspects(List<SuspectFbiRequestModel> suspects) throws ParseException {
        int rowsInserted = 0;
        for (SuspectFbiRequestModel suspect : suspects) {
            rowsInserted += addSuspect(suspect);
        }
        return rowsInserted;
    }

    public int addSuspect(SuspectFbiRequestModel suspect) throws ParseException {
        Optional<Long> suspectId = null;
        try (Handle handle = jdbi.open()) {
            suspectId = handle.createQuery(UPDATE_SUSPECT_QUERY_SCRIPT)
                    .bind("title", suspect.getTitle())
                    .bind("dateOfBirth", suspect.getDatesOfBirthUsedAsSingleString())
                    .bind("hair", suspect.getHair())
                    .bind("eyes", suspect.getEyes())
                    .bind("height", suspect.getHeightMin())
                    .bind("weight", suspect.getWeight())
                    .bind("sex", suspect.getSex())
                    .bind("race", suspect.getRace())
                    .bind("nationality", suspect.getNationality())
                    .bind("scarsAndMarks", suspect.getScarsAndMarks())
                    .bind("rewardText", suspect.getRewardText())
                    .bind("caution", suspect.getCaution())
                    .bind("details", suspect.getDetails())
                    .bind("warningMessage", suspect.getWarningMessage())
                    .bind("fbiUid", suspect.getUid())
                    .bind("modified", suspect.getModifiedAsTimestamp())
                    .bind("publication", suspect.getPublicationAsTimestamp())
                    .mapTo(Long.class)
                    .findOne();
            if (!suspectId.isPresent()) {
                return 0;
            }

            for (Map<String, String> image : suspect.getImages()) {
                Optional<Long> finalSuspectId = suspectId;
                handle.createUpdate(UPDATE_SUSPECT_PHOTO_SCRIPT)
                        .bind("suspectId", finalSuspectId.get())
                        .bind("original", image.get("original"))
                        .bind("thumb", image.get("thumb"))
                        .bind("large", image.get("large"))
                        .bind("caption", image.get("caution"))
                        .execute();
            }
        }
        return 1;
    }
}
