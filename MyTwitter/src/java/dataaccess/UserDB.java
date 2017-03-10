/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;
import util.*;
import business.User;
import java.io.*;
import java.sql.*;
//import java.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 *
 * @Sokana Boone
 */
public class UserDB {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/mytwitter";
    static final String USERNAME = "root";        
    static final String PASSWORD = "sasi";

    public static long insert(User user) {
      
        //implement insert into database
        try{
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            connection.setAutoCommit(false);
            //Need to do salt 
            String salt = PasswordUtil.getSalt();
            System.out.println("Salt for this user is: " + salt);
            String securePassword = PasswordUtil.hashPassword(user.getPassword() + salt);
            System.out.println("Hashed password is: " + securePassword);
            String birthday = user.getMonth() + " - " + user.getDay() + " - " + user.getYear();
            
            String query = "INSERT INTO User (Email, FirstName, LastName, Birthday, Nickname, Password, Salt)" +
                    " VALUES ('" + user.getEmail() + "', " +
                            "'" + user.getFirstName() +"', " +
                            "'" + user.getLastName() + "', " +
                            "'" + birthday + "', " +
                            "'" + user.getNickName() + "', " +
                            "'" + securePassword + "', " +
                            "'" + salt + "')";
            
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
    
    public static User select(String emailAddress)
    {
        //search in the database and find the User, if does not exist return null; if exists make a User object and return it.
        try{
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet userResult = statement.executeQuery("SELECT * FROM user WHERE email = " + "'" + emailAddress + "'");
            boolean userExists = userResult.next();
            
            if(userExists) {
                User user = new User();
                user.setFirstName(userResult.getString("FirstName"));
                user.setLastName(userResult.getString("LastName"));
                user.setEmail(userResult.getString("Email"));
                user.setPassword(userResult.getString("Password"));
                user.setNickName(userResult.getString("Nickname"));
                user.setUserID(userResult.getInt("UserID"));
                
                String birthday = userResult.getString("Birthday");
                String[] bdayParts = birthday.split("-");
                String month = bdayParts[0];
                String day = bdayParts[1];
                String year = bdayParts[2];
                
                user.setMonth(month);
                user.setDay(day);
                user.setYear(year);
                
                return user;
            }
            else {
                return null;
            }
        }catch(SQLException e) {
            for (Throwable t : e)
                t.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static ArrayList<User> selectAll()
    {
        ArrayList<User> users = new ArrayList<>();
        //select all users from database
        try{
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT FirstName, LastName, Nickname FROM User");
            
            while(results.next()) {
                User user = new User();
                user.setFirstName(results.getString("FirstName"));
                user.setLastName(results.getString("LastName"));
                user.setNickName(results.getString("Nickname"));
               
                users.add(user);   
            }
            statement.close();
            connection.close();
        }catch(SQLException e) {
            for (Throwable t : e)
                t.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return users;
    }
    
    public static boolean doesEmailExist(String email){
        boolean exists = false;
        
        //search database for email address - return true if it already exists
        try{
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT UserID FROM user WHERE email = " + "'" + email + "'");
            exists = result.next();
            statement.close();
            connection.close();
        }catch(SQLException e) {
            for (Throwable t : e)
                t.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return exists;
    }
    
    public static boolean validatePassword(String username, String password){
        boolean matches = false;
        
        //search database for email address - return true if it already exists
        try{
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT Password, Salt FROM user WHERE Email = " + "'" + username + "'");
            result.next();
            String storedPassword = result.getString("Password");
            String salt = result.getString("Salt");
            
            //get salt stored for user, hash with password user entered and see if it matches
            String hashedPassword = PasswordUtil.hashPassword(password + salt);
            if(storedPassword.equals(hashedPassword))
                matches = true;
            
            statement.close();
            connection.close();
        }catch(SQLException e) {
            for (Throwable t : e)
                t.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return matches;
    }
    
    public static long update(User user) {
      
        //implement insert into database
        try{
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            connection.setAutoCommit(false);
            String birthday = user.getMonth() + " - " + user.getDay() + " - " + user.getYear();
            String query = "UPDATE User" +
                    " SET FirstName = '" + user.getFirstName() +"', " +
                         "LastName = '" + user.getLastName() + "', " +
                         "Birthday = '" + birthday + "', " +
                         "Password = '" + PasswordUtil.hashPassword(user.getPassword() + (getSalt(user.getEmail()))) + "' " +
                    " WHERE Email = '" + user.getEmail() + "';";
            
            Statement statement = connection.createStatement();
            int rowCount = statement.executeUpdate(query);
            connection.commit();
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
    
    public static String newPassword(String username) {
        String password = "";
        
        //Temp password is their username
        try{
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            connection.setAutoCommit(false);
            
            //Temporary password is their username/email
            String query = "UPDATE User" +
                    " SET Password = '" + PasswordUtil.hashPassword(username + (getSalt(username))) + "' " +
                    " WHERE Email = '" + username + "';";
            
            Statement statement = connection.createStatement();
            int rowCount = statement.executeUpdate(query);
            connection.commit();
            statement.close();
            connection.close();
        }catch(SQLException e) {
            for (Throwable t : e)
                t.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
            
        return password;
    }
    
    public static void setLoginTime(String email) {
        try{
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            connection.setAutoCommit(false);
            
            //Temporary password is their username/email
            String query = "UPDATE user SET LastLogin = CurrentLogin WHERE Email = '" + email + "'";
            String query1 = "UPDATE user SET CurrentLogin = NOW() WHERE Email = '" + email + "'";
            
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.executeUpdate(query1);
            connection.commit();
            statement.close();
            connection.close();
        }catch(SQLException e) {
            for (Throwable t : e)
                t.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static int getUserID(String nickName) {
        int id = 0;
        
        try{
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT UserID FROM user WHERE Nickname = " + "'" + nickName + "'");
            result.next();
            id = result.getInt("UserID");
            
            statement.close();
            connection.close();
        }catch(SQLException e) {
            for (Throwable t : e)
                t.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
        
        return id;
    }
    
    public static Date getLastLogin(int userID) {
        Date login = null;
        
        try{
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT LastLogin FROM user WHERE UserID = " + userID);
            result.next();
            login = result.getDate("LastLogin");
            
            statement.close();
            connection.close();
        }catch(SQLException e) {
            for (Throwable t : e)
                t.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
        
        return login;
    }
    
    public static String getSalt(String username) {
        String salt = "";
        
        try{
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT Salt FROM user WHERE Email = " + "'" + username + "'");
            result.next();
            salt = result.getString("Salt");
            
            statement.close();
            connection.close();
        }catch(SQLException e) {
            for (Throwable t : e)
                t.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
        
        return salt;
    }
}
