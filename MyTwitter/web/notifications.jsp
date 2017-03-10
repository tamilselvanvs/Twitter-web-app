<%-- 
    Document   : notifications
    Created on : Jul 24, 2016, 1:50:54 PM
    Author     : Soko
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/includes/header.jsp"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Notifications</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
    </head>
    <body>
        <br>
        <section class="all_tweets">
            <h2>New Tweets:</h2>
            <form action="membership" method="post">
                <input type="hidden" name="action" value="notifications">
                <c:forEach items="${newTweets}" var="tweet" step="1">
                    <span class="fullname"> <c:out value="${tweet.firstName}"/> <c:out value="${tweet.lastName}" /></span><br>
                    <span class="nickname"> @<c:out value="${tweet.nickName}" />: <c:out value="${tweet.currentDate}"/> </span><br>
                    <span> ${tweet.tweet} </span><br>
                    <br>
                </c:forEach>
            </form>
        </section>
        
        <section class="all_tweets">
            <h2>New Followers:</h2>
            <form action="membership" method="post">
                <input type="hidden" name="action" value="notifications"
                <c:forEach items="${newFollowers}" var="u" step="1">
                    <span class="fullname"> <c:out value="${u.firstName}"/> <c:out value="${u.lastName}" /></span><br>
                    <span class="nickname"> @<c:out value="${u.nickName}" /> </span><br>
                    <br>
                </c:forEach>
            </form>
        </section>
    </body>
    <c:import url="/includes/footer.jsp" />
</html>
