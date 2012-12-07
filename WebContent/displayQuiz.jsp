<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="quiz.*, question.*, java.util.*, answer.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
int quizID = Integer.parseInt((String)request.getAttribute("currentQuiz"));
int currQuest = (Integer) request.getAttribute("currentQuestion");
String practiceMode = (String) session.getAttribute("practiceMode");
QuizManager quizM = (QuizManager) application.getAttribute("quizManager");
QuestionManager questM = (QuestionManager) application.getAttribute("questionManager");
AnswerManager am = (AnswerManager) application.getAttribute("answerManager");
Quiz quiz = quizM.getQuiz(quizID);
String quizName = quiz.getName();
ArrayList<Integer> questIds;
ArrayList<Answer> answerChoices;
ArrayList<String> answers;
if (quiz.getIsRandomized()) {
	questIds = quiz.getRandomizedQuestionIds();
} else {
	questIds = quiz.getQuestionIds();
}
Question quest;
ArrayList<ArrayList<String>> questionResponses = (ArrayList<ArrayList<String>>) session.getAttribute("questionResponses");
double challengerScore = -1;
String challenger = "";
if (request.getParameterMap().containsKey("challenger")) {
	challenger = request.getParameter("challenger");
	challengerScore = Double.parseDouble(request.getParameter("challengerScore"));
}
//PRACTICE MODE
ArrayList<Integer> numTimesCorrect=null;
if(practiceMode.equals("true")){
	numTimesCorrect=(ArrayList<Integer>)session.getAttribute("practiceQuestionsCounter");
	System.out.println("currQuest: " + currQuest);
	if(numTimesCorrect.size()==0){ //first time around, set all to 0
		for(int j = 0; j < questIds.size(); j++){
			numTimesCorrect.add(0);
		}
		session.setAttribute("practiceQuestionsCounter", numTimesCorrect);
		session.setAttribute("practiceQuestionIds", questIds);
	}else{
		questIds=(ArrayList<Integer>)session.getAttribute("practiceQuestionIds");
		if(!quiz.getIsFlashcard() ||(quiz.getIsFlashcard() && currQuest == 0)){ //if greater or equal to 3, don't display (remove from array)
			for(int j = numTimesCorrect.size()-1; j >=0; j--){
				if(numTimesCorrect.get(j) >= 3){
					questIds.remove(j);
					numTimesCorrect.remove(j);
				}
			}
			if(numTimesCorrect.size()==0){
				session.setAttribute("practiceMode", "false");
				request.getRequestDispatcher("donePracticing.jsp").forward(request, response);
				return;
			}
		}
		session.setAttribute("practiceQuestionsCounter", numTimesCorrect);
		session.setAttribute("practiceQuestionIds", questIds);
	}
}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= quizName %></title>
<%@include file="resources.jsp" %>
<script type="text/javascript">
var sec = 0;
function pad ( val ) { return val > 9 ? val : "0" + val; }
setInterval( function(){
    $("#seconds").html(pad(++sec%60));
    $("#minutes").html(pad(parseInt(sec/60,10)));
}, 1000);
</script>
</head>
<body>
<%@include file="header.jsp" %>
<div id="allQuizQuestions">
<h1><%= quizName %></h1>
<span id="minutes"></span>:<span id="seconds"></span>
<%
	if (quiz.getIsFlashcard() && quiz.getImmediateFeedback() && currQuest > 0) {
		if (session.getAttribute("prevAnswer").equals("correct")) {
			out.println("<p>Correct! Next question:</p>");
		} else {
			out.println("<p>You didn't get that question right! Better luck on this one.</p>");
		}
	}
	if (quiz.getIsFlashcard() && currQuest > 0) {
		long currTimeTaken = (Long)request.getAttribute("currentTimeTaken");
		out.println("<p>Time taken for previous questions: " + (currTimeTaken/1000) + " seconds</p>");
	}
%>

