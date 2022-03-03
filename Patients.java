package com.example.psychesolutionapp.Utils;

public class Patients {
    private String fullname, profileImage, username, gender;
    Long age;

    public Patients(String fullname, String profileImage, String username, String gender, Long age) {
        this.fullname = fullname;
        this.profileImage = profileImage;
        this.username = username;
        this.gender = gender;
        this.age = age;
    }

    public Patients() {
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }
}
