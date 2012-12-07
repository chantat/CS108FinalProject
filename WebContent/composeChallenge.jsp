<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="quiz.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Challenge!</title>
<%@include file="resources.jsp" %>
<% 
String user = (String)session.getAttribute("username");
if(user==null){
	System.out.println("user = null");
	out.println("<script type='text/javascript'>");
	out.println("window.location='announcements.jsp'");
	out.println("</script>");
}
%>
</head>
<body>
<%@include file="header.jsp" %>
<%
double score = Double.parseDouble(request.getParameter("score"));
double possibleScore = Double.parseDouble(request.getParameter("possibleScore"));
int quizID = Integer.parseInt(request.getParameter("quizId"));
QuizManager qm = (QuizManager) application.getAttribute("quizManager");
String quizStr = qm.getQuizName(quizID);
String subject = "Challenge!";
String message = user + " has challenged you to a the quiz ";
message += quizStr;
message += "! Can you beat a score of " + score + "/" + possibleScore +"?";
%>
<center>
<h1>New Challenge</h1>
<form action="ChallengeServlet" method="post">
<input type="hidden" name="quizId" value="<%= quizID %>"/>
<input type="hidden" name="score" value="<%= score %>"/>
<input type="hidden" name="possibleScore" value="<%= possibleScore %>"/>
<p>To: <input type="text" name="toID"/>
(Separate names with commas)</p>
<p>Subject: <input type="text" name="subject" value="<%= subject %>"/></p>
<p><textarea name="message" cols="50" rows="5"><%= message %></textarea></p>
<input type="submit" value="Challenge!"/>
</form>
<!--
<form action="MailServlet" method="post">
<p>To: <input type="text" name="toID"/>
(Separate names with commas)</p>
<p>Subject: <input type="text" name="subject" value="<%= subject %>"/></p>
<p><textarea name="message" cols="50" rows="5"><%= message %></textarea></p>
<input type="submit" name="Send" value="Send"/>
<a href="userHomePage.jsp#inboxTab">
<button>Discard</button>
</a>
</form>
-->
</center>
</body>
</html>