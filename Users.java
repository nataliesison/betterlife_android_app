package com.example.psychesolutionapp.Utils;

public class Users {

    private String city, firstName, lastName, profileImage, username, status, usertype;

    public Users() {
    }

    public Users(String city, String firstName, String lastName, String profileImage, String username, String status, String usertype) {
        this.city = city;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileImage = profileImage;
        this.username = username;
        this.status = status;
        this.usertype = usertype;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
}
