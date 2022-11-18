package com.example.bloodprojectapplication.model;

public class Notification {
    private String userName;
    private String userUID;
    private String userEmail;
    private String bloodGroup;
    private String phoneNumber;

    public Notification() {

    }

    public Notification(String userName, String userUID, String userEmail, String bloodGroup, String phoneNumber) {
        this.userName = userName;
        this.userUID = userUID;
        this.userEmail = userEmail;
        this.bloodGroup = bloodGroup;
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
