<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, userPackage.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create New Account</title>
<%@include file="resources.jsp" %>
</head>
<body>
<%@include file="header.jsp" %> 

<center>
<h2>Register</h2>
<%
	if (request.getAttribute("err") != null) {
		out.println("<div class='ui-widget' style='width:350px'>");
		out.println("<div class='ui-state-error ui-corner-all' style='padding: 0 .7em;'>");
		out.println("<p><span class='ui-icon ui-icon-alert' style='float: left; margin-right: .3em;'></span>");
		
		if (request.getAttribute("err").equals("alreadyExists")) {
			out.println("<strong>Oops!</strong> A user with that name already exists.</p>");
		} else if (request.getAttribute("err").equals("emptyPass")) {
			out.println("<strong>Oops!</strong> Your password cannot be blank.</p>");
		} else if (request.getAttribute("err").equals("emptyName")) {
			out.println("<strong>Oops!</strong> Your username cannot be blank.</p>");
		}
		
		out.println("</div>");
		out.println("</div>");
		out.println("<br>");
	}
%>

<form action="CreateAccountServlet" method="post">
Username: <input type="text" name="user">  <br>
Password: <input type="password" name="pwd"> <br>
<input type="checkbox" name="privacy1" value="Public">I want my quiz scores public<br>
<input type="checkbox" name="privacy2" value="Public">I want my user page to be visible to all<br>
<br>
<input type="submit" value="Register">
</form>

</center>