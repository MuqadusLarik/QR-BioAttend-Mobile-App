package com.rabailalibhatti.attendencesystemuser;

public class semesterModel {
    private String semKey;
    private String semTitle;
    private int semNumber;

    public semesterModel() {
    }

    public semesterModel(String semKey, String semTitle, int semNumber) {
        this.semKey = semKey;
        this.semTitle = semTitle;
        this.semNumber = semNumber;
    }

    public String getSemKey() {
        return semKey;
    }

    public void setSemKey(String semKey) {
        this.semKey = semKey;
    }

    public String getSemTitle() {
        return semTitle;
    }

    public void setSemTitle(String semTitle) {
        this.semTitle = semTitle;
    }

    public int getSemNumber() {
        return semNumber;
    }

    public void setSemNumber(int semNumber) {
        this.semNumber = semNumber;
    }
}
