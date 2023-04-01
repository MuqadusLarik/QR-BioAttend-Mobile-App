package com.rabailalibhatti.attendencesystemuser;

public class assignSubjectsShowList {
    private String subjectKey;
    private String subjectName;
    private String semesterName;

    public assignSubjectsShowList() {
    }

    public assignSubjectsShowList(String subjectKey, String subjectName, String semesterName) {
        this.subjectKey = subjectKey;
        this.subjectName = subjectName;
        this.semesterName = semesterName;
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

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }
}
