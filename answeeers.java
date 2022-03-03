package com.example.psychesolutionapp.Utils;

public class answeeers {
    private String username, answer, date, time, profileImg;

    public answeeers() {
    }

    public answeeers(String username, String answer, String date, String time, String profileImg) {
        this.username = username;
        this.answer = answer;
        this.date = date;
        this.time = time;
        this.profileImg = profileImg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }
}
