package com.example.psychesolutionapp;

public class pendingsched {

    public String date, firstName, lastName, profileImage, time, uid, usernamee;

    public pendingsched() {
    }

    public pendingsched(String date, String firstName, String lastName, String profileImage, String time, String uid, String usernamee) {
        this.date = date;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileImage = profileImage;
        this.time = time;
        this.uid = uid;
        this.usernamee = usernamee;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsernamee() {
        return usernamee;
    }

    public void setUsernamee(String usernamee) {
        this.usernamee = usernamee;
    }
}
