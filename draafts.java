package com.example.psychesolutionapp;

public class draafts {

    public String article, date, profileImage, time, title, uid, usernamee;

    public draafts() {
    }

    public draafts(String article, String date, String profileImage, String time, String title, String uid, String usernamee) {
        this.article = article;
        this.date = date;
        this.profileImage = profileImage;
        this.time = time;
        this.title = title;
        this.uid = uid;
        this.usernamee = usernamee;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
