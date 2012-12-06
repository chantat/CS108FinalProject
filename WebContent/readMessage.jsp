<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="mail.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<% 
String quote = "\"";
String redirect = "<meta http-equiv=" +quote+ "refresh"+quote+" content="+quote+"1;url=userLogin.jsp"+quote+">";
String user = (String)session.getAttribute("username");
if(user==null){
	System.out.println("user = null");
	out.print(redirect);
}
%>
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
<p><%= msg.getMessage() %></p>
<form action="QuizServlet" method="post">
<input type="hidden" name="quizId" value="<%=chlg.getQuizID() %>"/>
<input type="submit" value="Take Quiz">
</form>
<%} %>
<a href="inbox.jsp"><button>Inbox</button></a>
</body>
</html>