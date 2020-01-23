package com.techswitch.ispy.models.api;

import com.techswitch.ispy.models.database.ReportDatabaseModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReportApiModel {

    private Long id;
    private Long suspectId;
    private String dateOfSighting;
    private String location;
    private String description;
    private String timestampSubmitted;

    public ReportApiModel(ReportDatabaseModel reportDatabaseModel) {
        this.id = reportDatabaseModel.getId();
        this.suspectId = reportDatabaseModel.getSuspectId();
        this.dateOfSighting = getDateFormatted(reportDatabaseModel.getDateOfSighting());
        this.location = reportDatabaseModel.getLocation();
        this.description = reportDatabaseModel.getDescription();
        this.timestampSubmitted = getTimestampFormatted(reportDatabaseModel.getTimestampSubmitted());
    }

    private String getTimestampFormatted(LocalDateTime timestampSubmitted) {
        return timestampSubmitted.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"));
    }

    private String getDateFormatted(LocalDate dateOfSighting) {
        return dateOfSighting.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }


    public Long getId() {
        return id;
    }

    public Long getSuspectId() {
        return suspectId;
    }

    public String getDateOfSighting() {
        return dateOfSighting;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getTimestampSubmitted() {
        return timestampSubmitted;
    }
}
