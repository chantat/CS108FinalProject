<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz Master | Logout</title>
<%@include file="resources.jsp" %>
</head>
<%
session.setAttribute("username", null);
session.setAttribute("mode", "guest");
%>
<script type="text/javascript">
	window.location="announcements.jsp";
</script>
<body>

<%@include file="header.jsp" %>
<h1>You have been logged out.</h1>

<A HREF="userLogin.jsp">Return to Login Page</A>
</body>
</html>