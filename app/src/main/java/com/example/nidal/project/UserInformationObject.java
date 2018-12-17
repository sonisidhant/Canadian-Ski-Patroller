package com.example.nidal.project;

public class UserInformationObject {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String leaderFlag;
    //private Boolean onlineFlag;

    public UserInformationObject(){

    }

    public UserInformationObject(String firstName, String lastName, String email, String phoneNumber, String leaderFlag){//, Boolean onlineFlag) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.leaderFlag = leaderFlag;
        //this.onlineFlag = onlineFlag;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLeaderFlag() {
        return leaderFlag;
    }

    public void setLeaderFlag(String leaderFlag) {
        this.leaderFlag = leaderFlag;
    }
}
