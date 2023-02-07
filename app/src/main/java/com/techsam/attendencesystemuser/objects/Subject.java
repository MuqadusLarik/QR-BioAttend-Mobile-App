package com.techsam.attendencesystemuser.objects;

public class Subject {
    private String subId;
    private String subName;
    private String subCode;
    private String creditHours;

    public Subject(String subId, String subName, String subCode) {
        this.subId = subId;
        this.subName = subName;
        this.subCode =subCode;
    }
    public Subject() {
    }

    public Subject(String subId, String subName, String subCode, String creditHours) {
        this.subId = subId;
        this.subName = subName;
        this.subCode = subCode;
        this.creditHours = creditHours;
    }

    public String getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(String creditHours) {
        this.creditHours = creditHours;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {this.subName = subName;}

    public String getSubCode() {return subCode;}

    public void setSubCode(String subCode) {this.subCode = subCode;}
}
