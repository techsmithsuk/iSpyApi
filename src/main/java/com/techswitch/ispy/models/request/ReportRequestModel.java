package com.techswitch.ispy.models.request;

import com.techswitch.ispy.models.request.validator.ValidDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.techswitch.ispy.models.request.validator.DateValidator.DATE_FORMAT_INPUT;

public class ReportRequestModel {
    @NotNull(message = "Suspect ID cannot be null")
    private Long suspectId;
    @ValidDate(message = "Please use a YYYY-MM-DD format for date")
    private String date;
    private String location;
    @NotEmpty(message = "Description cannot be empty")
    private String description;

    public ReportRequestModel() {
    }

    public ReportRequestModel(Long suspectId, String date, String location, String description) {
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

    public LocalDate createSqlDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_INPUT);
        return LocalDate.parse(date, formatter);
    }
}
