<%@page import="dataaccess.TweetDB"%>
<%@page import="dataaccess.FollowDB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/includes/header.jsp"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
    </head>
    
    <body>
        <br>
        <c:if test="${user.getFirstName() == null}">     
            <c:redirect url="/login.jsp" /> 
        </c:if> 
       
        <c:if test="${user.getFirstName() != null}">
            <h1>Hello ${user.firstName}!</h1>
        </c:if>
        
            <section class="user_info">
                <p>
                    <form method="post" action="Profilepic"
                      enctype="multipart/form-data">
                    <img src="images/Tulips.jpg" style="height:50px;width:50px" alt="Avatar"><br/>
                    </form>
                    <span class="fullname">${user.firstName} ${user.lastName}</span><br>
                    <span class="nickname">@${user.nickName}</span>
                </p>
                <p>
                    <span class="count"># of tweets: <c:out value="${TweetDB.numOfTweets(user.email)}" /> </span><br>
                    <span class="count"># of followers: <c:out value="${numOfFollowers}" /> </span><br>
                    <span class="count"># of following: <c:out value="${numOfFollowing}" /> </span>
                     
                </p>
            </section>
                    
            <div class="my_tweets">
                <form action="tweet" method="post">
                    <input type="hidden" name="action" value="tweetPost">
                    <input type="hidden" name="emailAddress" value="${user.email}">
                    <input type="hidden" name="nickName" value="${user.nickName}">
                    
                    <textarea rows="8" cols="67" name="tweet" placeholder="Tweet here (max: 200)" maxlength="200" class="tweet_box"></textarea>
                    <span><i class="post_msg">${message1}</i></span><br>
                    <input type="submit" value="Post" class="post">
                </form>
            </div>
            
            <section class="follow">
                <h2>Who to follow:</h2>
                <form action="membership" method="post">
                    <c:forEach items="${allUsers}" var="u" step="1">
                        <span class="fullname"> <c:out value="${u.firstName}"/> <c:out value="${u.lastName}" /></span>
                        
                        <c:choose>
                            <c:when test="${user.nickName.equals(u.nickName)}">
                                <span>&nbsp;</span><br>
                            </c:when>
                            <c:when test="${FollowDB.isFollowing(user.nickName, u.nickName) == true}">
                                <form action="membership" method="post">
                                    <input type="hidden" name="action" value="unfollow">
                                    <input type="hidden" name="username" value="${user.email}">
                                    <input type="hidden" name="userNickName" value="${user.nickName}">
                                    <input type="hidden" name="otherNickName" value="${u.nickName}">
                                    <input type="submit" value="Unfollow" class="little_button">
                                </form>
                            </c:when>
                            <c:otherwise>
                                <form action="membership" method="post">
                                    <input type="hidden" name="action" value="follow">
                                    <input type="hidden" name="username" value="${user.email}">
                                    <input type="hidden" name="userNickName" value="${user.nickName}">
                                    <input type="hidden" name="otherNickName" value="${u.nickName}">
                                    <input type="submit" value="Follow" class="little_button">
                                </form>
                            </c:otherwise>
                        </c:choose>
                        <span class="nickname"> @<c:out value="${u.nickName}" /> </span><br>
                        <br>
                    </c:forEach>
                </form>
                
            </section>
            
            <section class="trends">
                <h2>Trends</h2>
            </section>
            
            <section class="all_tweets">
                <h2>Tweets:</h2>
                <form action="tweet" method="post">
                    <c:forEach items="${allTweets}" var="t" step="1">
                        <span class="fullname"> ${t.firstName} ${t.lastName} </span>
                        &nbsp;
                        <c:if test="${user.getEmail().equals(t.emailAddress)}">
                            <input type="hidden" name="action" value="deleteTweet">
                            <input type="hidden" name="tweetID" value="${t.getTweetID()}">
                            <input type="hidden" name="emailAddress" value="${t.emailAddress}">
                            <input type="hidden" name="nickName" value="${t.nickName}">
                            <input type="submit" value="Delete" class="little_button">
                        </c:if>
                        <br>
                        <span class="nickname"> @${t.nickName}: ${t.currentDate} </span><br>
                        <span> ${t.tweet} </span>
                        <br>
                        <br>
                    </c:forEach>
                </form>
            </section>
    </body>
    <c:import url="/includes/footer.jsp" /> 
</html>