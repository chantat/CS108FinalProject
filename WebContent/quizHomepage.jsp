<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*, quiz.*, userPackage.*, forum.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz Home Page</title>
<%@include file="resources.jsp" %>
<script type="text/javascript">
	$(document).ready(function() {
		$("#quizTable").dataTable({
			//"bJQueryUI" : true
		});
		$("#quizTable a").button();
	});
</script>
</head>
<body>
<%@include file="header.jsp" %>
<center><h1>All Quizzes</h1></center>
<div id="quizHomepageContent">
<% 
QuizManager quizManager = (QuizManager)application.getAttribute("quizManager");
Quiz[] quiz = quizManager.getAllQuizzes();
ReviewManager reviewmanager = (ReviewManager)application.getAttribute("reviewManager");
RatingManager ratingManager = (RatingManager)application.getAttribute("ratingManager");

out.println("<table id='quizTable'>");
out.println("<thead>");
out.println("<tr>");
out.println("<th>Quiz</th>");
out.println("<th>Author</th>");
out.println("<th>Description</th>");
out.println("<th>Category</th>");
out.println("<th>Tags</th>");
out.println("<th>Average Rating</th>");
out.println("<th>Forum</th>");
out.println("</tr>");
out.println("</thead>");
out.println("<tbody>");
for (int i = 0; i < quiz.length ; i++) {
	out.println("<tr>");
	int quizId = quiz[i].getQuizId();
	String quizName = quiz[i].getName(); 
	String authorId = quiz[i].getAuthorId();
	String description = quiz[i].getDescription();
	String category = quiz[i].getCategory();
	double avgRating = ratingManager.getAverageRating(quizId);
	
	String rating="";
	if(avgRating == - 1){
		rating="No ratings";
	}else{
		rating="" + avgRating;
	}
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
	
	String quizSummaryLink = "<a href='quizSummary.jsp?qID=" + quizId + "'>" + quizName + "</a>";
	
	/*String editButton = "<form action=\"EditQuizServlet\" method=\"post\">";
	editButton += "<input type=\"hidden\" name = \"quizId\" value=\""+ quizId + "\">";
	editButton += "<input type=\"submit\" value=\"Edit\">";
	editButton += "</form>";*/
	
	out.println("<td> " + quizSummaryLink + "</td>");
	out.println("<td> " + authorId + "</td>");
	out.println("<td> " + description + "</td>");
	out.println("<td> " + category + "</td>");
	out.println("<td> " + tagString + "</td>");
	out.println("<td> " + rating + "</td>");
	//String reviewLink="<td><a href=\"ReviewServlet?ID=" + quizId + "\"> Read reviews</a></td>";
	//out.println(reviewLink);
	String forumLink="<td><a href=\"ForumServlet?ID=" + quizId + "\"> Discuss Quiz</a></td>";
	out.println(forumLink);
	out.println("</tr>");
}
out.println("</tbody>");
out.println("</table>");


%>
</div>
<br><br>
</body>
</html>