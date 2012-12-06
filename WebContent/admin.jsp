<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*, quiz.*, userPackage.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<% 
String quote = "\"";
String redirect = "<meta http-equiv=" +quote+ "refresh"+quote+" content="+quote+"1;url=userLogin.jsp"+quote+">";
String user = (String)session.getAttribute("username");
if(user==null){
	System.out.println("user = null");
	out.print(redirect);
}
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Administrator Page</title>
</head>
<body>
<h1>Administrator Page</h1>
Enter Announcement: 
<form action="AdministratorServlet" method="post">
Announcement: <input type="text" name="subject"> <input type="text" name="text">  <br>
<%String adminId = (String)session.getAttribute("username");%>
<input type="hidden" name="adminId" value="<% out.print(adminId); %>"/>
<input type="hidden" name="function" value="create_announcement"/>
<input type="submit" value="Submit"/>
</form>

Remove User Account
<form action="AdministratorServlet" method="post">
Username: <input type="text" name="username">  <br>
<input type="hidden" name="function" value="remove_account"/>
<input type="submit" value="Submit"/>
</form>

Flagged Quizzes

<table border="1">
<% 
ReportManager reportMGR = (ReportManager)application.getAttribute("reportManager");
Report[] reports = reportMGR.getAllReported();
for(int i=0; i<reports.length;i++){
	Report temp = reports[i];
	out.println("<tr>");
	int quizID = temp.getQuizID();
	int occurence = temp.getOccurence();
	String date = temp.getDate().toString();
	out.println("<td> "+quizID+"</td>");
	out.println("<td> "+occurence+"</td>");
	out.println("<td> "+date+"</td>");
	out.println("</tr>");
}



%>

</table>

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

Site Statistics:

Total Number of Users:  <% 

AccountManager acctMGR = (AccountManager)application.getAttribute("manager");
int userPop = acctMGR.getPopulation();
out.print(userPop); %>

Total Number of Quizzes:  <%
QuizManager quizMGR = (QuizManager)application.getAttribute("quizManager");
Quiz[] allQuiz = quizMGR.getAllQuizzes();
out.print(allQuiz.length);

%>


<A HREF="http://localhost:8080/CS108FinalProject/userHomePage.jsp">Return to Home Page</A>


</body>
</html>