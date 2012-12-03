<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, quiz.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Fill in the Blank Question</title>
</head>
<body>
<% 
	ArrayList<Question> pendingQuestions = (ArrayList<Question>)session.getAttribute("pendingQuestions");
	ArrayList<String> pendingAnswers = (ArrayList<String>)session.getAttribute("pendingAnswers");
	int questionIndex = (Integer)session.getAttribute("editPendingQuestionIndex");
	
	String oldQuestion = "";
	String oldAnswer = "";
	
	if (questionIndex != -1) {
		oldQuestion = pendingQuestions.get(questionIndex).getQText();
		oldAnswer = pendingAnswers.get(questionIndex);
	}
	
%>

<h1>Fill in the Blank Question</h1>
<form action="CreateQRServlet" method="post">
Enter your question: <input type="text" value="<% out.print(oldQuestion); %>" name="questionText"> <br>
Enter your answer: <input type="text" value="<% out.print(oldAnswer); %>"name="answer"> <br>
<input type="submit" value="Submit">
</form>
</body>
</html>