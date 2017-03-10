package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.mail.MessagingException;

import business.*;
import dataaccess.*;
import java.security.NoSuchAlgorithmException;
import util.*;

import java.util.*;

/**
 *
 * @Sokana Boone
 */
@WebServlet(name = "membershipServlet", urlPatterns = {"/membership"})
public class membershipServlet extends HttpServlet {
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // get current action
        String action = request.getParameter("action");
        
        String url = "/login.jsp";
        String message = "";
        //default action
        if(action == null) {
            action = "login";
        }
        
        if(action.equals("login")) {
            url = login(request, response);
            if(url.equals("/login.jsp")){
                message = message.concat("Username/Password not found. Use the link below to sign up if you haven't joined. ");
            }
            else {
                String username = request.getParameter("userName");
                User user = UserDB.select(username);
                UserDB.setLoginTime(username);
                
                ArrayList<User> allUsers = UserDB.selectAll();
                ArrayList<Tweet> allTweets = TweetDB.selectAll(user.getEmail(), user.getUserID());
                int numOfFollowers = FollowDB.getNumOfFollowers(user.getNickName());
                int numOfFollowing = FollowDB.getNumOfFollowing(user.getNickName());
                
                request.setAttribute("allUsers", allUsers);
                request.setAttribute("allTweets", allTweets);
                request.setAttribute("numOfFollowing", numOfFollowing);
                request.setAttribute("numOfFollowers", numOfFollowers);
            }
        }
        else if (action.equals("add")) {
            // get parameters from the request
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String month = request.getParameter("month");
            String day = request.getParameter("day");
            String year = request.getParameter("year");
            String nickName = request.getParameter("nickName");
            String password = request.getParameter("password");
           
            //store data in User object
            User user = new User(firstName, lastName, email, password, month, day, year, nickName);

            // validate the parameters
            if (firstName == null || lastName == null || email == null || password == null || month == null || day == null || year == null ||
                firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || month.isEmpty() || day.isEmpty() || year.isEmpty()) {
                message = message.concat("Please fill out all required fields. ");
                url = "/signup.jsp";
            } 
            else if(((month.equals("02") || month.equals("2")) && (day.equals("30") || day.equals("31"))) ||
                    ((month.equals("04") || month.equals("4")) && day.equals("31")) ||
                    ((month.equals("06") || month.equals("6")) && day.equals("31")) ||
                    ((month.equals("09") || month.equals("9")) && day.equals("31")) ||
                     (month.equals("11") && day.equals("31"))) {
                message = message.concat("Invalid birthday. ");
                url = "/signup.jsp";
            }
            else if(password.length() < 7){
                message = message.concat("Password must be at least 7 characters long. ");
                url = "/signup.jsp";
            }
            else if(!email.contains(".")) {
                message = message.concat("Invalid email address. ");
                url = "/signup.jsp";
            }
            else if(UserDB.doesEmailExist(email)) {
                message = message.concat("Email address is already being used. Please choose another. ");
                
                url = "/signup.jsp";
            }
            else {
                url = "/home.jsp";
                UserDB.insert(user);
                ArrayList<User> allUsers = UserDB.selectAll();
                int numOfFollowers = FollowDB.getNumOfFollowers(user.getNickName());
                int numOfFollowing = FollowDB.getNumOfFollowing(user.getNickName());
                request.setAttribute("allUsers", allUsers);
                request.setAttribute("numOfFollowing", numOfFollowing);
                request.setAttribute("numOfFollowers", numOfFollowers);
                
            }
        
            //Store User object as a session attribute
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
                    
            request.setAttribute("user", user);
        }
        else if(action.equals("viewProfile")) {
            String email = request.getParameter("username");
            User user = UserDB.select(email);
            request.setAttribute("user", user);
            url = "/signup2.jsp";
        }
        else if(action.equals("homepage")) {
            String username = request.getParameter("username");
            User user = UserDB.select(username);
            ArrayList<User> allUsers = UserDB.selectAll();
            ArrayList<Tweet> allTweets = TweetDB.selectAll(user.getEmail(), user.getUserID());
            int numOfFollowers = FollowDB.getNumOfFollowers(user.getNickName());
            int numOfFollowing = FollowDB.getNumOfFollowing(user.getNickName());
            request.setAttribute("allUsers", allUsers);
            request.setAttribute("allTweets", allTweets);
            request.setAttribute("numOfFollowing", numOfFollowing);
            request.setAttribute("numOfFollowers", numOfFollowers);
            url = "/home.jsp";
        }
        else if(action.equals("updateProfile")) {
            url = updateProfile(request, response);
        }
        else if(action.equals("logout")) {
            HttpSession session = request.getSession();
            session.removeAttribute("user");
            session.invalidate();
            url = "/login.jsp";
        }
        else if(action.equals("follow")) {
            System.out.println("Action: follow");
            String username = request.getParameter("username");
            String userNickName = request.getParameter("userNickName");
            String otherNickName = request.getParameter("otherNickName");
            
            System.out.println("User's nickname: " + userNickName + "Other nickname: " + otherNickName);
            
            //get IDs of both users and insert into follow table
            int userID = UserDB.getUserID(userNickName);
            int followID = UserDB.getUserID(otherNickName);
            FollowDB.insert(userID, followID);
            
            User user = UserDB.select(username);
            ArrayList<User> allUsers = UserDB.selectAll();
            ArrayList<Tweet> allTweets = TweetDB.selectAll(user.getEmail(), user.getUserID());
            int numOfFollowers = FollowDB.getNumOfFollowers(user.getNickName());
            int numOfFollowing = FollowDB.getNumOfFollowing(user.getNickName());
            request.setAttribute("allUsers", allUsers);
            request.setAttribute("allTweets", allTweets);
            request.setAttribute("numOfFollowing", numOfFollowing);
            request.setAttribute("numOfFollowers", numOfFollowers);
            url = "/home.jsp";
        }
        else if(action.equals("unfollow")) {
            System.out.println("Action: unfollow");
            String username = request.getParameter("username");
            String userNickName = request.getParameter("userNickName");
            String otherNickName = request.getParameter("otherNickName");
            
            System.out.println("User's nickname: " + userNickName + "Other nickname: " + otherNickName);
            
            //get IDs of both users and insert into follow table
            int userID = UserDB.getUserID(userNickName);
            int followID = UserDB.getUserID(otherNickName);
            FollowDB.delete(userID, followID);
            
            User user = UserDB.select(username);
            ArrayList<User> allUsers = UserDB.selectAll();
            ArrayList<Tweet> allTweets = TweetDB.selectAll(user.getEmail(), user.getUserID());
            int numOfFollowers = FollowDB.getNumOfFollowers(user.getNickName());
            int numOfFollowing = FollowDB.getNumOfFollowing(user.getNickName());
            request.setAttribute("allUsers", allUsers);
            request.setAttribute("allTweets", allTweets);
            request.setAttribute("numOfFollowing", numOfFollowing);
            request.setAttribute("numOfFollowers", numOfFollowers);
            url = "/home.jsp";
        }
        else if(action.equals("notifications")) {
            String nickname = request.getParameter("nickname");
            int userID = UserDB.getUserID(nickname);
            ArrayList<Tweet> newTweets = TweetDB.getNewTweets(userID);
            ArrayList<User> newFollowers = FollowDB.getNewFollowers(userID);
            request.setAttribute("newFollowers", newFollowers);
            request.setAttribute("newTweets", newTweets);
            url = "/notifications.jsp";
        }
        else if(action.equals("forgotPassword")) {
            url = forgotPassword(request, response);
            if(url.equals("/forgotpassword.jsp")) {
                message = message.concat("Invalid username/email. Please try again.");
            }
            else{
                //Code to send email
                String username = request.getParameter("username");
                String to = username;
                String from = "email@mytwitter.com";
                String subject = "Your new password";
                String body = "Your temporary password for your MyTwitter account is " + username + ". "
                        + "Please change this password as soon as possible my clicking on Profile after signing in.";
                boolean isBodyHTML = false;
                try{
                    MailUtilGmail.sendMail(to, from, subject, body, isBodyHTML);
                }
                catch (MessagingException e){
                    String errorMessage = "ERROR: Unable to send email. Error message: " + e.getMessage();
                    request.setAttribute("errorMessage", errorMessage);
                }
            }
        }
        
