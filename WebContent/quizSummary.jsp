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
AccountManager acctManager = (AccountManager)application.getAttribute("manager");
//int quizID = Integer.parseInt((String)request.getAttribute("currentQuiz"));
int quizID= Integer.parseInt(request.getParameter("qID"));
ReviewManager reviewManager = (ReviewManager)application.getAttribute("reviewManager");
RatingManager ratingManager = (RatingManager)application.getAttribute("ratingManager");
AttemptManager attemptManager = (AttemptManager)application.getAttribute("attemptManager");

String user = (String)session.getAttribute("username");
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
String authorButton = "<form action=\"UserSearchServlet\" method=\"post\"><input type=\"hidden\" name = \"victim\" value=\"" +authorId +"\"><input type=\"submit\" value=\""+authorId+"\"></form>";
%>
<center><h1><%out.print(quizName); %></h1></center>
<br></br>
<div id="quizSummaryContent">
<h2>Quiz Info</h2>
<div id="quizSummaryInfo">
Author: <%
out.print(authorButton);
//TODO link this page correctly to user page
out.print("<br>");
out.print("Description: " + description);
out.print("<br>");
out.print("Category: " + category);
out.print("<br>");
out.print("Tags: ");
for(int i = 0; i < tags.size(); i++){
	out.print("#" + tags.get(i) + " ");
}
out.print("<br>");
String quizButton = "<form action=\"QuizServlet\" method=\"post\">";
quizButton += "<input type=\"hidden\" name = \"quizId\" value=\""+ quizID + "\">";
quizButton += "<input type=\"submit\" value=\"" + "Start Quiz" + "\">";
quizButton += "</form>";

String editButton = "<form action=\"EditQuizServlet\" method=\"post\">";
editButton += "<input type=\"hidden\" name = \"quizId\" value=\""+ quizID + "\">";
editButton += "<input type=\"submit\" value=\"Edit Quiz\">";
editButton += "</form>";

out.print("<br>"+quizButton);
if (authorId.equals(user)) out.print("<br>"+editButton+"<br>");

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
	String curUserID = topHighScorersOfAllTime.get(i).getUserId();
	String linkButton = "<form action=\"UserSearchServlet\" method=\"post\"><input type=\"hidden\" name = \"victim\" value=\"" +topHighScorersOfAllTime.get(i).getUserId() +"\"><input type=\"submit\" value=\""+topHighScorersOfAllTime.get(i).getUserId()+"\"></form>";
	if (acctManager.isPerfPublic(curUserID)) out.print("<td> " + linkButton + "</td>");
	else out.print("<td>Anonymous</td>");
	String percentString = String.format("%.2f", topHighScorersOfAllTime.get(i).getScore());
	out.print("<td> " + percentString + "%</td>");
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
	String curUserID = recentTopScores.get(i).getUserId();
	String linkButton = "<form action=\"UserSearchServlet\" method=\"post\"><input type=\"hidden\" name = \"victim\" value=\"" +recentTopScores.get(i).getUserId() +"\"><input type=\"submit\" value=\""+recentTopScores.get(i).getUserId()+"\"></form>";
	if (acctManager.isPerfPublic(curUserID)) out.print("<td> " + linkButton + "</td>");
	else out.print("<td>Anonymous</td>");
	String percentString = String.format("%.2f", recentTopScores.get(i).getScore());
	out.print("<td> " + percentString + "%</td>");
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
//String user = (String)session.getAttribute("username");
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
	String percentString = String.format("%.2f", userScores.get(i).getScore());
	out.print("<td> " + percentString + "%</td>");
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
	String curUserID = recentScores.get(i).getUserId();
	String linkButton = "<form action=\"UserSearchServlet\" method=\"post\"><input type=\"hidden\" name = \"victim\" value=\"" +recentScores.get(i).getUserId() +"\"><input type=\"submit\" value=\""+recentScores.get(i).getUserId()+"\"></form>";
	if (acctManager.isPerfPublic(curUserID)) out.print("<td> " + linkButton + "</td>");
	else out.print("<td>Anonymous</td>");
	String percentString = String.format("%.2f", recentScores.get(i).getScore());
	out.print("<td> " + percentString + "%</td>");
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
		String linkButton = "<form action=\"UserSearchServlet\" method=\"post\"><input type=\"hidden\" name = \"victim\" value=\"" +allQuizReviews.get(i).getUserID() +"\"><input type=\"submit\" value=\""+allQuizReviews.get(i).getUserID()+"\"></form>";
		out.print("--Reviewed by " + linkButton + "<br><br>");
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