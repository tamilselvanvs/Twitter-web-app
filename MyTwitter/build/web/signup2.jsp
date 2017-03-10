<%-- 
    Document   : signup2
    Created on : Jul 24, 2016, 2:48:21 PM
    Author     : Soko
--%>

<!DOCTYPE html>
<html>
    <%@page contentType="text/html" pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <c:import url="/includes/header.jsp"/>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profile</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
    </head>
    <body>
        <br>    
    <h1>Profile</h1>

        <p>To update your profile, use the form below:</p>
        <p><i>${message}</i></p>

        <form action="membership" method="post">
            <input type="hidden" name="action" value="updateProfile">

            <label class="pad_top">First Name:</label>
            <input type="text" name="firstName" value="${user.getFirstName()}" required><br>

            <label class="pad_top">Last Name:</label>
            <input type="text" name="lastName" value="${user.getLastName()}" required><br>

            <label class="pad_top">Nickname:</label>
            <input type="text" class="readonly" name="nickName" value="${user.getNickName()}" readonly><br>

            <label class="pad_top">Email:</label>
            <input type="email" class="readonly" name="email" value="${user.getEmail()}" readonly><br>

            <label class="pad_top">Password:</label>
            <input type="password" name="password" value="${user.getPassword()}" min="7" required><br>

            <label class="pad_top">Birthdate</label><br>
            <label class="pad_top">(mm/dd/yyyy):</label>
                <select name="month" value ="${user.getMonth()}" required>
                    <option value=""></option>
                    <option value="1">01</option>
                    <option value="2">02</option>
                    <option value="3">03</option>
                    <option value="4">04</option>
                    <option value="5">05</option>
                    <option value="6">06</option>
                    <option value="7">07</option>
                    <option value="8">08</option>
                    <option value="9">09</option>
                    <option value="10">10</option>
                    <option value="11">11</option>
                    <option value="12">12</option>
                </select>
                <select name="day" value="${user.getDay()}" required>
                    <option value=""></option>
                    <option value="1">01</option>
                    <option value="2">02</option>
                    <option value="3">03</option>
                    <option value="4">04</option>
                    <option value="5">05</option>
                    <option value="6">06</option>
                    <option value="7">07</option>
                    <option value="8">08</option>
                    <option value="9">09</option>
                    <option value="10">10</option>
                    <option value="11">11</option>
                    <option value="12">12</option>
                    <option value="13">13</option>
                    <option value="14">14</option>
                    <option value="15">15</option>
                    <option value="16">16</option>
                    <option value="17">17</option>
                    <option value="18">18</option>
                    <option value="19">19</option>
                    <option value="20">20</option>
                    <option value="21">21</option>
                    <option value="22">22</option>
                    <option value="23">22</option>
                    <option value="23">23</option>
                    <option value="24">24</option>
                    <option value="25">25</option>
                    <option value="26">26</option>
                    <option value="27">27</option>
                    <option value="28">28</option>
                    <option value="29">29</option>
                    <option value="30">30</option>
                    <option value="31">31</option>
                </select>
                <select name="year" value="${user.getYear()}" required>
                    <option value=""></option>
                    <option value="1980">1980</option>
                    <option value="1981">1981</option>
                    <option value="1982">1982</option>
                    <option value="1983">1983</option>
                    <option value="1984">1984</option>
                    <option value="1985">1985</option>
                    <option value="1986">1986</option>
                    <option value="1987">1987</option>
                    <option value="1988">1988</option>
                    <option value="1989">1989</option>
                    <option value="1990">1990</option>
                    <option value="1991">1991</option>
                    <option value="1992">1992</option>
                    <option value="1993">1993</option>
                    <option value="1994">1994</option>
                    <option value="1995">1995</option>
                    <option value="1996">1996</option>
                    <option value="1997">1997</option>
                    <option value="1998">1998</option>
                    <option value="1999">1999</option>
                    <option value="2000">2000</option>
                    <option value="2001">2001</option>
                    <option value="2002">2002</option>
                    <option value="2003">2003</option>
                    <option value="2004">2004</option>
                    <option value="2005">2005</option>
                    <option value="2006">2006</option>
                    <option value="2007">2007</option>
                    <option value="2008">2008</option>
                    <option value="2009">2009</option>
                    <option value="2010">2010</option>
                    <option value="2011">2011</option>
                    <option value="2012">2012</option>
                    <option value="2013">2013</option>
                    <option value="2014">2014</option>
                    <option value="2015">2015</option>
                    <option value="2016">2016</option>
                </select>
                <br>

            <input type="reset" value="Reset" class="margin_left">
            
            <input type="hidden" name="action" class="margin_left">
            <input type="submit" value="Update" class="margin_left">
            
            <input type="file" value="file" enctype="multipart/-form-date">
            <input type="submit" value="Upload Photo" class="margin_left">
            
        </form>
<!--        <form action="UploadServlet" method="post" enctype="multipart/form-data">
            Select file to upload: <input type="file" name="file" size="60">
            <input type="submit" value="Upload Photo" class="margin_left">
        </form>-->
        
    </body>
    <c:import url="/includes/footer.jsp" /> 
</html>
