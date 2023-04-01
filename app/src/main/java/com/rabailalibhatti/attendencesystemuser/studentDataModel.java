package com.rabailalibhatti.attendencesystemuser;

public class studentDataModel {
    private String studentKey;
    private String batchKey;
    private String semesterKey;

    public studentDataModel(String studentKey, String batchKey, String semesterKey) {
        this.studentKey = studentKey;
        this.batchKey = batchKey;
        this.semesterKey = semesterKey;
    }

    public studentDataModel() {
    }

    public String getStudentKey() {
        return studentKey;
    }

    public void setStudentKey(String studentKey) {
        this.studentKey = studentKey;
    }

    public String getBatchKey() {
        return batchKey;
    }

    public void setBatchKey(String batchKey) {
        this.batchKey = batchKey;
    }

    public String getSemesterKey() {
        return semesterKey;
    }

    public void setSemesterKey(String semesterKey) {
        this.semesterKey = semesterKey;
    }
}
