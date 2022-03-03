package com.example.psychesolutionapp;

public class QuestionsPending
{
    public String usernamee, uid, profileImage, question, time, date, selectedDept, selectedSubject, pushID;

    public QuestionsPending() {
    }

    public QuestionsPending(String usernamee, String uid, String profileImage, String question, String time, String date, String selectedDept, String selectedSubject, String pushID) {
        this.usernamee = usernamee;
        this.uid = uid;
        this.profileImage = profileImage;
        this.question = question;
        this.time = time;
        this.date = date;
        this.selectedDept = selectedDept;
        this.selectedSubject = selectedSubject;
        this.pushID = pushID;
    }

    public String getUsernamee() {
        return usernamee;
    }

    public void setUsernamee(String usernamee) {
        this.usernamee = usernamee;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSelectedDept() {
        return selectedDept;
    }

    public void setSelectedDept(String selectedDept) {
        this.selectedDept = selectedDept;
    }

    public String getSelectedSubject() {
        return selectedSubject;
    }

    public void setSelectedSubject(String selectedSubject) {
        this.selectedSubject = selectedSubject;
    }

    public String getPushID() {
        return pushID;
    }

    public void setPushID(String pushID) {
        this.pushID = pushID;
    }
}