        request.setAttribute("message", message);
            
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    
    private String login(HttpServletRequest request, HttpServletResponse response){
        //get and set values entered for username and password 
        String userName = request.getParameter("userName");
        String logPassword = request.getParameter("logPassword");
        
        HttpSession session = request.getSession();
        session.setAttribute("userName", userName);
        session.setAttribute("logPassword", logPassword);
        
        String url;
        //Check to see if username/email exists
        if(UserDB.doesEmailExist(userName)){
            //Create User based on username entered
            User user = UserDB.select(userName);
            
            //Validate password
            if(UserDB.validatePassword(userName, logPassword)) {
                session.setAttribute("user", user);
                url = "/home.jsp";
            }
            else {
                url = "/login.jsp";
            }   
        }
        //Username/email is not in database
        else {
            url = "/login.jsp";
        }
        return url;
    }
    
    private String updateProfile(HttpServletRequest request, HttpServletResponse response) {
        String message = "";
        String url = "";
        
        //get parameters
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");
        String month = request.getParameter("month");
        String day = request.getParameter("day");
        String year = request.getParameter("year");
        String email = request.getParameter("email");
        //Create User object
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setMonth(month);
        user.setDay(day);
        user.setYear(year);
        user.setEmail(email);
        
        // validate the parameters
            if (firstName == null || lastName == null || password == null || month == null || day == null || year == null ||
                firstName.isEmpty() || lastName.isEmpty() || password.isEmpty() || month.isEmpty() || day.isEmpty() || year.isEmpty()) {
                message = message.concat("Please fill out all required fields. ");
                url = "/signup2.jsp";
            } 
            else if(((month.equals("02") || month.equals("2")) && (day.equals("30") || day.equals("31"))) ||
                    ((month.equals("04") || month.equals("4")) && day.equals("31")) ||
                    ((month.equals("06") || month.equals("6")) && day.equals("31")) ||
                    ((month.equals("09") || month.equals("9")) && day.equals("31")) ||
                     (month.equals("11") && day.equals("31"))) {
                message = message.concat("Invalid birthday. ");
                url = "/signup2.jsp";
            }
            else if(password.length() < 7){
                message = message.concat("Password must be at least 7 characters long. ");
                url = "/signup2.jsp";
            }
            else {
                url = "/home.jsp";
                UserDB.update(user);
                ArrayList<User> allUsers = UserDB.selectAll();
                ArrayList<Tweet> allTweets = TweetDB.selectAll(user.getEmail(), user.getUserID());
                int numOfFollowers = FollowDB.getNumOfFollowers(user.getNickName());
                int numOfFollowing = FollowDB.getNumOfFollowing(user.getNickName());
                request.setAttribute("allUsers", allUsers);
                request.setAttribute("allTweets", allTweets);
                request.setAttribute("numOfFollowing", numOfFollowing);
                request.setAttribute("numOfFollowers", numOfFollowers);
            }
            
            request.setAttribute("message", message);
            request.setAttribute("user", user);
            return url;
    }
    
    public String forgotPassword(HttpServletRequest request, HttpServletResponse response) {
        String url;
        String username = request.getParameter("username");
        boolean isValid = UserDB.doesEmailExist(username);
        if(isValid) {
            UserDB.newPassword(username);
            url = "/login.jsp";
        }
        else {
            url = "/forgotpassword.jsp";
        }
        return url;
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
