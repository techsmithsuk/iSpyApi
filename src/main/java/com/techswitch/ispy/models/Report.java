package com.techswitch.ispy.models;

import com.techswitch.ispy.models.validator.ValidDate;

import javax.validation.constraints.NotNull;

public class Report {

    private Long suspectId;
    @ValidDate
    private String date;
    private String location;
    @NotNull
    private String description;

    public Report() {
    }

    public Report(Long suspectId, String date, String location, String description) {
        this.suspectId = suspectId;
        this.date = date;
        this.location = location;
        this.description = description;
    }

    public Long getSuspectId() {
        return suspectId;
    }

    public void setSuspectId(Long suspectId) {
        this.suspectId = suspectId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
}
