package com.techswitch.ispy.services;

import com.techswitch.ispy.Filter;
import com.techswitch.ispy.models.database.SuspectDatabaseModel;
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
    private final String UPDATE_SUSPECT_QUERY_SCRIPT = "INSERT INTO suspects (dates_of_birth_used, hair, eyes, " +
            "height_min, height_max, weight, sex, race, nationality, scars_and_marks, reward_text, caution, details, " +
            "warning_message, uid, modified, publication) \n" +
            "values (:datesOfBirthUsed, :hair, :eyes, :heightMin, :heightMax, :weight, :sex, :race, :nationality, " +
            ":scarsAndMarks, :rewardText, :caution, :details, :warningMessage, :uid, :modified, :publication) ON CONFLICT (uid) DO NOTHING RETURNING id;";

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

    public int addSuspectsAndReturnNumberOfInsertedSuspects(List<SuspectDatabaseModel> suspects) throws ParseException {
        int rowsInserted = 0;
        for (SuspectDatabaseModel suspect : suspects) {
            rowsInserted += addSuspect(suspect);
        }
        return rowsInserted;
    }


    public int addSuspect(SuspectDatabaseModel suspect) throws ParseException {
        Optional<Long> suspectId = null;
        try (Handle handle = jdbi.open()) {
            suspectId = handle.createQuery(UPDATE_SUSPECT_QUERY_SCRIPT)
                    .bind("datesOfBirthUsed", suspect.getDatesOfBirthUsedAsSingleString())
                    .bind("hair", suspect.getHair())
                    .bind("eyes", suspect.getEyes())
                    .bind("heightMin", suspect.getHeightMin())
                    .bind("heightMax", suspect.getHeightMax())
                    .bind("weight", suspect.getWeight())
                    .bind("sex", suspect.getSex())
                    .bind("race", suspect.getRace())
                    .bind("nationality", suspect.getNationality())
                    .bind("scarsAndMarks", suspect.getScarsAndMarks())
                    .bind("rewardText", suspect.getRewardText())
                    .bind("caution", suspect.getCaution())
                    .bind("details", suspect.getDetails())
                    .bind("warningMessage", suspect.getWarningMessage())
                    .bind("uid", suspect.getUid())
                    .bind("modified", suspect.getModifiedAsTimestamp())
                    .bind("publication", suspect.getPublicationAsTimestamp())
                    .mapTo(Long.class)
                    .findOne();
            if(!suspectId.isPresent()){
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

    private boolean isSuspectPresentInDatabase(SuspectDatabaseModel suspect, Handle handle) {
        return handle.createQuery("SELECT EXISTS(SELECT 1 FROM suspects where uid = :uid);")
                .bind("uid", suspect.getUid())
                .mapTo(Boolean.class)
                .one();
    }
}
