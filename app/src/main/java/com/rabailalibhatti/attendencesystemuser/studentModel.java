package com.rabailalibhatti.attendencesystemuser;

public class studentModel {
    private String key;
    private String name;
    private String rollNumber;
    private String batchKey;
    private String currentSemesterKey;
    private String userName;
    private String password;
    private Boolean passedOut;

    public studentModel() {
    }

    public studentModel(String key, String name, String rollNumber, String batchKey, String currentSemesterKey, String userName, String password) {
        this.key = key;
        this.name = name;
        this.rollNumber = rollNumber;
        this.batchKey = batchKey;
        this.currentSemesterKey = currentSemesterKey;
        this.userName = userName;
        this.password = password;
    }

    public studentModel(String key, String name, String rollNumber, String batchKey, String currentSemesterKey, String userName, String password, Boolean passedOut) {
        this.key = key;
        this.name = name;
        this.rollNumber = rollNumber;
        this.batchKey = batchKey;
        this.currentSemesterKey = currentSemesterKey;
        this.userName = userName;
        this.password = password;
        this.passedOut = passedOut;
    }

    public Boolean getPassedOut() {
        return passedOut;
    }

    public void setPassedOut(Boolean passedOut) {
        this.passedOut = passedOut;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getBatchKey() {
        return batchKey;
    }

    public void setBatchKey(String batchKey) {
        this.batchKey = batchKey;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCurrentSemesterKey() {
        return currentSemesterKey;
    }

    public void setCurrentSemesterKey(String currentSemesterKey) {
        this.currentSemesterKey = currentSemesterKey;
    }
}
