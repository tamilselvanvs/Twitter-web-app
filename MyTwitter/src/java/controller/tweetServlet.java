/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import business.Tweet;
import business.User;
import dataaccess.TweetDB;
import dataaccess.UserDB;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.*;

/**
 *
 * @author Soko
 */
@WebServlet(name = "tweetServlet", urlPatterns = {"/tweet"})
public class tweetServlet extends HttpServlet {

    
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
        
        //get current action
        String action = request.getParameter("action");
        
        String url = "/home.jsp";
        
        if(action.equals("tweetPost")) {
            //get parameters
            String emailAddress = request.getParameter("emailAddress");
            String nickName = request.getParameter("nickName");
            String tweet = request.getParameter("tweet");
            
            if(tweet.length() < 1){
                String message1 = "Your post cannot be blank.";
                url = "/home.jsp";
                
                request.setAttribute("message1", message1);
            }
            else {
                //Store data in Tweet object
                Tweet t = new Tweet();
                t.setEmailAddress(emailAddress);
                t.setTweet(tweet);

                TweetDB.insert(t);
                int numOfTweets = TweetDB.numOfTweets(emailAddress);
                int userID = UserDB.getUserID(nickName);

                ArrayList<Tweet> allTweets = TweetDB.selectAll(emailAddress, userID);
                ArrayList<User> allUsers = UserDB.selectAll();

                HttpSession session = request.getSession();
                session.setAttribute("tweet", t);
                session.setAttribute("allTweets", allTweets);
                session.setAttribute("numOfTweets", numOfTweets);
                session.setAttribute("allUsers", allUsers);

                request.setAttribute("allUsers", allUsers);
                request.setAttribute("numOfTweets", numOfTweets);
                request.setAttribute("allTweets", allTweets);
                request.setAttribute("tweet", t); 
            }
        }
        else if(action.equals("deleteTweet")) {
            int tweetID = Integer.parseInt(request.getParameter("tweetID"));
            TweetDB.delete(tweetID);
            
            String emailAddress = request.getParameter("emailAddress");
            String nickName = request.getParameter("nickName");
            int userID = UserDB.getUserID(nickName);
            
            ArrayList<Tweet> allTweets = TweetDB.selectAll(emailAddress, userID);
            ArrayList<User> allUsers = UserDB.selectAll();
            
            request.setAttribute("allUsers", allUsers);
            request.setAttribute("allTweets", allTweets);
            
            url = "/home.jsp";
        }
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
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
