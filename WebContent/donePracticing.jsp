<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Done Practicing</title>
</head>
<body>
<h2>You've practiced to perfection!</h2>
<br>
<p>No more questions left for you to practice</p>
<% // Link to quiz Homepage 
String quizHomepageLink = "<A HREF=\"quizHomepage.jsp\">Quiz Homepage</A>";
out.println(quizHomepageLink);
%>

</body>
</html>