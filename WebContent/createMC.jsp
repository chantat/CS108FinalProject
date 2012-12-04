<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*, question.*, answer.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Create Multiple-Choice Question</title>
</head>
<body>
<% 
	ArrayList<Question> pendingQuestions = (ArrayList<Question>)session.getAttribute("pendingQuestions");
	ArrayList<Answer> pendingAnswers = (ArrayList<Answer>)session.getAttribute("pendingAnswers");
	int questionIndex = (Integer)session.getAttribute("editPendingQuestionIndex");
	
	String oldQuestion = "";
	String oldAnswer = "";
	
	if (questionIndex != -1) {
		oldQuestion = pendingQuestions.get(questionIndex).getQText();
		oldAnswer = pendingAnswers.get(questionIndex).getAnswerList().get(0);
	}
	
%>

<h1>Question-Response Question</h1>
<form action="CreateQRServlet" method="post">
Enter your question: <input type="text" value="<% out.print(oldQuestion); %>" name="questionText"> <br>
Enter your answer: <input type="text" value="<% out.print(oldAnswer); %>"name="answer"> <br>
<input type="submit" value="Submit">
</form>

</body>
</html>