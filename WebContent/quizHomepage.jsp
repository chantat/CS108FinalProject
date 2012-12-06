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
<title>Quiz Home Page</title>
</head>
<body>

<table border="1">
<% 
QuizManager quizManager = (QuizManager)application.getAttribute("quizManager");
Quiz[] quiz = quizManager.getAllQuizzes();

for (int i = 0; i < quiz.length ; i++) {
	out.println("<tr>");
	int quizId = quiz[i].getQuizId();
	String quizName = quiz[i].getName(); 
	String authorId = quiz[i].getAuthorId();
	String description = quiz[i].getDescription();
	String category = quiz[i].getCategory();
	ArrayList<String> tags = quiz[i].getTags();
	
	String tagString = "";
	for (int j = 0; j < tags.size(); j++) {
		if (j != 0) {
			tagString += ", ";
		}
		tagString += tags.get(j);
	}
	
	String quizButton = "<form action=\"QuizServlet\" method=\"post\">";
	quizButton += "<input type=\"hidden\" name = \"quizId\" value=\""+ quizId + "\">";
	quizButton += "<input type=\"submit\" value=\"" + quizName + "\">";
	quizButton += "</form>";
	
	String editButton = "<form action=\"EditQuizServlet\" method=\"post\">";
	editButton += "<input type=\"hidden\" name = \"quizId\" value=\""+ quizId + "\">";
	editButton += "<input type=\"submit\" value=\"Edit\">";
	editButton += "</form>";
	
	out.println("<td> " + quizButton + "</td>");
	out.println("<td> " + editButton + "</td>");
	out.println("<td> " + authorId + "</td>");
	out.println("<td> " + description + "</td>");
	out.println("<td> " + category + "</td>");
	out.println("<td> " + tagString + "</td>");
	out.println("</tr>");
}



%>

</table>
<A HREF="http://localhost:8080/CS108FinalProject/userHomePage.jsp">Return to Home Page</A>
</body>
</html>