<form action="ScoreServlet" method="post">
<%
for (int i = 0; i < questIds.size(); i++) {
	quest = questM.getQuestion(questIds.get(i));
	if (quiz.getIsFlashcard()) {
		quest = questM.getQuestion(questIds.get(currQuest));
	}
	switch(quest.getType()){
	case QuestionManager.QUESTION_RESPONSE:%>
		<p><%= quest.getQText() %></p>
		<input type="text" name="<%= quest.getID() %>answer0"/>
		<br>
		<%break;
	case QuestionManager.FILL_IN_THE_BLANK:%>
		<%
		String fibStr = quest.getQText();
		int index = fibStr.indexOf("###");
		String firstHalf = fibStr.substring(0, index);
		String secondHalf = fibStr.substring(index+3);
		%>
		<p><%= firstHalf %>
		<input type="text" name="<%= quest.getID() %>answer0"/>
		<%= secondHalf %></p>
		<br>
		<%break;
	case QuestionManager.MULTIPLE_CHOICE:%>
		<p><%= quest.getQText() %></p>
		<%
		answerChoices = am.getAnswers(quest.getID());
		answers = new ArrayList<String>();
		for(int j = 0; j < answerChoices.size(); j++){
			answers.add(answerChoices.get(j).getAnswerList().get(0));
		}
		for (int j = 0; j < answers.size(); j++) {
			String radioButton = "<input type=\"radio\" name=\"" + quest.getID() + "answer" + 0 + "\"";
			radioButton += "value=\"" + answers.get(j) + "\">" + answers.get(j);
			radioButton += "<br>";
			out.print(radioButton);
		}
		%>
		<br>
		<%break;
	case QuestionManager.PICTURE_RESPONSE:%>
		<p><%= quest.getQText() %></p>
		<img src="<%= ((PictureResponseQuestion)quest).getURL() %>" width="100"/> <br>
		<input type="text" name="<%= quest.getID() %>answer0"/>
		<br>
		<%break;
	case QuestionManager.MULTI_ANSWER:%>
		<p><%= quest.getQText() %></p>
		<%for (int j = 0; j < quest.getNumAnswers(); j++) { %>
			<input type="text" name="<%= quest.getID() %>answer<%= + j %>"/>
		<%} %>
		<br>
		<%break;
	case QuestionManager.MULTI_CHOICE_MULTI_ANSWER:%>
		<p><%= quest.getQText() %></p>
		<%
		answerChoices = am.getAnswers(quest.getID());
		answers=new ArrayList<String>();
		for(int j = 0; j < answerChoices.size(); j++){
			for(int k = 0; k < answerChoices.size(); k++){
				if(answerChoices.get(k).getAnswerOrder() == j+1){
					answers.add(answerChoices.get(k).getAnswerList().get(0));
				}
			}
		}
		for (int j = 0; j < answers.size(); j++) {
			String radioButton = "<input type=\"checkbox\" name=\"" + quest.getID() + "answer" + 0 + "\"";
			radioButton += "value=\"" + answers.get(j) + "\">" + answers.get(j);
			radioButton += "<br>";
			out.print(radioButton);
		}
		%>
		<br>
		<%break;
	case QuestionManager.MATCHING:%>
		<p><%= quest.getQText() %></p>
		<%
		answerChoices = am.getAnswers(quest.getID());
		
		ArrayList<String> leftChoices = new ArrayList<String>();
		ArrayList<String> rightChoices = new ArrayList<String>();
		for (int j = 0; j < answerChoices.size(); j++) {
			String answerText = answerChoices.get(j).getAnswerList().get(0);
			String[] tokens = answerText.split("#");
			leftChoices.add(tokens[0]);
			rightChoices.add(tokens[1]);
		}
		Collections.shuffle(rightChoices);
		
		for (int j = 0; j < leftChoices.size(); j++) {
			out.print(leftChoices.get(j));
			String dropDownString = "<select name=\"" + quest.getID() + "answer" + j + "\">";
			for (int k = 0; k < rightChoices.size(); k++) {
				dropDownString += "<option value=\"" + rightChoices.get(k) + "\">" + rightChoices.get(k) + "</option>";
			}
			dropDownString += "</select><br>";
			out.print(dropDownString);
		}%>
		<br>
		<%break;
	}
	if (quiz.getIsFlashcard()) break;
}
%>
<input type="hidden" name="currentQuiz" value="<% out.print(quizID); %>"/>
<input type="hidden" name="allowsPractice" value="<% out.print(practiceMode); %>"/>
<input type="hidden" name="challenger" value="<%= challenger %>"/>
<input type="hidden" name="challengerScore" value="<%= challengerScore %>"/>
<input type="hidden" name="currentQuestion" value="<% out.print(currQuest); %>"/>
<input type="submit" value="Submit"/>
</form>
</div>
</body>
</html>