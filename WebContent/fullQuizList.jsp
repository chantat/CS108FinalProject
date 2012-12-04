<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ page import="java.util.*, java.lang.Math.*, userPackage.* ,announcement.*, achievement.*, quiz.*, java.sql.*, mail.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz List</title>
</head>
<body>

<h2>Newest Quizzes</h2>
<table border="1">
<% 
QuizManager quizMGR = (QuizManager)application.getAttribute("quizManager");
Quiz[] quizzes = quizMGR.getAllQuizzesSortTime();

for(int i=0; i<quizzes.length;i++){
	out.println("<tr>");;
	int quizID = quizzes[i].getQuizId();
	String quizName = quizMGR.getQuizName(quizID);
	String time = quizzes[i].getTimeCreated().toString();
	String category = quizzes[i].getCategory();
	out.println("<td> "+quizName+"</td>");
	out.println("<td> "+category+"</td>");
	out.println("<td> "+time+"</td>");
	out.println("</tr>");
}
%>

</table>
</body>
</html>