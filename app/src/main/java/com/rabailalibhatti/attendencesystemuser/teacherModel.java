package com.rabailalibhatti.attendencesystemuser;

public class teacherModel {
    private String key;
    private String name;
    private String contact;
    private String username;
    private String password;

    public teacherModel() {
    }

    public teacherModel(String key, String name, String username, String password) {
        this.key = key;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public teacherModel(String key, String name, String contact, String username, String password) {
        this.key = key;
        this.name = name;
        this.contact = contact;
        this.username = username;
        this.password = password;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}