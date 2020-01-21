package com.techswitch.ispy.services;
import com.techswitch.ispy.Filter;
import com.techswitch.ispy.models.database.ReportDatabaseModel;
import com.techswitch.ispy.models.request.ReportRequestModel;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {

    private Jdbi jdbi;

    @Autowired
    public ReportService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public ReportDatabaseModel createReport(ReportRequestModel reportRequestModel) {
        return jdbi.withHandle(handle ->
                handle.createQuery("INSERT INTO reports (suspect_id, date_of_sighting, location, description, timestamp_submitted) " +
                        "VALUES (:suspectId, :date, :location, :description, :timestampSubmitted) " +
                        "RETURNING id, suspect_id, to_char(date_of_sighting, 'dd-mm-yyyy') as date_of_sighting, location, " +
                        "description, to_char(timestamp_submitted, 'dd-mm-yyyy  HH24:mm:ss') as timestamp_submitted")
                        .bind("suspectId", reportRequestModel.getSuspectId())
                        .bind("date", reportRequestModel.createSqlDate())
                        .bind("location", reportRequestModel.getLocation())
                        .bind("description", reportRequestModel.getDescription())
                        .bind("timestampSubmitted", Timestamp.valueOf(LocalDateTime.now()))
                        .mapToBean(ReportDatabaseModel.class)
                        .first()
        );
    }

    public List<ReportDatabaseModel> getAllReports(Filter filter){
        return jdbi.withHandle(handle -> handle.createQuery("select id, suspect_id, " +
                "to_char(date_of_sighting, 'dd-mm-yyyy') as date_of_sighting, location, description, " +
                "to_char(timestamp_submitted, 'dd-mm-yyyy  HH24:mm:ss') as timestamp_submitted " +
                "from reports LIMIT :limit OFFSET :offset")
                .bind("limit",filter.getPageSize())
                .bind("offset",filter.getOffset())
                .mapToBean(ReportDatabaseModel.class)
                .list());
    }
}
