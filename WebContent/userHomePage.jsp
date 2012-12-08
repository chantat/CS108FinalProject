<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*, java.sql.*, userPackage.* ,announcement.*, achievement.*, mail.*, quiz.*, java.text.*" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
<title>Quiz Master | <%= user %></title>
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
		$("#flaggedQuizTable").dataTable();
		$("#recentFriendAttemptsTable").dataTable();
		$("#recentFriendAchieveTable").dataTable();
		$("#inboxTable").dataTable();
		$("#achieveTable").dataTable();
		$("#newMessage").button();
		$("#createQuizLink").button();
		$("#quizTakenTable a").button();
		$("#quizCreatedTable a").button();
		$("#newQuizTable a").button();
		$("#popularQuizTable a").button();
		$("#recentFriendAttemptsTable a").button();
		$("#flaggedQuizTable a").button();
	});
</script>
</head>
<body>
<%@include file="header.jsp" %>
<center><span style="font-size:16pt">Welcome back, <%= user %>!</span></center>

<br>
<% 
AccountManager acct = (AccountManager)application.getAttribute("manager");
MailSystem ms = (MailSystem)application.getAttribute("mailSystem");
if (request.getAttribute("err") != null) {
	out.println("<center><div class='ui-widget' style='width:350px'>");
	out.println("<div class='ui-state-error ui-corner-all' style='padding: 0 .7em;'>");
	out.println("<p><span class='ui-icon ui-icon-alert' style='float: left; margin-right: .3em;'></span>");
	
	if (request.getAttribute("err").equals("doesNotExist")) {
		out.println("<strong>Sorry!</strong> No such user found.</p>");
	}
	
	out.println("</div>");
	out.println("</div></center>");
	out.println("<br>");
}
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
		<a id="createQuizLink" href="createQuiz.jsp">Create New Quiz</a>
		
		<h2>Quizzes You've Made</h2>
		<div id="quizCreatedTableContent">
		<table id="quizCreatedTable">
		<thead>
			<tr>
				<th>Quiz</th>
				<th>Description</th>
				<th>Category</th>
				<th>Tags</th>
				<th>Edit</th>
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
							
							String editButton = "<form action=\"EditQuizServlet\" method=\"post\">";
							editButton += "<input type=\"hidden\" name = \"quizId\" value=\""+ quizId + "\">";
							editButton += "<input type=\"submit\" value=\"" + "Edit" + "\">";
							editButton += "</form>";
							
							String quizSummaryLink = "<a href='quizSummary.jsp?qID=" + quizId + "'>" + quizName + "</a>";
							
							out.println("<td> " + quizSummaryLink + "</td>");
							out.println("<td> " + description + "</td>");
							out.println("<td> " + category + "</td>");
							out.println("<td> " + tagString + "</td>");
							out.println("<td> " + editButton + "</td>");
							out.println("</tr>");
						}
					}
				}
				%>
		</tbody>
		</table>
		</div>
		<br>
		
		<h2>Recently Taken Quizzes</h2>
		<div id="quizTakenTableContent">
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
						
						String quizSummaryLink = "<a href='quizSummary.jsp?qID=" + quizID + "'>" + quizName + "</a>";
						
						out.println("<td> " + quizSummaryLink + "</td>");
						out.println("<td> " + score + "</td>");
						out.println("<td> " + time + "</td>");
						out.println("</tr>");
					}
				}
				%>
		</tbody>
		</table>
		</div>
		<br>
		
		<h2>New Quizzes</h2>
		<div id="newQuizTableContent">
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
					//	if (quiz[i].getAuthorId().equals(user)) {
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
							
							String quizSummaryLink = "<a href='quizSummary.jsp?qID=" + quizId + "'>" + quizName + "</a>";
							
							out.println("<td> " + quizSummaryLink + "</td>");
							out.println("<td> " + category + "</td>");
							out.println("<td> " + time + "</td>");
							out.println("</tr>");
				//		}
					}
				}
			%>
		</tbody>
		</table>
		</div>
		<br>
		
		<h2>Popular Quizzes</h2>
		<div id="popularQuizTableContent">
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
			//			if (quiz[i].getAuthorId().equals(user)) {
							out.println("<tr>");
							int quizID = popularQuizIDs[i].getQuizID();
							String quizName = quizManager.getQuizName(quizID);
							double averageRating = popularQuizIDs[i].getRating();
							
							String quizButton = "<form action=\"QuizServlet\" method=\"post\">";
							quizButton += "<input type=\"hidden\" name = \"quizId\" value=\""+ quizID + "\">";
							quizButton += "<input type=\"submit\" value=\"" + quizName + "\">";
							quizButton += "</form>";
							
							String quizSummaryLink = "<a href='quizSummary.jsp?qID=" + quizID + "'>" + quizName + "</a>";
							
							out.println("<td> " + quizSummaryLink + "</td>");
							out.println("<td> " + averageRating + "</td>");
							out.println("</tr>");
		//				}
					}
				}
				%>
		</tbody>
		</table>
		</div>
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
		
		<div id="recentFriendAttemptsTableContent">
		<table id="recentFriendAttemptsTable">
		<thead>
			<tr>
				<th>Friend</th>
				<th>Quiz</th>
				<th>Score</th>
				<th>Time</th>
			</tr>
		</thead>
		<tbody>
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
				
				String quizSummaryLink = "<a href='quizSummary.jsp?qID=" + quizID + "'>" + quizName + "</a>";
				
				out.println("<td> "+friendID+"</td>");
				out.println("<td> "+quizSummaryLink+"</td>");
				out.println("<td> "+score+"</td>");
				out.println("<td> "+time+"</td>");
				out.println("</tr>");
			}
		}
		%>
		</tbody>
		</table>
		</div>
		<br><br>
		<h3>Recent Friend Achievements</h3>
		
		<div id="recentFriendAchieveTableContent">
		<table id="recentFriendAchieveTable">
		<thead>
		<tr>
			<th>Achievement</th>
			<th>Friend</th>
			<th>Description</th>
			<th>Time</th>
		</tr>
		</thead>
		<tbody>
		<% 
		AchievementManager achMGR = (AchievementManager)application.getAttribute("achievementManager");
		ArrayList<Achievement> recentAchievements = friendMgr.getFriendRecentAchievements(user);
		if(recentAchievements.size()!=0){   //make sure some recent achievements exist	
			for(int i=0; i<recentAchievements.size();i++){
				out.println("<tr>");
				String friendID = recentAchievements.get(i).getName();
				String desc = recentAchievements.get(i).getDescription();
				String time = recentAchievements.get(i).getWhenAchieved().toString();
				int achieveID = recentAchievements.get(i).getAchieveID();
				String URL = achMGR.getIconURL(achieveID);
				out.println("<td> "+URL+"</td>");
				out.println("<td> "+friendID+"</td>");
				out.println("<td> "+desc+"</td>");
				out.println("<td> "+time+"</td>");
				out.println("</tr>");
			}
		}
		
		%>
		</tbody>
		</table>
		</div>
		<br>
		
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
			<br><br>
			
			<div id="inboxTableContent">
			<table id="inboxTable">
			<thead>
			<tr>
			<th>Status</th>
			<th>From</th>
			<th>Subject</th>
			<th>Time</th>
			<th>Delete</th>
			</tr>
			</thead>
			<tbody>
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
				<%
				String subject = msg.getSubject();
				String subStr = subject;
				if (subject.length() > 25) {
					subStr = subject.substring(0, 22);
					subStr += "...";
				}
				%>
				<form action="ReadServlet" method="post">
				<input type="submit" value="<%= subStr %>"/>
				<input name="fromID" type="hidden" value="<%= msg.getFromID() %>"/>
				<input name="timeStamp" type="hidden" value="<%= msg.getTime() %>"/>
				</form>
				</td>
				<% String dateStr = new SimpleDateFormat("MM/dd/yy h:mm a").format(msg.getTime()); %>
				<td><%= dateStr %></td>
				<td>
				<form action="DeleteServlet" method="post">
				<input type="submit" value="Delete"/>
				<input name="fromID" type="hidden" value="<%= msg.getFromID() %>"/>
				<input name="timeStamp" type="hidden" value="<%= msg.getTime() %>"/>
				</form>
				</td>
				</tr>
			<%}%>
			</tbody>
			</table>
			</div>
			<br>
	</div>
	<div id="achievementTab">
		<h2>Achievements You've Unlocked</h2>
		<div id="achieveTableContent">
		<table id="achieveTable">
		<thead>
			<tr>
			<th>Achievement</th>
			<th>Name</th>
			<th>Description</th>
			</tr>
		</thead>
		<tbody>
			<% 
			

			Achievement[] achList = achMGR.getAllAchievement(user);
	
			
			for(int i=0; i<achList.length;i++){
				if(achList[i].getIsAchieved()){
					out.println("<tr>");
					String achName = achList[i].getName();
					String describe = achList[i].getDescription();
					int achieveID = achList[i].getAchieveID();
					String URL = achMGR.getIconURL(achieveID);
					out.println("<td> "+URL+"</td>");
					out.println("<td> "+achName+"</td>");
					out.println("<td> "+describe+"</td>");
					out.println("</tr>");
				}
			}
			

			
			
			%>
		</tbody>
		</table>
		</div><br>
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
		out.println("<div id='flaggedQuizTableContent'>");
		out.println("<table id='flaggedQuizTable'>");%>
		<thead>
		<tr>
			<th>Quiz ID</th>
			<th>Name</th>
			<th>Occurrences</th>
			<th>Last Flagged</th>
		</tr>
		</thead>
		<tbody>
		<%
		QuizManager quizMGR = (QuizManager)application.getAttribute("quizManager");
		ReportManager reportMGR = (ReportManager)application.getAttribute("reportManager");
		Report[] reports = reportMGR.getAllReported();
		if(reports != null && reports.length>0){
			for(int i=0; i<reports.length;i++){
				Report temp = reports[i];
				out.println("<tr>");
				int quizID = temp.getQuizID();
				int occurence = temp.getOccurrence();
				String date = temp.getDate().toString();
				String quizName = quizMGR.getQuizName(quizID);
				String quizSummaryLink = "<a href='quizSummary.jsp?qID=" + quizID + "'>" + quizName + "</a>";		
				out.println("<td> "+quizID+"</td>");
				out.println("<td> "+quizSummaryLink+"</td>");
				out.println("<td> "+occurence+"</td>");
				out.println("<td> "+date+"</td>");
				out.println("</tr>");
			}
		}
		out.println("</tbody>");
		out.println("</table>");
		out.println("</div><br>");
		
		out.println("<h2>Post Announcement</h2>");
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