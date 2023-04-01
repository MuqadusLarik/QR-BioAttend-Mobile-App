package com.rabailalibhatti.attendencesystemuser;

public class PresentAbsent {
    private String studentId;
    private String studentName;
    private String status;//present, absent


    public PresentAbsent() {
    }

    public PresentAbsent(String studentId, String studentName, String status) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.status = status;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
