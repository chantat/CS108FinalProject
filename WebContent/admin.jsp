<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Administrator Page</title>
</head>
<body>
<h1>Administrator Page</h1>
Enter Announcement: 
<form action="AdministratorServlet" method="post">
Announcement: <input type="text" name="announcement">  <br>
<input type="hidden" name="function" value="create_announcement"/>
<input type="submit" value="Submit"/>
</form>

Remove User Account
<form action="AdministratorServlet" method="post">
Username: <input type="text" name="username">  <br>
<input type="hidden" name="function" value="remove_account"/>
<input type="submit" value="Submit"/>
</form>

Remove quiz
<form action="AdministratorServlet" method="post">
QuizId: <input type="text" name="quizId">  <br>
<input type="hidden" name="function" value="remove_quiz"/>
<input type="submit" value="Submit"/>
</form>

Clear history
<form action="AdministratorServlet" method="post">
QuizId: <input type="text" name="quizId">  <br>
<input type="hidden" name="function" value="clear_quiz_history"/>
<input type="submit" value="Submit"/>
</form>

Promote account
<form action="AdministratorServlet" method="post">
Username: <input type="text" name="username">  <br>
<input type="hidden" name="function" value="promote_admin"/>
<input type="submit" value="Submit"/>
</form>

</body>
</html>