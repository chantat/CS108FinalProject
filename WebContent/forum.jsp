<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*, quiz.*, forum.*, java.sql.Timestamp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Quiz Thread</title>
<%@include file="resources.jsp" %>
</head>
<body>
<%@include file="header.jsp" %>
<%
ForumManager fm = (ForumManager)application.getAttribute("forumManager");
int quizID = (Integer)request.getAttribute("quizId");
QuizManager quizManager = (QuizManager)application.getAttribute("quizManager");
Quiz quiz=quizManager.getQuiz(quizID);
String quizName = quiz.getName();
%>
<center><h1><% out.print(quizName); %></h1></center><br><br>
<div id="quizThreadContent">
<%

ArrayList<ForumPost> allPosts= fm.getForumPosts(quizID);
int numPosts=allPosts.size();
if(numPosts == 0) out.print("There are no forum posts about this quiz.<br><br>");
else{
	out.print("Discuss!<br><br>");
	for(int i = 0; i < allPosts.size(); i++){
		String postText=allPosts.get(i).getPostText();
		String userID=allPosts.get(i).getUserID();
		Timestamp timePosted=allPosts.get(i).getTimePosted();
		out.println(postText + "<br>");
		out.println("--" + userID + " on " + timePosted + "<br><br>");
	}
}
out.print("<form action=\"ForumServlet\" method=\"post\">");
String hiddenInput = "<input type=\"hidden\" name=\"quizId\" value=\"" + quizID + "\">";
out.print(hiddenInput);
String postBox = "<br><textarea cols=\"30\" rows=\"5\" name=\"postText\"";
postBox += "value=\"" + "\">";
postBox += "</textarea>";
out.println(postBox);
if(numPosts==0){
	out.println("<br><input type=\"submit\" value=\"Create first post\"><br>");
}else{
	out.println("<br><input type=\"submit\" value=\"Post to thread\"><br>");
}
out.print("</form>");
%>
</div>
</body>
</html>