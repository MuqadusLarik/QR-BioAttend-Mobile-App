package com.rabailalibhatti.attendencesystemuser;

import java.util.HashMap;

public class StudentAttendenceModel {
    private String attendenceKey;
    private String subjectKey;
    private String teacherKey;
    private String date;
    private String status;
    private HashMap<String, String> AttendenceRecord;
    private int totalNumber;
    private int presentCount;
    private int absentCount;
    private int leaveCount;

    public StudentAttendenceModel() {
    }

    public StudentAttendenceModel(String date) {
        this.date = date;
    }

    public StudentAttendenceModel(String subjectKey, String teacherKey) {
        this.subjectKey = subjectKey;
        this.teacherKey = teacherKey;
    }

    public StudentAttendenceModel(String attendenceKey, String subjectKey, String teacherKey) {
        this.attendenceKey = attendenceKey;
        this.subjectKey = subjectKey;
        this.teacherKey = teacherKey;
    }

    public StudentAttendenceModel(String attendenceKey, String subjectKey, String date, HashMap<String, String> attendenceRecord) {
        this.attendenceKey = attendenceKey;
        this.subjectKey = subjectKey;
        this.date = date;
        AttendenceRecord = attendenceRecord;
    }

    public StudentAttendenceModel(String attendenceKey, String subjectKey, String teacherKey, String date, HashMap<String, String> attendenceRecord) {
        this.attendenceKey = attendenceKey;
        this.subjectKey = subjectKey;
        this.teacherKey = teacherKey;
        this.date = date;
        AttendenceRecord = attendenceRecord;
    }

    public StudentAttendenceModel(String attendenceKey, String subjectKey, String teacherKey, String date, String status, HashMap<String, String> attendenceRecord) {
        this.attendenceKey = attendenceKey;
        this.subjectKey = subjectKey;
        this.teacherKey = teacherKey;
        this.date = date;
        this.status = status;
        AttendenceRecord = attendenceRecord;
    }

    public StudentAttendenceModel(String attendenceKey, String subjectKey, String teacherKey, String date, String status, HashMap<String, String> attendenceRecord, int totalNumber, int presentCount, int absentCount, int leaveCount) {
        this.attendenceKey = attendenceKey;
        this.subjectKey = subjectKey;
        this.teacherKey = teacherKey;
        this.date = date;
        this.status = status;
        AttendenceRecord = attendenceRecord;
        this.totalNumber = totalNumber;
        this.presentCount = presentCount;
        this.absentCount = absentCount;
        this.leaveCount = leaveCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAttendenceKey() {
        return attendenceKey;
    }

    public void setAttendenceKey(String attendenceKey) {
        this.attendenceKey = attendenceKey;
    }

    public String getSubjectKey() {
        return subjectKey;
    }

    public void setSubjectKey(String subjectKey) {
        this.subjectKey = subjectKey;
    }

    public String getTeacherKey() {
        return teacherKey;
    }

    public void setTeacherKey(String teacherKey) {
        this.teacherKey = teacherKey;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public HashMap<String, String> getAttendenceRecord() {
        return AttendenceRecord;
    }

    public void setAttendenceRecord(HashMap<String, String> attendenceRecord) {
        AttendenceRecord = attendenceRecord;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public int getPresentCount() {
        return presentCount;
    }

    public void setPresentCount(int presentCount) {
        this.presentCount = presentCount;
    }

    public int getAbsentCount() {
        return absentCount;
    }

    public void setAbsentCount(int absentCount) {
        this.absentCount = absentCount;
    }

    public int getLeaveCount() {
        return leaveCount;
    }

    public void setLeaveCount(int leaveCount) {
        this.leaveCount = leaveCount;
    }
}
