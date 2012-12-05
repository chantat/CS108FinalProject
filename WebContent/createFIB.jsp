<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, answer.*, question.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Fill in the Blank Question</title>
</head>
<body>
<% 
	ArrayList<Question> pendingQuestions = (ArrayList<Question>)session.getAttribute("pendingQuestions");
	ArrayList<ArrayList<Answer>> pendingAnswers = (ArrayList<ArrayList<Answer>>)session.getAttribute("pendingAnswers");
	int questionIndex = (Integer)session.getAttribute("editPendingQuestionIndex");
	
	String oldQuestion = "";
	String oldAnswer="";
	
	if (questionIndex != -1) {
		oldQuestion = pendingQuestions.get(questionIndex).getQText();
		oldAnswer = pendingAnswers.get(questionIndex).get(0).getAnswerList().get(0);
	}
	
%>

<h1>Fill in the Blank Question</h1>
<form action="CreateFIBServlet" method="post">
Enter your statement, with ### where the blank goes: <input type="text" value="<% out.print(oldQuestion); %>" name="questionText"> <br>
Enter your answer: <input type="text" value="<% out.print(oldAnswer); %>"name="answer"> <br>
<input type="submit" value="Submit">
</form>
</body>
</html>