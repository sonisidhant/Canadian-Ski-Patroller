package com.example.nidal.project;

public class User {

    public String firstName;
    public String email;
    public String lastName;




    public String leaderFlag;
    public boolean onlineFlag;
    public String phoneNumber;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email, String firstName, String lastName, String leaderFlag, boolean onlineFlag, String phoneNumber) {
        this.firstName = firstName;
        this.email = email;
        this.lastName = lastName;
        this.leaderFlag = leaderFlag;
        this.phoneNumber = phoneNumber;
        this.onlineFlag = onlineFlag;
    }

    public String getLeaderFlag() {
        return leaderFlag;
    }

    public void setLeaderFlag(String leaderFlag) {
        this.leaderFlag = leaderFlag;
    }

}