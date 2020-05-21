/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author sam-7
 */
public class User {
    public String userID;
    private String firstName;
    private String lastName;
    private String email;
    private String staffID;
    private String department;
    private String userType;
    private String password;

    public User(String userID, String firstName, String lastName, String email, String staffID, String department, String userType, String password) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.staffID = staffID;
        this.department = department;
        this.userType = userType;
        this.password = password;
        System.out.println("User: " + this.firstName);
    }
    

    public User() {
    }
   
    
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
    
    

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" + "userID=" + userID + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", staffID=" + staffID + ", department=" + department + ", userType=" + userType + ", password=" + password + '}';
    }
    
    

    
    
    
}








