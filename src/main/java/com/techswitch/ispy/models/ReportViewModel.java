package com.techswitch.ispy.models;

public class ReportViewModel {

    private Long id;
    private Long suspectId;
    private String dateOfSighting;
    private String location;
    private String description;
    private String timestampSubmitted;

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

    public String getDateOfSighting() {
        return dateOfSighting;
    }

    public void setDateOfSighting(String dateOfSighting) {
        this.dateOfSighting = dateOfSighting;
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

    public String getTimestampSubmitted() {
        return timestampSubmitted;
    }

    public void setTimestampSubmitted(String timestampSubmitted) {
        this.timestampSubmitted = timestampSubmitted;
    }
}
