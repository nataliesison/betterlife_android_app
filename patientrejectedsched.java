package com.example.psychesolutionapp;

public class patientrejectedsched {
    String DoctorID, PatientName, date, time, patientUid, profileImage, usernamee;

    public patientrejectedsched() {
    }

    public patientrejectedsched(String doctorID, String patientName, String date, String time, String patientUid, String profileImage, String usernamee) {
        this.DoctorID = doctorID;
        this.PatientName = patientName;
        this.date = date;
        this.time = time;
        this.patientUid = patientUid;
        this.profileImage = profileImage;
        this.usernamee = usernamee;
    }

    public String getDoctorID() {
        return DoctorID;
    }

    public void setDoctorID(String doctorID) {
        DoctorID = doctorID;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String patientName) {
        PatientName = patientName;
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

    public String getPatientUid() {
        return patientUid;
    }

    public void setPatientUid(String patientUid) {
        this.patientUid = patientUid;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getUsernamee() {
        return usernamee;
    }

    public void setUsernamee(String usernamee) {
        this.usernamee = usernamee;
    }
}
