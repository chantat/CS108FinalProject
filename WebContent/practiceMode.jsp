<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="quiz.*, question.*, java.util.*, answer.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Practice mode</title>
<%@include file="resources.jsp" %>
</head>
<body>
<%@include file="header.jsp" %>
<center>
<h1>Would you like to play in practice mode?</h1>
<form action="QuizServlet" method="post">
<%
int quizID = Integer.parseInt((String)request.getAttribute("currentQuiz"));
int currQuest = (Integer) request.getAttribute("currentQuestion");
request.removeAttribute("practiceMode");
session.setAttribute("practiceQuestionsCounter", null);
	String yesButton = "<input type=\"radio\" name=\"practiceMode\"";
	yesButton += "value=\"" + "true" + "\">" + "Yes";
	yesButton += "<input type=\"hidden\" name=\"quizId\" value=\"" + quizID + "\"/>";
	yesButton += "<br>";
	out.print(yesButton);
	
	String noButton = "<input type=\"radio\" name=\"practiceMode\"";
	noButton += "value=\"" + "false" + "\">" + "No";
	noButton += "<input type=\"hidden\" name=\"quizId\" value=\"" + quizID + "\"/>";
	noButton += "<br>";
	out.print(noButton);
	out.println("<br><input type=\"submit\" value=\"Submit\"><br>");
%>
</form>
</center>
</body>
</html>