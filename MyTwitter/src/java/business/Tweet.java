/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;
import java.io.Serializable;
import java.util.*;

/**
 *
 * @author Soko
 */
public class Tweet implements Serializable {
    //attributes
    private String emailAddress;
    private String tweet;
    private String firstName;
    private String lastName;
    private String nickName;
    private int tweetID;
    private int mentionedUserID;
    private Date currentDate;
    
    public Tweet() {
        emailAddress = "";
        tweet = "";
        firstName = "";
        lastName = "";
        nickName = "";
        tweetID = 0;
        mentionedUserID = 0;
        currentDate = null;
    }
    
    public Tweet(String emailAddress, String tweet) {
        this.emailAddress = emailAddress;
        this.tweet = tweet;
    }
    
    //get/set methods for all attributes
    public String getEmailAddress() {
        return emailAddress;
    }
    
    public String getTweet() {
        return tweet;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    public String getNickName() {
        return nickName;
    }
    
    public int getTweetID() {
        return tweetID;
    }
    
    public int getMentionedUserID() {
        return mentionedUserID;
    }
    
    public Date getCurrentDate() {
        return currentDate;
    }
    
    public void setEmailAddress(String emailAddress){
        this.emailAddress = emailAddress;
    }
    
    public void setTweet(String tweet) {
        this.tweet = tweet;
    }
    
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public void setNickName(String nickName){
        this.nickName = nickName;
    }
    
    public void setTweetID(int tweetID) {
        this.tweetID = tweetID;
    }
    
    public void setMentionedUserID(int mentionedUserID) {
        this.mentionedUserID = mentionedUserID;
    }
    
    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }
}
