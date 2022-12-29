package com.techsam.attendencesystemuser.objects;

public class Batch {
    private String bid;
    private String batchTitle;
    private String startYear;
    private String endYear;

    public Batch(String bid, String batchTitle, String startYear) {
        this.bid = bid;
        this.batchTitle = batchTitle;
        this.startYear = startYear;
    }
    public Batch(){

    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
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

}
