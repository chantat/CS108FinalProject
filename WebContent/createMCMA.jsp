<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ page import="java.util.*, question.*, answer.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Create Multi-Choice Multi-Answer Question</title>
</head>
<body>
<% 
	ArrayList<Question> pendingQuestions = (ArrayList<Question>)session.getAttribute("pendingQuestions");
	ArrayList<ArrayList<Answer>> pendingAnswers = (ArrayList<ArrayList<Answer>>)session.getAttribute("pendingAnswers");
	int questionIndex = (Integer)session.getAttribute("editPendingQuestionIndex");
	
	String oldQuestion = "";
	int oldNumAnswers=0;
	
	if (questionIndex != -1){
		oldQuestion = pendingQuestions.get(questionIndex).getQText();
		oldNumAnswers=pendingQuestions.get(questionIndex).getNumAnswers();
	}
	
%>

<h1>Multi-Choice Multi-Answer Question</h1>
<form action="CreateMCMAServlet" method="post">
Enter your question: <input type="text" value="<% out.print(oldQuestion); %>" name="questionText">
Enter number of correct answers: <input type="number" value="<% out.print(oldNumAnswers); %>" name="numCorrectAnswers">
<input type="submit" value="Submit">
</form>
</body>
</html>