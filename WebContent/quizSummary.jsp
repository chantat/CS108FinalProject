<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*, quiz.*, userPackage.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Quiz Summary</title>
<%@include file="resources.jsp" %>
</head>
<body>
<%@include file="header.jsp" %>
<%
QuizManager quizManager = (QuizManager)application.getAttribute("quizManager");
//int quizID = Integer.parseInt((String)request.getAttribute("currentQuiz"));
int quizID=26;
ReviewManager reviewManager = (ReviewManager)application.getAttribute("reviewManager");
RatingManager ratingManager = (RatingManager)application.getAttribute("ratingManager");
AttemptManager attemptManager = (AttemptManager)application.getAttribute("attemptManager");


Quiz quiz=quizManager.getQuiz(quizID);
String quizName = quiz.getName(); 
String authorId = quiz.getAuthorId();
String description = quiz.getDescription();
String category = quiz.getCategory();
double avgRating = ratingManager.getAverageRating(quizID);
ArrayList<String> tags = quiz.getTags();
String rating="";
if(avgRating == - 1){
	rating="No ratings";
}else{
	rating="" + avgRating;
}
%>
<center><h1><%out.print(quizName); %></h1></center>
<br></br>
<div id="quizSummaryContent">
<h2>Quiz Info</h2>
<div id="quizSummaryInfo">
Author: <a href="userHomePage.jsp"> <%
out.print(authorId + "</a>");
//TODO link this page correctly to user page
out.print("<br>");
out.print("Description: " + description);
out.print("<br>");
out.print("Category: " + category);
out.print("<br>");
out.print("Tags: <br>");
for(int i = 0; i < tags.size(); i++){
	out.print("#" + tags.get(i) + " ");
}
out.print("</div>");
out.print("<div id='quizSummaryLeaderboard'>");
out.print("<br> <h2>Leaderboard</h2>");
out.print("<h3>All-Time</h3>");
ArrayList<Attempt> topHighScorersOfAllTime=attemptManager.getTopHighScorersEver(quizID);


//table
out.print("<table id='allTimeLeaderboard'>");
out.print("<thead>");
out.print("<tr>");
out.print("<th>Username</th>");
out.print("<th>Score</th>");
out.print("<th>Time Spent</th>");
out.print("<th>Time Taken</th>");
out.print("</tr>");
out.print("</thead>");
out.print("<tbody>");

for(int i = 0; i < topHighScorersOfAllTime.size(); i++){
	out.print("<tr>");
	out.print("<td> " + topHighScorersOfAllTime.get(i).getUserId() + "</td>");
	out.print("<td> " + topHighScorersOfAllTime.get(i).getScore() + "</td>");
	out.print("<td> " + topHighScorersOfAllTime.get(i).getTimeSpent() + " sec</td>");
	out.print("<td> " + topHighScorersOfAllTime.get(i).getTimeTaken() + "</td>");
	out.print("</tr>");
}
out.print("</tbody>");
out.print("</table>");

out.print("<h3>Past Hour</h3>");
ArrayList<Attempt> recentTopScores = attemptManager.getTopPerformersByTime(quizID);


//table
out.print("<table id='recentLeaderboard'>");
out.print("<thead>");
out.print("<tr>");
out.print("<th>Username</th>");
out.print("<th>Score</th>");
out.print("<th>Time Spent</th>");
out.print("<th>Time Taken</th>");
out.print("</tr>");
out.print("</thead>");
out.print("<tbody>");

for(int i = 0; i < recentTopScores.size(); i++){
	out.print("<tr>");
	out.print("<td> " + recentTopScores.get(i).getUserId() + "</td>");
	out.print("<td> " + recentTopScores.get(i).getScore() + "</td>");
	out.print("<td> " + recentTopScores.get(i).getTimeSpent() + " sec</td>");
	out.print("<td> " + recentTopScores.get(i).getTimeTaken() + "</td>");
	out.print("</tr>");
}
out.print("</tbody>");
out.print("</table>");
%>
<div id="quizSummaryStats">
<h2>Statistics</h2>
<h3>Your Performance</h3>
<%
String user = (String)session.getAttribute("username");
ArrayList<Attempt> userScores = attemptManager.getUserPastPerformance(quizID, user, "score");

//table
out.print("<table id='userPerformance'>");
out.print("<thead>");
out.print("<tr>");
out.print("<th>Score</th>");
out.print("<th>Time Spent</th>");
out.print("<th>Time Taken</th>");
out.print("</tr>");
out.print("</thead>");
out.print("<tbody>");

for(int i = 0; i < userScores.size(); i++){
	out.print("<tr>");
	out.print("<td> " + userScores.get(i).getScore() + "</td>");
	out.print("<td> " + userScores.get(i).getTimeSpent() + " sec</td>");
	out.print("<td> " + userScores.get(i).getTimeTaken() + "</td>");
	out.print("</tr>");
}
out.print("</tbody>");
out.print("</table>");
%>

<h3>Recent Test Takers</h3>

<%
ArrayList<Attempt> recentScores = attemptManager.getMostRecentPerformers(quizID);

//table
out.print("<table id='recentPerformance'>");
out.print("<thead>");
out.print("<tr>");
out.print("<th>Username</th>");
out.print("<th>Score</th>");
out.print("<th>Time Spent</th>");
out.print("<th>Time Taken</th>");
out.print("</tr>");
out.print("</thead>");
out.print("<tbody>");

for(int i = 0; i < recentScores.size(); i++){
	out.print("<tr>");
	out.print("<td> " + recentScores.get(i).getUserId() + "</td>");
	out.print("<td> " + recentScores.get(i).getScore() + "</td>");
	out.print("<td> " + recentScores.get(i).getTimeSpent() + " sec</td>");
	out.print("<td> " + recentScores.get(i).getTimeTaken() + "</td>");
	out.print("</tr>");
}
out.print("</tbody>");
out.print("</table>");
%>

</div>

</div>
<br></br>
<div id="quizSummaryRating">
<h2>Reviews</h2>
<%

out.print("Average Rating: " + rating);
out.print("<br><br>");

ArrayList<Review> allQuizReviews= reviewManager.getMostRecentReviews(quizID);
int numReviews=allQuizReviews.size();
if(numReviews !=0){
	out.print("Displaying the " + numReviews + " most recent reviews<br><br>");
	for(int i = 0; i < allQuizReviews.size(); i++){
		String reviewText=allQuizReviews.get(i).getReview();
		out.print(reviewText + "<br>");
		out.print("--Reviewed by " + allQuizReviews.get(i).getUserID() + "<br><br>");
	}
}

%>
</div>

<br></br>
<%

%>
</div>
</body>
</html>