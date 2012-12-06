<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ page import="java.util.*, userPackage.* ,announcement.*, achievement.*, quiz.*, java.sql.*, mail.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<% 
String quote = "\"";
String redirect = "<meta http-equiv=" +quote+ "refresh"+quote+" content="+quote+"1;url=userLogin.jsp"+quote+">";
String user = (String)session.getAttribute("username");

%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Taken Quizzes</title>
</head>
<body>


<h2>Taken Quizzes</h2>
<table border="1">
<% if(user!=null){
	//String user = (String)session.getAttribute("username");
	
	AttemptManager attemptMGR = (AttemptManager)application.getAttribute("attemptManager");
	Attempt[] attempts = attemptMGR.getAllAttempts(user);
	
	for(int i=0; i<attempts.length;i++){
		out.println("<tr>");;
		int quizID = attempts[i].getQuizId();
		double score = attempts[i].getScore();
		String time = attempts[i].getTimeTaken().toString();
		out.println("<td> "+quizID+"</td>");
		out.println("<td> "+score+"</td>");
		out.println("<td> "+time+"</td>");
		out.println("</tr>");
	}
}
%>

</table>

<A HREF="http://localhost:8080/CS108FinalProject/userHomePage.jsp">Return to Home Page</A>
</body>
</html>