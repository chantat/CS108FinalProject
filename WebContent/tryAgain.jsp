<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Try Again</title>
<%@include file="resources.jsp" %>
</head>
<body>
<%@include file="header.jsp" %>
<center>
<h1>Oops!</h1>
Either your username or password is incorrect. Please try again.
<br><br>
<form action="LoginServlet" method="post">
Username: <input type="text" name="user"> <br>
Password: <input type="password" name="pwd"><br><br>
<input type="submit" value="Log in">
</form>
</center>

</body>
</html>