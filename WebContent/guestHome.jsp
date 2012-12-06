<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@ page import="java.util.*, java.lang.Math.*, userPackage.* ,announcement.*, achievement.*, quiz.*, java.sql.*, mail.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Guest Home Page</title>
</head>
<body>

<h1>Guest Home Page</h1>

<h2>Announcements</h2>
<table border="1">
<% 
AnnouncementManager announceMGR = (AnnouncementManager)application.getAttribute("announcementManager");
Announcement[] announce = announceMGR.getAllAnnouncement();

for(int i=0; i<announce.length;i++){
	out.println("<tr>");
	String adminName = announce[i].getAdminId();
	String text = announce[i].getAnnouncementText();
	String subject = announce[i].getSubject();
	String time = announce[i].getPostTime().toString();
	out.println("<td> "+adminName+"</td>");
	out.println("<td> "+subject+"</td>");
	out.println("<td> "+time+"</td>");
	out.println("<td> "+text+"</td>");
	out.println("</tr>");
}



%>

</table>

<h2>Quiz List</h2>

<h2>Newest Quizzes</h2>
<table border="1">
<% 
QuizManager quizMGR = (QuizManager)application.getAttribute("quizManager");
Quiz[] quizzes = quizMGR.getAllQuizzesSortTime();

for(int i=0; i<Math.min(10,quizzes.length);i++){
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
<A HREF="http://localhost:8080/CS108FinalProject/fullQuizList.jsp">See Full Quiz List</A>


<h2>Popular Quizzes</h2>
<table border="1">
<% 
RatingManager ratingManager = (RatingManager)application.getAttribute("ratingManager");
Rating[] popularQuizIDs=ratingManager.getMostPopularQuizzes();
for(int i=0; i<popularQuizIDs.length;i++){
	out.println("<tr>");;
	int quizID = popularQuizIDs[i].getQuizID();
	String quizName =quizMGR.getQuizName(quizID);
	double averageRating = popularQuizIDs[i].getRating();
	out.println("<td> "+quizName+"</td>");
	out.println("<td> "+ averageRating+"</td>");
	out.println("</tr>");
}
%>
</table>

</body>
</html>