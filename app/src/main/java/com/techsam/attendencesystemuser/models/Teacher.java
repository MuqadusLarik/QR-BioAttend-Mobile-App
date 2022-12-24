package com.techsam.attendencesystemuser.models;

import java.util.ArrayList;

public class Teacher {
    private String id;
    private String name;
    private String user;
    private String pass;
    private ArrayList<Subject> list;

    public Teacher() {
    }


    public Teacher(String id, String name, String user, String pass, ArrayList<Subject> list) {
        this.id = id;
        this.name = name;
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
