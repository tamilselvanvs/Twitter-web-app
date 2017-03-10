/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import business.User;
import java.util.*;
import java.sql.*;

/**
 *
 * @author Soko
 */
public class FollowDB {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/mytwitter";
    static final String USERNAME = "root";        
    static final String PASSWORD = "sasi";
    
    public static long insert (int userID, int followID) {
        //implement insert into database
        try{
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            connection.setAutoCommit(false);
            
            String query = "INSERT INTO Follow (followerUserID, followedUserID, followDate)" +
                    " VALUES (" + userID + ", " + followID + ", NOW())";
            
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
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
    
    public static long delete (int userID, int followID) {
        //implement insert into database
        try{
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            connection.setAutoCommit(false);
            
            String query = "DELETE FROM Follow WHERE followerUserID = " + userID + " AND followedUserID = " + followID;
            System.out.println("Delete query is: " + query);
            
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
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
    
    public static boolean isFollowing (String userNickName, String otherNickName) {
        boolean isFollowing = false;
        
        //find out if userID is following otherUserID
        try{
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            int userID = UserDB.getUserID(userNickName);
            int otherUserID = UserDB.getUserID(otherNickName);
            String query = "SELECT followID FROM follow WHERE followerUserID = " + userID 
                                                        + " AND followedUserID = " + otherUserID;
            System.out.println("Query being executed for isFollowing: " + query);
            ResultSet results = statement.executeQuery(query);
            
            if(results.next())
                isFollowing = true;
            
            statement.close();
            connection.close();
        }catch(SQLException e) {
            for (Throwable t : e)
                t.printStackTrace();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("Value of isFollowing is: " + isFollowing);
        return isFollowing;
    }
    
    public static ArrayList<User> getNewFollowers (int userID) {
        ArrayList<User> newFollowers = new ArrayList<>();
        
        //get user's new followers
        try{
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE results AS "
                    + "SELECT User.UserID, User.FirstName, User.LastName, User.NickName, Follow.followerUserID, Follow.followedUserID, Follow.followDate "
                    + "FROM Follow "
                    + "INNER JOIN User "
                    + "ON Follow.followerUserID = User.UserID;";
            String query1 = "CREATE TABLE results1 AS SELECT UserID, LastLogin FROM user WHERE UserID = " + userID;
            String query2 = "SELECT * FROM results " 
                    + "INNER JOIN results1 "
                    + "ON results.followedUserID = results1.UserID " 
                    + "WHERE results.followDate > results1.LastLogin"
                    + " ORDER BY followDate;";
            String query3 = "DROP TABLE results;";
            String query4 = "DROP TABLE results1";
            
            statement.executeUpdate(query);
            statement.executeUpdate(query1);
            ResultSet results = statement.executeQuery(query2);
            
            while(results.next()) {
                User user = new User();
                user.setFirstName(results.getString("FirstName"));
                user.setLastName(results.getString("LastName"));
                user.setNickName(results.getString("NickName"));
                
                newFollowers.add(user);
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
        
        return newFollowers;
    }
    
    public static int getNumOfFollowers (String nickname) {
        int numOfFollowers = 0;
        
        try{
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            int userID = UserDB.getUserID(nickname);
            ResultSet results = statement.executeQuery("SELECT * FROM Follow WHERE followedUserID = '" + userID + "'");
            
            while(results.next()) {
                numOfFollowers++;   
            }
            statement.close();
            connection.close();
        }catch(SQLException e) {
            for (Throwable t : e)
                t.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
        
        return numOfFollowers;
    }
    
    public static int getNumOfFollowing (String nickname) {
        int numOfFollowing = 0;
        
        try{
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            int userID = UserDB.getUserID(nickname);
            ResultSet results = statement.executeQuery("SELECT * FROM Follow WHERE followerUserID = '" + userID + "'");
            
            while(results.next()) {
                numOfFollowing++;   
            }
            statement.close();
            connection.close();
        }catch(SQLException e) {
            for (Throwable t : e)
                t.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
        
        return numOfFollowing;
    }
}
