<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*, quiz.*, userPackage.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Quiz Summary</title>
</head>
<body>
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
</body>
<h1>Quiz Summary</h1>
<br></br>
<h2> <%out.print(quizName); %> </h2>
<br></br>
Quiz Author: <a href="userHomePage.jsp"> <%
out.print(authorId + "</a>");
//TODO link this page correctly to user page
out.print("<br>");
out.print("Quiz Description: " + description);
out.print("<br>");
out.print("Category: " + category);
out.print("<br>");
out.print("Tags: <br>");
for(int i = 0; i < tags.size(); i++){
	out.print("#" + tags.get(i) + " ");
}
out.print("<br> Leaderboard: <br>");
ArrayList<Attempt> topHighScorersOfAllTime=attemptManager.getTopHighScorersEver(quizID);


//table
out.print("<table id='leaderboard'>");
out.print("<thead>");
out.print("<tr>");
out.print("<th>Username</th>");
out.print("<th>Score</th>");
out.print("<th>Time spent</th>");
out.print("<th>Time taken</th>");
out.print("</tr>");
out.print("</thead>");
out.print("<tbody>");

for(int i = 0; i < topHighScorersOfAllTime.size(); i++){
	out.print("<tr>");
	out.print("<td> " + topHighScorersOfAllTime.get(i).getUserId() + "</td>");
	out.print("<td> " + topHighScorersOfAllTime.get(i).getScore() + "</td>");
	out.print("<td> " + topHighScorersOfAllTime.get(i).getTimeSpent() + "</td>");
	out.print("<td> " + topHighScorersOfAllTime.get(i).getTimeTaken() + "</td>");
	out.print("</tr>");
}
out.print("</tbody>");
out.print("</table>");
%>
<br></br>
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


<br></br>
<%

%>

</html>