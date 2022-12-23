package com.techsam.attendencesystemuser;

public class Student {
    private String name;
    private String surname;
    private String fName;
    private String cnic;
    private String cell;
    private String studentClass;
    private String batch;
    private String rollNo;
    private String semester;
    private String username;
    private String password;


    public Student(){

    }

    public Student(String name, String surname, String fName, String cnic, String cell, String studentClass, String batch, String rollNo, String semester, String username, String password) {
        this.name = name;
        this.surname = surname;
        this.fName = fName;
        this.cnic = cnic;
        this.cell = cell;
        this.studentClass = studentClass;
        this.batch = batch;
        this.rollNo = rollNo;
        this.semester = semester;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
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

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
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
