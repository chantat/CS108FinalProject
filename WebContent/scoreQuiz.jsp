<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*, quiz.*, userPackage.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Quiz score</title>
</head>
<body>
<h1>Quiz score</h1>
<%
double score = (Double)request.getAttribute("currentScore");
double possibleScore = (Double)request.getAttribute("totalPossibleScore");
out.println("<p>You score a " + score + " out of " + possibleScore + " on the quiz</p>");
%>
</body>
</html>