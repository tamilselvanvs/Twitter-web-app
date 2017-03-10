<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <form method="post" action="membership">
            <input type="hidden" name="action" value="homepage">
            <input type="hidden" name="username" value="${user.getEmail()}">
            <input type="submit" value="Home" class="header">
        </form>

        <form method="post" action="membership">
            <input type="hidden" name="action" value="notifications">
            <input type="hidden" name="nickname" value="${user.getNickName()}">
            <input type="submit" value="Notifications" class="header">
        </form>
        
        <form method="post" action="membership">
            <input type="hidden" name="action" value="viewProfile">
            <input type="hidden" name="username" value="${user.getEmail()}">
            <input type="submit" value="Profile" class="header">
        </form>
            
        <form method="post" action="membership">
            <input type="hidden" name="action" value="logout">  
            <input type="submit" value="Logout" class="logout"> 
        </form>    
        
    </body>
</html>
