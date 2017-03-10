/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;
import business.Tweet;
import java.io.*;
import java.sql.*;
import java.util.*;


/**
 *
 * @author Soko
 */
public class TweetDB {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/mytwitter";
    static final String USERNAME = "root";        
    static final String PASSWORD = "sasi";
    
    public static long insert(Tweet tweet) {
      
        //implement insert into database
        try{
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            connection.setAutoCommit(false);
            
            String t = tweet.getTweet();
            if(t.contains("@")){
                String[] tParts = t.split(" ");
                for(int i = 0; i < tParts.length; i++) {
                    int start = tParts[i].indexOf("@");
                    if(start >= 0) {
                        int start1 = (start + 1);
                        String nickname = tParts[i].substring(start1);
                        int id = UserDB.getUserID(nickname);
                        tweet.setMentionedUserID(id);
                    }
                } 
            }
            String query = "INSERT INTO Tweet (SenderEmail, CurrentDate, Tweet, mentionedUserID)" +
                    " VALUES ('" + tweet.getEmailAddress() + "', " +
                            "NOW(), " +
                            "'" + tweet.getTweet() + "', " +
                            "'" + tweet.getMentionedUserID() + "')";
            
            Statement statement = connection.createStatement();
            int rowCount = statement.executeUpdate(query);
            connection.commit();
            statement.close();
            connection.close();
        }catch(SQLException e) {
            for (Throwable t : e)
                t.printStackTrace();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public static ArrayList<Tweet> selectAll(String username, int id)
    {
        ArrayList<Tweet> tweets = new ArrayList<>();
        //get all tweets that a user posted and is mentioned in
        try{
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE results AS "
                    + "SELECT User.Email, User.FirstName, User.LastName, User.NickName, Tweet.CurrentDate, Tweet.Tweet, Tweet.TweetID, Tweet.mentionedUserID "
                    + "FROM Tweet "
                    + "INNER JOIN User "
                    + "ON Tweet.SenderEmail = User.Email;";
            String query1 = "SELECT * FROM results " 
                    + "WHERE Email = '" + username + "' OR mentionedUserID = " + id 
                    + " ORDER BY CurrentDate;";
            String query2 = "DROP TABLE results;";
            
            statement.executeUpdate(query);
            ResultSet results = statement.executeQuery(query1);
            
            while(results.next()) {
                Tweet tweet = new Tweet();
                tweet.setEmailAddress(results.getString("Email"));
                tweet.setCurrentDate(results.getDate("CurrentDate"));
                tweet.setTweet(results.getString("Tweet"));
                tweet.setFirstName(results.getString("FirstName"));
                tweet.setLastName(results.getString("LastName"));
                tweet.setNickName(results.getString("NickName"));
                tweet.setTweetID(results.getInt("TweetID"));
                tweet.setMentionedUserID(results.getInt("mentionedUserID"));
                
                String t = tweet.getTweet();
                if(t.contains("@") || t.contains("#")){
                    String[] parts = t.split(" ");
                    for(int i = 0; i < parts.length; i++) {
                        int start = parts[i].indexOf("@");
                        int start1 = parts[i].indexOf("#");
                        if(start >= 0 || start1 >= 0) {
                            parts[i] = "<span class="+"blue"+">" + parts[i] + "</span>";
                        }
                    }
                    String result = Arrays.toString(parts);  
                    String newTweet = result.replace("[", "");
                    String newTweet1 = newTweet.replace(",", "");
                    String newTweet2 = newTweet1.replace("]", "");
                    tweet.setTweet(newTweet2);
                }
                tweets.add(tweet);
            }
            statement.executeUpdate(query2);
            statement.close();
            connection.close();
        }catch(SQLException e) {
            for (Throwable t : e)
                t.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return tweets;
    }
    
    public static ArrayList<Tweet> getNewTweets (int userID) {
        ArrayList<Tweet> newTweets = new ArrayList<>();
        
        //get all tweets that a user has been mentioned in since last login
        try{
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE results AS "
                    + "SELECT User.Email, User.FirstName, User.LastName, User.NickName, Tweet.CurrentDate, Tweet.Tweet, Tweet.TweetID, Tweet.mentionedUserID "
                    + "FROM Tweet "
                    + "INNER JOIN User "
                    + "ON Tweet.SenderEmail = User.Email;";
            String query1 = "CREATE TABLE results1 AS SELECT UserID, LastLogin FROM user WHERE UserID = " + userID;
            String query2 = "SELECT * FROM results " 
                    + "INNER JOIN results1 "
                    + "ON results.mentionedUserID = results1.UserID " 
                    + "WHERE results.CurrentDate > results1.LastLogin"
                    + " ORDER BY CurrentDate;";
            String query3 = "DROP TABLE results;";
            String query4 = "DROP TABLE results1";
            
            statement.executeUpdate(query);
            statement.executeUpdate(query1);
            ResultSet results = statement.executeQuery(query2);
            System.out.println("The query to get results: " + query2);
            System.out.println("User ID being submitted for query: " + userID);
            while(results.next()) {
                System.out.println("There are results");
                Tweet tweet = new Tweet();
                tweet.setEmailAddress(results.getString("Email"));
                tweet.setCurrentDate(results.getDate("CurrentDate"));
                tweet.setTweet(results.getString("Tweet"));
                tweet.setFirstName(results.getString("FirstName"));
                tweet.setLastName(results.getString("LastName"));
                tweet.setNickName(results.getString("NickName"));
                tweet.setTweetID(results.getInt("TweetID"));
                tweet.setMentionedUserID(results.getInt("mentionedUserID"));

                String t = tweet.getTweet();
                System.out.println("Tweet from result set: " + t);
                if(t.contains("@") || t.contains("#")){
                    String[] parts = t.split(" ");
                    for(int i = 0; i < parts.length; i++) {
                        int start = parts[i].indexOf("@");
                        int start1 = parts[i].indexOf("#");
                        if(start >= 0 || start1 >= 0) {
                            parts[i] = "<span class="+"blue"+">" + parts[i] + "</span>";
                        }
                    }
                    String result = Arrays.toString(parts);  
                    String newTweet = result.replace("[", "");
                    String newTweet1 = newTweet.replace(",", "");
                    String newTweet2 = newTweet1.replace("]", "");
                    tweet.setTweet(newTweet2);
                }
                newTweets.add(tweet);
            }
            
            statement.executeUpdate(query3);
            statement.executeUpdate(query4);
            statement.close();
            connection.close();
        }catch(SQLException e) {
            for (Throwable t : e)
                t.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
        
        return newTweets;
    }
    
    public static int numOfTweets(String emailAddress) {
        int numOfTweets = 0;
        
        try{
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM Tweet WHERE SenderEmail = '" + emailAddress + "'");
            
            while(results.next()) {
                numOfTweets++;   
            }
            statement.close();
            connection.close();
        }catch(SQLException e) {
            for (Throwable t : e)
                t.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
        
        return numOfTweets;
    }
    
    public static long delete(int tweetID) {
       
        try{
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            String query = "DELETE FROM tweet WHERE TweetID = " + tweetID;
            
            System.out.println(query);
            int rowCount = statement.executeUpdate(query);
            statement.close();
            connection.close();
        }catch(SQLException e) {
            for (Throwable t : e)
                t.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
