package com.example.psychesolutionapp;

public class listDiagnosis {
    public String Patient_ID, Session_Pat,  Patient_name, Patient_gender, date_diagnosis, Diagnosis, Started_Time, Ended_Time;
    Long Patient_age;

    public listDiagnosis() {
    }

    public listDiagnosis(String patient_ID, String session_Pat, Long patient_age, String patient_name, String patient_gender, String date_diagnosis, String diagnosis, String started_Time, String ended_Time) {
        this.Patient_ID = patient_ID;
        this.Session_Pat = session_Pat;
        this.Patient_age = patient_age;
        this.Patient_name = patient_name;
        this.Patient_gender = patient_gender;
        this.date_diagnosis = date_diagnosis;
        this.Diagnosis = diagnosis;
        this.Started_Time = started_Time;
        this.Ended_Time = ended_Time;
    }

    public String getSession_Pat() {
        return Session_Pat;
    }

    public void setSession_Pat(String session_Pat) {
        Session_Pat = session_Pat;
    }

    public String getPatient_ID() {
        return Patient_ID;
    }

    public void setPatient_ID(String patient_ID) {
        Patient_ID = patient_ID;
    }

    public Long getPatient_age() {
        return Patient_age;
    }

    public void setPatient_age(Long patient_age) {
        Patient_age = patient_age;
    }

    public String getPatient_name() {
        return Patient_name;
    }

    public void setPatient_name(String patient_name) {
        Patient_name = patient_name;
    }

    public String getPatient_gender() {
        return Patient_gender;
    }

    public void setPatient_gender(String patient_gender) {
        Patient_gender = patient_gender;
    }

    public String getDate_diagnosis() {
        return date_diagnosis;
    }

    public void setDate_diagnosis(String date_diagnosis) {
        this.date_diagnosis = date_diagnosis;
    }

    public String getDiagnosis() {
        return Diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        Diagnosis = diagnosis;
    }

    public String getStarted_Time() {
        return Started_Time;
    }

    public void setStarted_Time(String started_Time) {
        Started_Time = started_Time;
    }

    public String getEnded_Time() {
        return Ended_Time;
    }

    public void setEnded_Time(String ended_Time) {
        Ended_Time = ended_Time;
    }
}
