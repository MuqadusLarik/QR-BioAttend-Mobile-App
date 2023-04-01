package com.rabailalibhatti.attendencesystemuser;

import java.util.HashMap;

public class AttendenceObj {
    private String subName;
    private String subId;
    private String teacherName;
    private String teacherId;
    private String date;
    private HashMap<String, PresentAbsent> list;

    public AttendenceObj(String subName, String subId, String teacherName, String teacherId, String date, HashMap<String, PresentAbsent> list) {
        this.subName = subName;
        this.subId = subId;
        this.teacherName = teacherName;
        this.teacherId = teacherId;
        this.date = date;
        this.list = list;
    }
    public AttendenceObj() {}

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

    public HashMap<String, PresentAbsent> getList() {
        return list;
    }

    public void setList(HashMap<String, PresentAbsent> list) {
        this.list = list;
    }
}
