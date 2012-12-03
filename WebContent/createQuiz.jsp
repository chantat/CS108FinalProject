<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, quiz.*, question.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Quiz</title>
</head>
<body>

<form action="CreateQuizServlet" method="post">
Quiz Name: <input type="text" name="quizName"> <br>
Description: <input type="text" name="description">  <br>
Category: <input type="text" name="category"> <br>
<input type="submit" value="Create Quiz">
</form>
<br><br>

<table border="1">
<% 
ArrayList<Question> pendingQuestions = (ArrayList<Question>)session.getAttribute("pendingQuestions");
for (int i = 0; i < pendingQuestions.size(); i++) {
	Question question = pendingQuestions.get(i);
	String editButtonString = "<form action=\"EditQuestionServlet\" method=\"post\">";
	editButtonString += "<input type=\"hidden\" name = \"questionIndex\" value=\""+ i + "\">";
	editButtonString += "<input type=\"submit\" value=\"Edit\">";
	editButtonString += "</form>";
	out.println("<tr>");
	out.println("<td> " + question.getQText() + "</td>");
	out.println("<td> " + QuestionManager.getTypeName(question.getType()) + "</td>");
	out.println("<td> " + editButtonString + "</td>");
	out.println("</tr>");
}


String dropDownString = "<select name=\"questionType\">";
for (int i = 0; i < QuestionManager.getNumType(); i++) {
	int type = i + 1;
	dropDownString += "<option value=\"" + type + "\">" + QuestionManager.getTypeName(type) + "</option>";
}
dropDownString += "</select>";

String addButtonString = "<input type=\"hidden\" name = \"questionIndex\" value=\"-1\">";
addButtonString += "<input type=\"submit\" value=\"Add Question\">";

out.println("<form action=\"EditQuestionServlet\" method=\"post\">");
out.println("<tr>");
out.println("<td> " + " " + "</td>");
out.println("<td> " + dropDownString + "</td>");
out.println("<td> " + addButtonString + "</td>");
out.println("</tr>");
out.println("</form>");
%>	
</table>
</body>
</html>