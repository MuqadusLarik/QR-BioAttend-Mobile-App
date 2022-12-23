package com.techsam.attendencesystemuser;

public class Teacher {
    private String name;
    private String qualification;
    private String age;
    private String username;
    private String password;

    public Teacher(String name, String qualification, String age, String username, String password) {
        this.name = name;
        this.qualification = qualification;
        this.age = age;
        this.username = username;
        this.password = password;
    }
    public Teacher() {
    }

    public Teacher(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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
}
