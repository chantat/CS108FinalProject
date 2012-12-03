<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="mail.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
Message msg = (Message) request.getAttribute("message");
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= msg.getSubject() %></title>
</head>
<body>
<h1><%= msg.getSubject() %></h1>
<p>From: <%= msg.getFromID() %></p>
<p>Time: <%= msg.getTime() %></p>
<%if (msg.getType().equals("Message")) { %>
<p><%= msg.getMessage() %></p>
<%} %>
<%if (msg.getType().equals("Request")) { %>
<p><%= msg.getMessage() %></p>
<form action="AddFriendServlet" method="post">
<input type="hidden" name="victim" value=<%= msg.getFromID() %>>
<input type="submit" value="Approve Request">
</form>
<%} %>
<%if (msg.getType().equals("Challenge")) { %>
<p><%= msg.getFromID() %> has challenged you to take a quiz! 
Follow the link below to try to beat <%= msg.getFromID() %>'s score!</p>
<form action="FILL_IN_THIS_SERVLET" method="post">
<input type="hidden" name="quizID" value=<%= msg.getMessage() %>>
<input type="submit" value="Take Quiz">
</form>
<%} %>
<a href="inbox.jsp">Inbox</a>
</body>
</html>