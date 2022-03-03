package com.example.psychesolutionapp;

public class signupInfo {

    private String FirstName;
    private String LastName;
    private String EmailAddress;
    private String Password;
    private String Conpassword;

    public signupInfo(){

    }

    public String getFirstName(){
        return FirstName;
    }
    public void setFirstName(String firstName){
       FirstName = firstName;
    }

    public String getLastName(){
        return LastName;
    }
    public void setLastName(String lastName){
        LastName = lastName;
    }

    public String getEmailAddress(){
        return EmailAddress;
    }
    public void setEmailAddress(String emailAddress){
        EmailAddress = emailAddress;
    }

    public String getPassword(){
        return Password;
    }
    public void setPassword(String password){
        Password = password;
    }

    public String getConpassword(){
        return Conpassword;
    }
    public void setConpassword(String conpassword){
        Conpassword = conpassword;
    }


    public signupInfo(String firstName, String lastName, String emailAddress, String password, String conpassword){
        FirstName = firstName;
        LastName = lastName;
        EmailAddress = emailAddress;
        Password = password;
        Conpassword = conpassword;
    }
}
