<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Quiz</title>
</head>
<body>

<%String username = (String)session.getAttribute("username");%>

<form action="CreateQuizServlet" method="post">
Quiz Name: <input type="text" name="quizName"> <br>
Description: <input type="text" name="description">  <br>
Category: <input type="text" name="category"> <br><br>

Question Text: <input type="text" name="questionText"> <br>
Answer: <input type="text" name="answer"> <br>
</form>
</body>
</html>