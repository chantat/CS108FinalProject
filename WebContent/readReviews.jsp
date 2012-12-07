<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*, quiz.*, userPackage.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Quiz Reviews</title>
</head>
<body>
<%
ReviewManager rm = (ReviewManager)application.getAttribute("reviewManager");

int quizId = (Integer)request.getAttribute("quizId");
ArrayList<Review> allQuizReviews= rm.getAllReviews(quizId);
int numReviews=allQuizReviews.size();
if(numReviews == 0) out.println("There are no reviews to display");
else{
	out.println("Displaying all reviews<br><br>");
	for(int i = 0; i < allQuizReviews.size(); i++){
		String reviewText=allQuizReviews.get(i).getReview();
		out.println(reviewText + "<br>");
		out.println("--Reviewed by " + allQuizReviews.get(i).getUserID() + "<br><br>");
	}
}
%>
</body>
</html>