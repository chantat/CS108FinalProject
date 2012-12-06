<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*, java.sql.*, userPackage.* ,announcement.*, achievement.*, mail.*, quiz.*" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz Master | <%= user %></title>
<%@include file="resources.jsp" %>
<% 
String user = (String)session.getAttribute("username");
if(user==null){
	System.out.println("user = null");
	out.println("<script type='text/javascript'>");
	out.println("window.location='announcements.jsp'");
	out.println("</script>");
}
%>
<script type="text/javascript">
	$(function() {
		$("#profileTabs").tabs({
			'show' : "slideDown"
		});
		$("#newQuizTable").dataTable({
			"bJQueryUI" : true
		});
		$("#quizCreatedTable").dataTable({
			"bJQueryUI" : true
		});
		$("#quizTakenTable").dataTable({
			"bJQueryUI" : true
		});
		$("#popularQuizTable").dataTable({
			"bJQueryUI" : true
		});
		$("#newMessage").button();
		$("#createQuiz").button();
	})
</script>
</head>
<body>
<%@include file="header.jsp" %>
<center><span style="font-size:16pt">Welcome back, <%= user %>!</span></center>

<br>
<% 
AccountManager acct = (AccountManager)application.getAttribute("manager");
MailSystem ms = (MailSystem)application.getAttribute("mailSystem");
%>
<div id="profileTabs">
	<ul>
		<li><a href="#quizTab">Quizzes</a></li>
		<li><a href="#friendTab">Friends</a></li>
		<li><a href="#inboxTab">Inbox (<%= ms.getUnreadForUser(user) %>)</a></li>
		<li><a href="#achievementTab">Achievements</a></li>
		<li><a href="#prefTab">Preferences</a></li>
		<% if (acct.isAdmin(user)) out.println("<li><a href='#adminTab'>Admin</a></li>"); %>
	</ul>
	<div id="quizTab">
		<a id="createQuiz" href="createQuiz.jsp">Create New Quiz</a>
		
		<h2>Quizzes You've Made</h2>
		<table id="quizCreatedTable">
		<thead>
			<tr>
				<th>Quiz</th>
				<th>Description</th>
				<th>Category</th>
				<th>Tags</th>
			</tr>
		</thead>
		<tbody>
			<% 
				QuizManager quizManager = (QuizManager)application.getAttribute("quizManager");
				Quiz[] quiz = quizManager.getAllQuizzes();
				
				if (quiz != null) {
				
					for (int i = 0; i < quiz.length ; i++) {
						if (quiz[i].getAuthorId().equals(user)) {
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
							
							out.println("<td> " + quizButton + "</td>");
							out.println("<td> " + description + "</td>");
							out.println("<td> " + category + "</td>");
							out.println("<td> " + tagString + "</td>");
							out.println("</tr>");
						}
					}
				}
				%>
		</tbody>
		</table>
		<br>
		
		<h2>Recently Taken Quizzes</h2>
		<table id="quizTakenTable">
		<thead>
			<tr>
				<th>Quiz</th>
				<th>Score</th>
				<th>Time</th>
			</tr>
		</thead>
		<tbody>
			<% 
				AttemptManager attemptManager = (AttemptManager)application.getAttribute("attemptManager");
				Attempt[] attempts = attemptManager.getAllAttempts(user);
				
				if (attempts != null) {
				
					for (int i = 0; i < attempts.length ; i++) {
						out.println("<tr>");;
						int quizID = attempts[i].getQuizId();
						String quizName = quizManager.getQuizName(quizID);
						double score = attempts[i].getScore();
						String time = attempts[i].getTimeTaken().toString();
						
						String quizButton = "<form action=\"QuizServlet\" method=\"post\">";
						quizButton += "<input type=\"hidden\" name = \"quizId\" value=\""+ quizID + "\">";
						quizButton += "<input type=\"submit\" value=\"" + quizName + "\">";
						quizButton += "</form>";
						
						out.println("<td> " + quizButton + "</td>");
						out.println("<td> " + score + "</td>");
						out.println("<td> " + time + "</td>");
						out.println("</tr>");
					}
				}
				%>
		</tbody>
		</table>
		<br>
		
		<h2>New Quizzes</h2>
		<table id="newQuizTable">
		<thead>
			<tr>
				<th>Quiz</th>
				<th>Category
				<th>Time</th>
			</tr>
		</thead>
		<tbody>
			<% 
				quiz = quizManager.getAllQuizzesSortTime();
				if (quiz != null) {
				
					for (int i=0; i<Math.min(10,quiz.length);i++) {
						if (quiz[i].getAuthorId().equals(user)) {
							out.println("<tr>");
							int quizId = quiz[i].getQuizId();
							String quizName = quiz[i].getName(); 
							String authorId = quiz[i].getAuthorId();
							String description = quiz[i].getDescription();
							String category = quiz[i].getCategory();
							String time = quiz[i].getTimeCreated().toString();
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
							
							out.println("<td> " + quizButton + "</td>");
							out.println("<td> " + category + "</td>");
							out.println("<td> " + time + "</td>");
							out.println("</tr>");
						}
					}
				}
			%>
		</tbody>
		</table>
		<br>
		
		<h2>Popular Quizzes</h2>
		<table id="popularQuizTable">
		<thead>
			<tr>
				<th>Quiz</th>
				<th>Average Rating</th>
			</tr>
		</thead>
		<tbody>
			<% 
				RatingManager ratingManager = (RatingManager)application.getAttribute("ratingManager");
				Rating[] popularQuizIDs=ratingManager.getMostPopularQuizzes();
				
				if (popularQuizIDs != null) {
				
					for (int i=0; i<popularQuizIDs.length;i++) {
						if (quiz[i].getAuthorId().equals(user)) {
							out.println("<tr>");
							int quizID = popularQuizIDs[i].getQuizID();
							String quizName = quizManager.getQuizName(quizID);
							double averageRating = popularQuizIDs[i].getRating();
							
							String quizButton = "<form action=\"QuizServlet\" method=\"post\">";
							quizButton += "<input type=\"hidden\" name = \"quizId\" value=\""+ quizID + "\">";
							quizButton += "<input type=\"submit\" value=\"" + quizName + "\">";
							quizButton += "</form>";
							
							out.println("<td> " + quizButton + "</td>");
							out.println("<td> " + averageRating + "</td>");
							out.println("</tr>");
						}
					}
				}
				%>
		</tbody>
		</table>
	</div>
	<div id="friendTab">
		<form action="UserSearchServlet" method="post">
			User Search: <input type="text" name="victim"> 
			<input type="submit" value="Search for User">
		</form>
		
		<h2>Friends List</h2>
		<table id="friendTable">
		<%
			FriendManager friendMgr = (FriendManager)application.getAttribute("friendManager");
			
			
			//TEST
			System.out.println("Friend Table");
			friendMgr.dumpFriendTable();
			System.out.println("Req Table");
			friendMgr.dumpRequestTable();
			
			
			ArrayList<String> friends = friendMgr.getFriends(user);
			ArrayList<String> requests = friendMgr.getRequests(user);
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
		
		%>
		</table>
		<a href="#">see all</a>
		
		<h2>Friend Requests Received</h2>
		<table id="friendRequestTable">
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
				String quizName = quizManager.getQuizName(quizID);
				double score = recentAttempts.get(i).getScore();
				String time = recentAttempts.get(i).getTimeTaken().toString();
				out.println("<td> "+friendID+"</td>");
				out.println("<td> "+quizName+"</td>");
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
		
	</div>
	<div id="inboxTab">
		<%!
			String user;
			MailSystem ms;
			List<Message> inbox;
			Message msg;
			List<Request> requests;
			Request rqst;
			List<Challenge> challenges;
			Challenge chlg;
			%>
			<h1>Inbox</h1>
			<a id="newMessage" href="compose.jsp">New Message</a>
			
			<table>
			<tr>
			<th>Status</th>
			<th>From</th>
			<th>Subject</th>
			<th>Time</th>
			</tr>
			<%
			user = (String) session.getAttribute("username");
			ms = (mail.MailSystem) application.getAttribute("mailSystem");
			inbox = ms.getInboxForUser(user);
			for (int i = 0; i < inbox.size(); i++) {
				msg = inbox.get(i);%>
				<tr>
				<% if(msg.getStatus() == 1) {%>
				<td>Read</td>
				<% } else { %>
				<td>Unread</td>
				<% } %>
				<td><%= msg.getFromID() %></td>
				<td>
				<form action="ReadServlet" method="post">
				<input type="submit" value="<%= msg.getSubject() %>"/>
				<input name="fromID" type="hidden" value="<%= msg.getFromID() %>"/>
				<input name="timeStamp" type="hidden" value="<%= msg.getTime() %>"/>
				</form>
				</td>
				<td><%= msg.getTime() %></td>
				</tr>
			<%}%>
			</table>
			
			<h1>Friend Requests</h1>
			<% 
			friendMgr = (userPackage.FriendManager)application.getAttribute("friendManager"); 
			requests = friendMgr.getRequests(user);
			%>
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
			
			<h1>Challenges</h1>
			<table>
			<tr>
			<th>Read</th>
			<th>From</th>
			<th>Quiz</th>
			<th>Time</th>
			</tr>
			<%
			challenges = ms.getChallengesForUser(user);
			for (int i = 0; i < challenges.size(); i++) {
				chlg = challenges.get(i);%>
				<tr>
				<% if(chlg.getStatus() == 1) {%>
				<td>Read</td>
				<% } else { %>
				<td>Unread</td>
				<% } %>
				<td><%= chlg.getFromID() %></td>
				<td><%= chlg.getQuizID() %></td>
				<td><%= chlg.getTime() %></td>
				</tr>
			<%}
			%>
			</table>
	</div>
	<div id="achievementTab">
		<table border="1">
			<% 
			
			
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
			
			
			
			
			%>
		</table>
	</div>
	<div id="prefTab">
		<h2>Privacy Preferences</h2>

		<form action="ChangePrivacyServlet" method="post">
		<input type="checkbox" name="privacy1" value="Public" <%if(acct.isPerfPublic(user)) out.print("checked");  %>>I want my quiz scores public<br>
		<input type="checkbox" name="privacy2" value="Public" <%if(acct.isPagePublic(user)) out.print("checked");  %>>I want my user page to be visible to all<br>
		<br><input type="submit" value="Update">
		
		</form>
	</div>
	<%
	if (acct.isAdmin(user)) {
		out.println("<div id='adminTab'>");
		
		out.println("<h2>Flagged Quizzes</h2>");
		out.println("<table id='flaggedQuizTable'>");
		
		ReportManager reportMGR = (ReportManager)application.getAttribute("reportManager");
		Report[] reports = reportMGR.getAllReported();
		if(reports.length>0){
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
		}
		
		out.println("</table>");
		
		out.println("<h2>Enter Announcement</h2>");
		out.println("<form action='AdministratorServlet' method='post'>");
		out.println("<input type='text' value='Subject' name='subject'><br>");
		out.println("<textarea name='text'></textarea><br>");
		String adminId = (String)session.getAttribute("username");
		out.println("<input type='hidden' name='adminId' value='"+ adminId + "'/>");
		out.println("<input type='hidden' name='function' value='create_announcement'/>");
		out.println("<input type='submit' value='Post'/>");
		out.println("</form>");
	
		out.println("<h2>Remove User Account</h2>");
		out.println("<form action='AdministratorServlet' method='post'>");
		out.println("Username <input type='text' name='username'><br>");
		out.println("<input type='hidden' name='function' value='remove_account'/>");
		out.println("<input type='submit' value='Remove User'/>");
		out.println("</form>");
		
		out.println("<h2>Remove Quiz</h2>");
		out.println("<form action='AdministratorServlet' method='post'>");
		out.println("Quiz ID: <input type='text' name='quizId'><br>");
		out.println("<input type='hidden' name='function' value='remove_quiz'/>");
		out.println("<input type='submit' value='Remove Quiz'/>");
		out.println("</form>");

		out.println("<h2>Clear Quiz History</h2>");
		out.println("<form action='AdministratorServlet' method='post'>");
		out.println("Quiz ID: <input type='text' name='quizId'><br>");
		out.println("<input type='hidden' name='function' value='clear_quiz_history'/>");
		out.println("<input type='submit' value='Clear'/>");
		out.println("</form>");

		out.println("<h2>Promote User to Admin</h2>");
		out.println("<form action='AdministratorServlet' method='post'>");
		out.println("Username <input type='text' name='username'><br>");
		out.println("<input type='hidden' name='function' value='promote_admin'/>");
		out.println("<input type='submit' value='Promote'/>");
		out.println("</form>");
		
		out.println("<h2>Site Statistics</h2>");
		out.println("<h3>Total Number of Users</h3>");
		AccountManager acctMGR = (AccountManager)application.getAttribute("manager");
		int userPop = acctMGR.getPopulation();
		out.print(userPop);
		
		out.println("<h3>Total Number of Quizzes</h3>");
		Quiz[] allQuiz = quizManager.getAllQuizzes();
		out.print(allQuiz.length);
		
		out.println("<h3>Average Quiz Rating</h3>");
		RatingManager ratingMGR = (RatingManager)application.getAttribute("ratingManager");
		out.print(ratingMGR.getTotalAverageRating());
		
		out.println("</div>");
	}
	%>
</div>

</body>
</html>