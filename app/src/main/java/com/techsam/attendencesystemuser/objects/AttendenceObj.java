package com.techsam.attendencesystemuser.objects;

import java.util.ArrayList;

public class AttendenceObj {
    private String subName;
    private String subId;
    private String teacherName;
    private String teacherId;
    private String date;
    private ArrayList<PresentAbsent> list;

    public AttendenceObj(){

    }

    public AttendenceObj(String subName, String subId, String teacherName, String teacherId, String date, ArrayList<PresentAbsent> list) {
        this.subName = subName;
        this.subId = subId;
        this.teacherName = teacherName;
        this.teacherId = teacherId;
        this.date = date;
        this.list = list;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<PresentAbsent> getList() {
        return list;
    }

    public void setList(ArrayList<PresentAbsent> list) {
        this.list = list;
    }
}
