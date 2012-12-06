<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="mail.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<% 
String user = (String)session.getAttribute("username");
if(user==null){
	System.out.println("user = null");
	out.println("<script type='text/javascript'>");
	out.println("window.location='announcements.jsp'");
	out.println("</script>");
}
%>
<%
Message msg = (Message) request.getAttribute("message");
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= msg.getSubject() %></title>
<%@include file="resources.jsp" %>
</head>
<body>
<%@include file="header.jsp" %>
<h1><%= msg.getSubject() %></h1>
<p>From: <%= msg.getFromID() %></p>
<p>Time: <%= msg.getTime() %></p>
<%if (msg.getType().equals("Message")) { %>
<p><%= msg.getMessage() %></p>
<%} %>

<!-- Friend Request -->
<%if (msg.getType().equals("Request")) { %>
<p><%= msg.getMessage() %></p>
<form action="AddFriendServlet" method="post">
<input type="hidden" name="victim" value=<%= msg.getFromID() %>>
<input type="submit" value="Approve Request">
</form>
<%} %>

<!-- Challenge -->
<%if (msg.getType().equals("Challenge")) { %>
<% ChallengeMessage chlg = (ChallengeMessage) request.getAttribute("message"); %>
<p><%= chlg.getMessage() %></p>
<form action="QuizServlet" method="post">
<input type="hidden" name="quizId" value="<%=chlg.getQuizID() %>"/>
<input type="submit" value="Take Quiz">
</form>
<%} %>

<!-- Reply -->
<form action="compose.jsp" method="post">
<input type="hidden" name="toID" value="<%= msg.getFromID() %>">
<input type="hidden" name="subject" value="RE: <%= msg.getSubject() %>">
<% 
String replyText = "-------------------------\n";
replyText += msg.getMessage();
%>
<input type="hidden" name="msgText" value="<%= replyText %>">
<input type="submit" value="Reply">
</form>
<a href="userHomePage.jsp#inboxTab"><button>Inbox</button></a>
</body>
</html>