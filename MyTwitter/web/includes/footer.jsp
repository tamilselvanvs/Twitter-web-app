<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.GregorianCalendar, java.util.Calendar" %>

<%
    GregorianCalendar currentDate = new GregorianCalendar();
    int currentYear = currentDate.get(Calendar.YEAR);
    int currentMonth = currentDate.get(Calendar.MONTH);
    int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);
    String todaysDate = (currentMonth + "/" + currentDay + "/" + currentYear);
%>    
<br>
<p>Today's Date: <%= todaysDate %> </p>
<p>&copy; Copyright <%= currentYear %> </p>
    
</body>
</html>
