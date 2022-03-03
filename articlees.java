package com.example.psychesolutionapp;

public class articlees {

    public String usernamee, uid, profileImage, article, title, time, date;

    public articlees() {
    }

    public articlees(String usernamee, String uid, String profileImage, String article, String title, String time, String date) {
        this.usernamee = usernamee;
        this.uid = uid;
        this.profileImage = profileImage;
        this.article = article;
        this.title = title;
        this.time = time;
        this.date = date;
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

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
