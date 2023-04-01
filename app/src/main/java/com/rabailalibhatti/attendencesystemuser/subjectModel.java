package com.rabailalibhatti.attendencesystemuser;

public class subjectModel {
    private String subKey;
    private String subTitle;
    private String subCode;
    private String subCreditHours;
    private String semesterKey;
    private String assignedTo;

    public subjectModel() {
    }

    public subjectModel(String subKey, String subTitle, String subCode, String subCreditHours, String semesterKey, String assignedTo) {
        this.subKey = subKey;
        this.subTitle = subTitle;
        this.subCode = subCode;
        this.subCreditHours = subCreditHours;
        this.semesterKey = semesterKey;
        this.assignedTo = assignedTo;
    }

    public String getSubKey() {
        return subKey;
    }

    public void setSubKey(String subKey) {
        this.subKey = subKey;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getSubCreditHours() {
        return subCreditHours;
    }

    public void setSubCreditHours(String subCreditHours) {
        this.subCreditHours = subCreditHours;
    }

    public String getSemesterKey() {
        return semesterKey;
    }

    public void setSemesterKey(String semesterKey) {
        this.semesterKey = semesterKey;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }
}
