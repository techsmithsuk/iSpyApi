package com.techswitch.ispy.services;

import com.techswitch.ispy.models.Report;
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

    public Long createReport(Report report){
        try{
           return jdbi.withHandle(handle ->
                    handle.createQuery("INSERT INTO reports (suspect_id, date_of_sighting, location, description, timestamp_submitted) " +
                            "VALUES (:suspectId, :date::date, :location, :description, :timestampSubmitted::timestamp) RETURNING id")
                            .bind("suspectId", report.getSuspectId())
                            .bind("date", report.getDate())
                            .bind("location", report.getLocation())
                            .bind("description", report.getDescription())
                            .bind("timestampSubmitted", Timestamp.valueOf(LocalDateTime.now()))
                            .mapTo(Long.class)
                            .findOne()
                            .get()
            );
        }catch (Exception e){
            System.err.println(e.getMessage());
            return 0L;
        }
    }
}
