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

    private final String UPDATE_SUSPECT_QUERY_SCRIPT = "INSERT INTO suspects (title, date_of_birth, hair, eyes, height, weight, sex, race, nationality, scars_and_marks, reward_text, caution, details, warning_message, fbi_uid, modified, publication) \n" +
            "values (:title, :dateOfBirth, :hair, :eyes, :height, :weight, :sex, :race, :nationality, :scarsAndMarks, :rewardText, :caution, :details, :warningMessage, :fbiUid, :modified, :publication) ON CONFLICT (fbi_uid) DO NOTHING RETURNING id;";

    private final String UPDATE_SUSPECT_PHOTO_SCRIPT = "INSERT INTO suspect_photo_urls (suspectId, original, thumb, large, caption) VALUES (:suspectId, :original, :thumb, :large, :caption);";

    private final String SELECT_ALL_SUSPECTS_SCRIPT = "select distinct on (suspects.id) " +
            "suspects.id, suspects.title, suspects.date_of_birth, suspects.hair, suspects.eyes, suspects.height, " +
            "suspects.weight, suspects.sex, suspects.race, suspects.nationality, suspects.scars_and_marks, " +
            "suspects.reward_text, suspects.caution, suspects.details, suspects.warning_message, suspects.fbi_uid, " +
            "suspects.modified, suspects.publication, suspect_photo_urls.original as image_url\n" +
            "from " +
            "suspects " +
            "inner join " +
            "suspect_photo_urls on suspect_photo_urls.suspectid = suspects.id " +
            "order by suspects.id, suspect_photo_urls.id asc " +
            "LIMIT :limit OFFSET :offset;";

    private Jdbi jdbi;

    @Autowired
    public SuspectsService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public List<SuspectDatabaseModel> getAllSuspects(Filter filter) {
        return jdbi.withHandle(handle -> handle.createQuery(SELECT_ALL_SUSPECTS_SCRIPT)
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

    public int countSuspects() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT COUNT(*) FROM suspects")
                .mapTo(Integer.class)
                .one());
    }
}
