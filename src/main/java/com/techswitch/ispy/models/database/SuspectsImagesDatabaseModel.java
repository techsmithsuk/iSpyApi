package com.techswitch.ispy.models.database;

public class SuspectsImagesDatabaseModel {

    private Long id;
    private Long suspectId;
    private String original;
    private String thumb;
    private String large;
    private String caption;


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

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
