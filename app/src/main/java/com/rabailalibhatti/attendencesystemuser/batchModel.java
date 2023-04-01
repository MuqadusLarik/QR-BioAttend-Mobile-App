package com.rabailalibhatti.attendencesystemuser;

public class batchModel {
    private String key;
    private String batchTitle;
    private String startYear;
    private String endYear;
    private String batchStatus;

    public batchModel() {
    }

    public batchModel(String key, String batchTitle, String startDate, String endDate) {
        this.key = key;
        this.batchTitle = batchTitle;
        this.startYear = startDate;
        this.endYear = endDate;
    }

    public batchModel(String key, String batchTitle, String startDate, String endDate, String batchStatus) {
        this.key = key;
        this.batchTitle = batchTitle;
        this.startYear = startDate;
        this.endYear = endDate;
        this.batchStatus = batchStatus;
    }

    public String getKey() {
        return key;
    }

    public String getBatchStatus() {
        return batchStatus;
    }

    public void setBatchStatus(String batchStatus) {
        this.batchStatus = batchStatus;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getBatchTitle() {
        return batchTitle;
    }

    public void setBatchTitle(String batchTitle) {
        this.batchTitle = batchTitle;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getEndYear() {
        return endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }
}
