package com.techsam.attendencesystemuser.objects;

public class Batch {
    private String bid;
    private String batchTitle;
    private String startYear;
    private String endYear;

    public Batch(String bid, String batchTitle, String startYear, String endYear) {
        this.bid = bid;
        this.batchTitle = batchTitle;
        this.startYear = startYear;
        this.endYear = endYear;
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

    public String getEndYear() {return endYear;}

    public void setEndYear(String endYear) {this.endYear = endYear;}
}
