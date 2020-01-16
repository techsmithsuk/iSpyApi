package com.techswitch.ispy.services;
import com.techswitch.ispy.models.Report;
import com.techswitch.ispy.models.ReportViewModel;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class ReportService {

    private Jdbi jdbi;

    @Autowired
    public ReportService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public ReportViewModel createReport(Report report) {
        return jdbi.withHandle(handle ->
                handle.createQuery("INSERT INTO reports (suspect_id, date_of_sighting, location, description, timestamp_submitted) " +
                        "VALUES (:suspectId, :date, :location, :description, :timestampSubmitted) " +
                        "RETURNING id, suspect_id, to_char(date_of_sighting, 'dd-mm-yyyy') as date_of_sighting, location, " +
                        "description, to_char(timestamp_submitted, 'dd-mm-yyyy  HH24:mm:ss') as timestamp_submitted")
                        .bind("suspectId", report.getSuspectId())
                        .bind("date", report.createSqlDate())
                        .bind("location", report.getLocation())
                        .bind("description", report.getDescription())
                        .bind("timestampSubmitted", Timestamp.valueOf(LocalDateTime.now()))
                        .mapToBean(ReportViewModel.class)
                        .first()
        );
    }
}
