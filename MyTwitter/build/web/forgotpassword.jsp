<%-- 
    Document   : forgotpassword
    Created on : Jul 20, 2016, 9:41:56 PM
    Author     : Soko
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/includes/header.jsp"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forgot Password</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
    </head>
    <body>
        <br>
        <h1>Forgot Password</h1>
        <p><i>${message}</i></p>
        
        <form action="membership" method="post">
            <input type="hidden" name="action" value="forgotPassword">
            
            <input class="margin_left" type="email" name="username" placeholder="Username/Email" required><br>
            
            <input type="submit" value="Submit" class="margin_left"> 
        </form>
        
    </body>
    <c:import url="/includes/footer.jsp" />
</html>
