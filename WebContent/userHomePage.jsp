<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*, java.lang.Math.*, userPackage.* ,announcement.*, achievement.*, quiz.*, java.sql.*, mail.*" %>
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
<title>User Home Page</title>
</head>
<body>
<h1><%
if(user!=null){
	out.print(user);
	
}
%>'s Home Page</h1>

<% MailSystem ms = (MailSystem)application.getAttribute("mailSystem"); %>
<a href="inbox.jsp">Inbox(<%= ms.getUnreadForUser(user) %>)</a>


<form action="UserSearchServlet" method="post">
User Search: <input type="text" name="victim"> 
<input type="submit" value="Search for User">
</form>


<% 
/*
<h2>Friend List</h2>
<A HREF="http://localhost:8080/CS108FinalProject/FriendList.jsp">See Friend List</A>
*/
%>

<h2>Friends List</h2>


<table border="1">

<%
ArrayList<String> requests = null;
AccountManager acct = null;
FriendManager friendMgr =null;

if(user!=null){
	friendMgr = (FriendManager)application.getAttribute("friendManager");
	acct = (AccountManager)application.getAttribute("manager");
	
/*	
	//TEST
	System.out.println("Friend Table");
	friendMgr.dumpFriendTable();
	System.out.println("Req Table");
	friendMgr.dumpRequestTable();
*/	
	
	ArrayList<String> friends = friendMgr.getFriends(user);
	requests = friendMgr.getRequests(user);
	for(int i=0; i<friends.size();i++){
		String friendName = friends.get(i);
		String linkButton = "<form action=\"UserSearchServlet\" method=\"post\"><input type=\"hidden\" name = \"victim\" value=\"" +friendName +"\"><input type=\"submit\" value=\""+friendName+"\"></form>";
		out.println("<tr>");
		out.println("<td> "+linkButton+"</td>");
		String removeButton = "<form action=\"RemoveFriendServlet\" method=\"post\"><input type=\"hidden\" name = \"victim\" value=\"" +friendName +"\"><input type=\"submit\" value=\"Remove From Friend List\"></form>";
		out.println("<td> "+removeButton+"</td>");
		String composeLink = "<form action=\"ComposeServlet\" method=\"post\"><input type=\"hidden\" name=\"toID\" value=\"" +friendName+"\"><input type=\"submit\" value=\"Send Message\"></form>";
		out.println("<td> "+composeLink+"</td>");
		out.println("</tr>");
	}
}
%>

</table>

<h2>Friend Requests Received</h2>


<table border="1">
<% 
for(int i=0; i<requests.size();i++){
	out.println("<tr>");
	String requestorName = requests.get(i);
	out.println("<td> "+requestorName+"</td>");
	String approveButton = "<form action=\"AddFriendServlet\" method=\"post\"><input type=\"hidden\" name = \"victim\" value=\""+ requestorName+ "\"><input type=\"submit\" value=\"Approve Request\"></form>";
	out.println("<td> "+approveButton+"</td>");
	out.println("</tr>");
}



%>

</table>




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


<h2>Recently Taken Quizzes</h2>
<table border="1">
<% 
if(user!=null){
	AttemptManager attemptMGR = (AttemptManager)application.getAttribute("attemptManager");
	Attempt[] attempts = attemptMGR.getAllAttempts(user);
	
	for(int i=0; i<Math.min(10,attempts.length);i++){
		out.println("<tr>");;
		int quizID = attempts[i].getQuizId();
		String quizName =quizMGR.getQuizName(quizID);
		double score = attempts[i].getScore();
		String time = attempts[i].getTimeTaken().toString();
		out.println("<td> "+quizName+"</td>");
		out.println("<td> "+score+"</td>");
		out.println("<td> "+time+"</td>");
		out.println("</tr>");
	}
}
%>

</table>
<A HREF="http://localhost:8080/CS108FinalProject/fullAttemptsList.jsp">See Full History</A>

<h2>Friends' Recent Activity</h2>

<h3>Recent Friend Quiz Attempts</h3>

<table border="1">
<% 

ArrayList<Attempt> recentAttempts = friendMgr.getFriendRecentAttempts(user);
if(recentAttempts.size()!=0){   //make sure some attempts exist
	for(int i=0; i<recentAttempts.size();i++){
		out.println("<tr>");
		String friendID = recentAttempts.get(i).getUserId();
		int quizID = recentAttempts.get(i).getQuizId();
		String quizName =quizMGR.getQuizName(quizID);
		double score = recentAttempts.get(i).getScore();
		String time = recentAttempts.get(i).getTimeTaken().toString();
		out.println("<td> "+friendID+"</td>");
		out.println("<td> "+quizID+"</td>");
		out.println("<td> "+score+"</td>");
		out.println("<td> "+time+"</td>");
		out.println("</tr>");
	}
}
%>

</table>

<h3>Recent Friend Achievements</h3>
<table border="1">
<% 

ArrayList<Achievement> recentAchievements = friendMgr.getFriendRecentAchievements(user);
if(recentAchievements.size()!=0){   //make sure some recent achievements exist	
	for(int i=0; i<recentAchievements.size();i++){
		out.println("<tr>");
		String friendID = recentAchievements.get(i).getName();
		String desc = recentAchievements.get(i).getDescription();
		String time = recentAchievements.get(i).getWhenAchieved().toString();
		out.println("<td> "+friendID+"</td>");
		out.println("<td> "+desc+"</td>");
		out.println("<td> "+time+"</td>");
		out.println("</tr>");
	}
}

%>

</table>


<h2>Achievements</h2>

<table border="1">
<% 
if(user!=null){

	AchievementManager achMGR = (AchievementManager)application.getAttribute("achievementManager");
	Achievement[] achList = achMGR.getAllAchievement(user);
	
	for(int i=0; i<achList.length;i++){
		if(achList[i].getIsAchieved()){
			out.println("<tr>");
			String achName = achList[i].getName();
			String describe = achList[i].getDescription();
			
			out.println("<td> "+achName+"</td>");
			out.println("<td> "+describe+"</td>");
			out.println("</tr>");
		}
	}
}


%>
</table>

<h2>Privacy Preferences</h2>

<form action="ChangePrivacyServlet" method="post">
<input type="checkbox" name="privacy1" value="Public" <%if(acct.isPerfPublic(user)) out.print("checked");  %>>I want my quiz scores public<br>
<input type="checkbox" name="privacy2" value="Public" <%if(acct.isPagePublic(user)) out.print("checked");  %>>I want my user page to be visible to all<br>
<input type="submit" value="Submit">

</form>


<%    //enables Admin page if user is Admin

if(acct.isAdmin(user)){
	String returnLink = "<A HREF=\"http://localhost:8080/CS108FinalProject/admin.jsp\">Administration Page</A>";
	out.println(returnLink);
}
%>

<% // Link to quiz Homepage 
String quizHomepageLink = "<A HREF=\"quizHomepage.jsp\">Quiz Homepage</A>";
out.println(quizHomepageLink);
%>


<% // Link to create quiz page

String createQuizLink = "<A HREF=\"createQuiz.jsp\">Create Quiz</A>";
out.println(createQuizLink);

%>



<form action="DeleteServlet" method="post">
<input type="submit" value="Delete My Account">
</form>


<form action="LogOutServlet" method="post">
<input type="submit" value="Log Out">
</form>


</body>
</html>