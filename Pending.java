package com.example.psychesolutionapp;

public class Pending {
    public String pend_uid, pend_answer, pend_date, pend_time, pend_profileimage, pend_username;

    public Pending() {
    }

    public Pending(String pend_uid, String pend_answer, String pend_date, String pend_time, String pend_profileimage, String pend_username) {
        this.pend_uid = pend_uid;
        this.pend_answer = pend_answer;
        this.pend_date = pend_date;
        this.pend_time = pend_time;
        this.pend_profileimage = pend_profileimage;
        this.pend_username = pend_username;
    }

    public String getPend_uid() {
        return pend_uid;
    }

    public void setPend_uid(String pend_uid) {
        this.pend_uid = pend_uid;
    }

    public String getPend_answer() {
        return pend_answer;
    }

    public void setPend_answer(String pend_answer) {
        this.pend_answer = pend_answer;
    }

    public String getPend_date() {
        return pend_date;
    }

    public void setPend_date(String pend_date) {
        this.pend_date = pend_date;
    }

    public String getPend_time() {
        return pend_time;
    }

    public void setPend_time(String pend_time) {
        this.pend_time = pend_time;
    }

    public String getPend_profileimage() {
        return pend_profileimage;
    }

    public void setPend_profileimage(String pend_profileimage) {
        this.pend_profileimage = pend_profileimage;
    }

    public String getPend_username() {
        return pend_username;
    }

    public void setPend_username(String pend_username) {
        this.pend_username = pend_username;
    }
}
