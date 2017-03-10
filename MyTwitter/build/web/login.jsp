<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Log In</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
    </head>
    <body>
        
        <h1>Log In</h1>
        <p><i>${message}</i></p>
        
        <div>
            <form action="membership" method="post">
                <input type="hidden" name="action" value="login">
                
                <input class="margin_left" type="email" name="userName" placeholder="Email/Username" autofocus required><br>
                
                <input class="margin_left" type="password" name="logPassword" placeholder="Password" required><br>
            
                <input type="submit" value="Log In" class="margin_left"> 
                <a class="link" href="forgotpassword.jsp">Forgot password?</a><br>
            </form>
                
            <p>
                <span class="link">New?</span><a class="link" href="signup.jsp">Sign up here!</a>
            </p>   
           
        </div>
    </body>
    <c:import url="/includes/footer.jsp" /> 
</html>
