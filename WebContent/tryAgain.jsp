<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Try Again</title>
</head>
<body>
<h1>Please Try Again</h1>
Either your username or password is incorrect. Please try again. 



<form action="LoginServlet" method="post">
Username: <input type="text" name="user"> <br>
Password: <input type="password" name="pwd">
<input type="submit" value="Submit">
</form>
<A HREF="http://localhost:8080/Welcome/create.html">Create a new account</A>

</body>
</html>