<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz Master | Login</title>
<%@include file="resources.jsp" %>
</head>
<body>
<%@include file="header.jsp" %>

<center>
	<h2>Login</h2>
	<div>
		<form action="LoginServlet" method="post">
			Username: <input type="text" name="user">  <br>
			Password: <input type="password" name="pwd"> <br>
			<br>
			<input type="submit" value="Log in" class="ui-button ui-corner-all">
		</form>
	</div>
</center>

<!--  Or Log in as a Guest: 
<form action="GuestLoginServlet" method="post">
<input type="hidden" name="mode" value="guest"> 
<input type="submit" value="Log in as Guest">
</form>
Guests can see quizzes and users, but cannot take any quizzes.-->




</body>
</html>