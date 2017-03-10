/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;
import java.io.Serializable;

/**
 *
 * @javabean for User Entity
 */
public class User implements Serializable {
    //attributes
    private String firstName;
    private String lastName;
    private String email;
    private String nickName;
    private String password;
    private String month;
    private String day;
    private String year;
    private int userID;
    private String picFilename;
    
    public User() {
        firstName = "";
        lastName = "";
        email = "";
        nickName = "";
        password = "";
        month = "";
        day = "";
        year = "";
        userID = 0;
        picFilename = "";
    }
    
    public User(String firstName, String lastName, String email, String password, String month, String day, String year, String nickName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.month = month;
        this.day = day;
        this.year = year;
        this.nickName = nickName;
    }
    
    //set/get methods for all attributes.
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
    
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getMonth() {
        return month;
    }
    
    public void setMonth(String month){
        this.month = month;
    }
    
    public String getDay() {
        return day;
    }
    
    public void setDay(String day){
        this.day = day;
    }
    public String getYear() {
        return year;
    }
    
    public void setYear(String year){
        this.year = year;
    }
    
    public int getUserID() {
        return userID;
    }
    
    public void setUserID(int userID) {
        this.userID = userID;
    }
    
    public String getPicFilename() {
        return picFilename;
    }
    
    public void setPicFilename(String picFilename) {
        this.picFilename = picFilename;
    }
}
