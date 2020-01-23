package com.techswitch.ispy.models.database;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReportDatabaseModel {

    private Long id;
    private Long suspectId;
    private LocalDate dateOfSighting;
    private String location;
    private String description;
    private LocalDateTime timestampSubmitted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSuspectId() {
        return suspectId;
    }

    public void setSuspectId(Long suspectId) {
        this.suspectId = suspectId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateOfSighting() {
        return dateOfSighting;
    }

    public void setDateOfSighting(LocalDate dateOfSighting) {
        this.dateOfSighting = dateOfSighting;
    }

    public LocalDateTime getTimestampSubmitted() {
        return timestampSubmitted;
    }

    public void setTimestampSubmitted(LocalDateTime timestampSubmitted) {
        this.timestampSubmitted = timestampSubmitted;
    }
}
