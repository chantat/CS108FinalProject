<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="quiz.*, question.*, java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
int quizID = Integer.parseInt((String)request.getAttribute("currentQuiz"));
int currQuest = (Integer) request.getAttribute("currentQuestion");
QuizManager quizM = (QuizManager) application.getAttribute("quizManager");
QuestionManager questM = (QuestionManager) application.getAttribute("questionManager");
Quiz quiz = quizM.getQuiz(quizID);
String quizName = quiz.getName();
ArrayList<Integer> questIds;
if (quiz.getIsRandomized()) {
	questIds = quiz.getRandomizedQuestionIds();
} else {
	questIds = quiz.getQuestionIds();
}
Question quest;
ArrayList<ArrayList<String>> questionResponses = (ArrayList<ArrayList<String>>) session.getAttribute("questionResponses");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= quizName %></title>
</head>
<body>
<h1><%= quizName %></h1>

<form action="ScoreServlet" method="post">
<%
for (int i = 0; i < questIds.size(); i++) {
	quest = questM.getQuestion(questIds.get(i));
	switch(quest.getType()){
	case QuestionManager.QUESTION_RESPONSE:%>
		<p><%= quest.getQText() %></p>
		<input type="text" name="<%= quest.getID() %>answer0"/>
		<%break;
	case QuestionManager.FILL_IN_THE_BLANK:%>
		<%
		String fibStr = quest.getQText();
		int index = fibStr.indexOf("###");
		String firstHalf = fibStr.substring(0, index);
		String secondHalf = fibStr.substring(index);
		%>
		<p><%= firstHalf %>
		<input type="text" name="<%= quest.getID() %>answer0"/>
		<%= secondHalf %></p>
		
		<%break;
	case QuestionManager.MULTIPLE_CHOICE:%>
		<p><%= quest.getQText() %></p>
		<%
		for (int j = 0; j < quest.getNumAnswers(); j++) {%>
			<input type="radio" name="<%= quest.getID() %>answer0" value=""/> <!-- TODO: get possibilities -->
		<%}%>
		<%break;
	case QuestionManager.PICTURE_RESPONSE:%>
		<img src="<%= quest.getQText() %>"/>
		<input type="text" name="<%= quest.getID() %>answer0"/>
		<%break;
	case QuestionManager.MULTI_ANSWER:%>
		<p><%= quest.getQText() %></p>
		<%for (int j = 0; j < quest.getNumAnswers(); j++) { %>
			<input type="text" name="<%= quest.getID() %>answer<%= + j %>"/>
		<%} %>
		<%break;
	case QuestionManager.MULTI_CHOICE_MULTI_ANSWER:%>
		<p><%= quest.getQText() %></p>
		<%for (int j= 0; j < quest.getNumAnswers(); j++) { %>
			<input type="checkbox"/>
		<%} %>
		<%break;
	case QuestionManager.MATCHING:%>
		
	<%}
}
%>
<input type="hidden" name="currentQuiz" value="<% out.print(quizID); %>"/>
<input type="submit" value="Submit"/>
</form>
</body>
</html>