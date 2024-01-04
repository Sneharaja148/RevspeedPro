package com.revature.revspeed.project.model;
import com.revature.revspeed.project.dbconnection.Dbconnection;


public class User {
    private int userId;
    private String username;
    private String password;
    private String phoneNo;
    private String email;
    private String address;

    public User() {

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User(int userId, String username, String password, String phoneNo, String email, String address) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.phoneNo = phoneNo;
        this.email = email;
        this.address = address;
    }
// Constructors, getters, and setters

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }


}

