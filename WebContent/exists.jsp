<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Account Name Already Exists</title>
</head>
<body>

<h1>The Name <%= request.getParameter("user") %> is Already in Use. </h1>
Please try a different username.



<form action="CreateAccountServlet" method="post">
Username: <input type="text" name="user">  <br>
Password: <input type="password" name="pwd">
<input type="submit" value="Submit">
</form>

</body>
</html>