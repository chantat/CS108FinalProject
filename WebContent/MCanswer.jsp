<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ page import="java.util.*, question.*, answer.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Multiple choice answer</title>
</head>
<body>
<% 
	ArrayList<Question> pendingQuestions = (ArrayList<Question>)session.getAttribute("pendingQuestions");
	ArrayList<ArrayList<Answer>> pendingAnswers = (ArrayList<ArrayList<Answer>>)session.getAttribute("pendingAnswers");
	int questionIndex = (Integer)session.getAttribute("editPendingQuestionIndex");
	
	String oldQuestion = "";
	String oldAnswer = "";
	double oldScore=0.0;
	
	if (questionIndex != -1) {
		oldAnswer = pendingAnswers.get(questionIndex).get(0).getAnswerList().get(0);
		oldScore=pendingAnswers.get(questionIndex).get(0).getScore();
	}
	session.setAttribute("numAnswers", (Integer)session.getAttribute("numAnswers")+1);
	System.out.println("NUMANS:" + session.getAttribute("numAnswers"));
	
%>

<h1>Enter your multiple-choice question answer choices (in order)</h1>
<form action="AddMCAnswersServlet" method="post">
Enter an answer: <input type="text" value="<% out.print(oldAnswer); %>" name="answer"><br>
<input type="checkbox" name="isCorrectAnswer">Check if correct answer (can only check this for one answer choice)<br>
Enter score (if correct answer): <input type="number" value="<% if(questionIndex != -1)out.print(oldScore); %>" name="score"><br>
<input type="checkbox" name="isFinished">Check if last answer choice<br>
<input type="submit" value="Submit">
</form>
</body>
</html>