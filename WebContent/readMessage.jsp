<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
mail.Message msg = (mail.Message) request.getAttribute("message");
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= msg.getSubject() %></title>
</head>
<body>
<h1><%= msg.getSubject() %></h1>
<p>From: <%= msg.getFromID() %></p>
<p>Time: <%= msg.getTime() %></p>
<p><%= msg.getMessage() %></p>
</body>
</html>