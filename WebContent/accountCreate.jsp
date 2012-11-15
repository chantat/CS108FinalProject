<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create New Account</title>
</head>
<body>
<h1>Create New Account</h1>
Please enter proposed username and password. 

<form action="CreateAccountServlet" method="post">
Username: <input type="text" name="user">  <br>
Password: <input type="password" name="pwd">
<input type="submit" value="Submit">
</form>