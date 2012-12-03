<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="quiz.*, java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
int quizID = (Integer) request.getAttribute("currentQuiz");
QuizManager quizM = (QuizManager) application.getAttribute("quizManager");
QuestionManager questM = (QuestionManager) application.getAttribute("questionManager");
Quiz quiz = quizM.getQuiz(quizID);
String quizName = quiz.getName();
ArrayList<Integer> questIds = quiz.getQuestionIds();
Question quest;
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= quizName %></title>
</head>
<body>
<h1><%= quizName %></h1>

<%
for (int i = 0; i < questIds.size(); i++) {
	quest = questM.getQuestion(questIds.get(i));
	if (quest.getType() == questM.QUESTION_RESPONSE) {
%>
<form action="ScoringServlet" method="post">
<p><%= quest.getQText() %></p>
<input type="text" name="entry"/>
<input type="hidden" name="qID" value="<%= quest.getID() %>">
<input type="submit" value="Submit"/>
</form>
<%
	}
}
%>
</body>
</html>