package com.rabailalibhatti.attendencesystemuser;

import java.util.List;

public class teacherSubjectsModel {
    private String teacherName;
    private List<String> subKeys;

    public teacherSubjectsModel() {
    }

    public teacherSubjectsModel(String teacherName, List<String> subKeys) {
        this.teacherName = teacherName;
        this.subKeys = subKeys;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public List<String> getSubKeys() {
        return subKeys;
    }

    public void setSubKeys(List<String> subKeys) {
        this.subKeys = subKeys;
    }
}
