<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create QR</title>
</head>
<body>
<h1>New Question-Response Question</h1>
<form action="CreateQRServlet" method="post">
 <input type="hidden" value="100" name="qID">
 <input type="text" value="Enter your question!" name="qText">
 <input type="text" value="Enter your answer!" name="answerText">
 <input type="submit" value="Add Question">
</form>
</body>
</html>