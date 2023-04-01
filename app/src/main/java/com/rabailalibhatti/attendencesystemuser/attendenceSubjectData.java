package com.rabailalibhatti.attendencesystemuser;

public class attendenceSubjectData {
    private String subjectKey;
    private String subjectName;
    private String semesterKey;
    private String status;
    private String dateee;

    public attendenceSubjectData() {
    }

    public attendenceSubjectData(String subjectKey, String subjectName, String semesterKey, String status) {
        this.subjectKey = subjectKey;
        this.subjectName = subjectName;
        this.semesterKey = semesterKey;
        this.status = status;
    }

    public attendenceSubjectData(String subjectKey, String subjectName, String semesterKey, String status, String dateee) {
        this.subjectKey = subjectKey;
        this.subjectName = subjectName;
        this.semesterKey = semesterKey;
        this.status = status;
        this.dateee = dateee;
    }

    public String getDateee() {
        return dateee;
    }

    public void setDateee(String dateee) {
        this.dateee = dateee;
    }

    public String getSubjectKey() {
        return subjectKey;
    }

    public void setSubjectKey(String subjectKey) {
        this.subjectKey = subjectKey;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSemesterKey() {
        return semesterKey;
    }

    public void setSemesterKey(String semesterKey) {
        this.semesterKey = semesterKey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
