<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*, quiz.*, userPackage.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<% 
String quote = "\"";
String redirect = "<meta http-equiv=" +quote+ "refresh"+quote+" content="+quote+"1;url=userLogin.jsp"+quote+">";
String user = (String)session.getAttribute("username");
if(user==null){
	System.out.println("user = null");
	out.print(redirect);
}
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Quiz Results</title>
<%@include file="resources.jsp" %>
</head>
<body>
<%@include file="header.jsp" %>
<center><h1>Quiz Results</h1></center>
<div id="quizResults">
<%
double score = (Double)request.getAttribute("totalScore");
double possibleScore = (Double)request.getAttribute("totalPossibleScore");
int percentScore = (int) (score/possibleScore * 100.0);
String practiceMode = (String)request.getAttribute("practiceMode");
boolean practice=false;
if(practiceMode.equals("true")) practice=true;
int timeTaken = 0;
if (!practice) timeTaken = (Integer)request.getAttribute("timeTaken");

/* If user was challenged, tell them whether or not they won. */
if (request.getParameterMap().containsKey("challenger")) {
	String challenger = request.getParameter("challenger");
	Double challengerScore = Double.parseDouble(request.getParameter("challengerScore"));
	String challengeResponseMessage = "";
	if (!challenger.equals("") && challengerScore != -1) {
		if (score > challengerScore) {
			challengeResponseMessage = "Congratulations! You won ";
			challengeResponseMessage += challenger + "'s challenge! ";
			challengeResponseMessage += challenger + " only scored ";
			
		}
		else if (score < challengerScore) {
			challengeResponseMessage = "Too bad! You lost ";
			challengeResponseMessage += challenger + "'s challenge! ";
			challengeResponseMessage += challenger + " scored ";
		}
		else {
			challengeResponseMessage = "Wow! You tied ";
			challengeResponseMessage += challenger + "'s challenge! ";
			challengeResponseMessage += challenger + " also scored ";
		}
		challengeResponseMessage += challengerScore;
		challengeResponseMessage += "/" + possibleScore + " (";
		challengeResponseMessage += percentScore + "%).";
		out.println("<p>" + challengeResponseMessage + "</p>");
	}
}

out.println("<p>You scored a " + score + " out of " + possibleScore + " on the quiz.</p>");
if (!practice) out.println("<p>Time taken: " + timeTaken + " seconds.<p>");
application.setAttribute("currentQuiz", Integer.parseInt(request.getParameter("currentQuiz")));
if(!practice){
	out.println("<p>Challenge a friend to beat your score!</p>");
	System.out.println("QuizID of Challenge: " + request.getParameter("currentQuiz"));
%>

<form action="composeChallenge.jsp" method="post">
<input type="hidden" name="quizId" value="<%= request.getParameter("currentQuiz") %>"/>
<input type="hidden" name="score" value="<%= score %>"/>
<input type="hidden" name="possibleScore" value="<%= possibleScore %>"/>
<input type="submit" value="Challenge!"/>
</form>


<% 
}else{
	%>
	<form action="QuizServlet" method="post">
	<input type="hidden" name="quizId" value="<%= request.getParameter("currentQuiz") %>"/>
	<input type="hidden" name="practiceMode" value="ongoing"/>
	<input type="submit" value="Practice this quiz again!"/>
	</form>
	<%
}
%>
<form action="RatingServlet" method="post">
<%
RatingManager rm = (RatingManager)application.getAttribute("ratingManager");
out.println("Submit a rating and review below!<br><br>");
for (int i = 0; i < rm.RATING_MAXIMUM; i++) {
	int grade = i+1;	
	String radioButton = "<input type=\"radio\" name=\"grade\"";
	radioButton += "value=\"" + grade + "\">" + grade;
	radioButton += "<input type=\"hidden\" name=\"quizId\" value=" + request.getParameter("currentQuiz") + ">";
	out.print(radioButton);
}
out.println("<br><br>");
out.println("Write your review below:");
String hiddenInput = "<input type=\"hidden\" name=\"quizId\" value=" + request.getParameter("currentQuiz") + ">";
out.println(hiddenInput);
String reviewBox = "<br><textarea cols=\"30\" rows=\"5\" name=\"reviewText\"";
reviewBox += "value=\"" + "\">";
reviewBox += "</textarea>";
out.println(reviewBox);
out.println("<br><input type=\"checkbox\" name=\"reportFlag\" value=\"Flag\">Flag this quiz as inappropriate<br>");
out.println("<br><input type=\"submit\" value=\"Submit rating and review!\"><br>");
%>
</form>
</div>

</body>
</html>