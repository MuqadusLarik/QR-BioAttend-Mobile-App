package com.techsam.attendencesystemuser.objects;

import java.util.ArrayList;

public class Student {
    private String id;
    private String name;
    private String batch;
    private String rollNo;
    private String user;
    private String pass;
    private ArrayList<Subject> list;


    public Student(){

    }

    public Student(String id, String name, String batch, String rollNo, String user, String pass, ArrayList<Subject> list) {
        this.id = id;
        this.name = name;
        this.batch = batch;
        this.rollNo = rollNo;
        this.user = user;
        this.pass = pass;
        this.list = list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public ArrayList<Subject> getList() {
        return list;
    }

    public void setList(ArrayList<Subject> list) {
        this.list = list;
    }
}
