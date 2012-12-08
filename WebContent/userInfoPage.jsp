<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="java.util.*, userPackage.*,achievement.*,quiz.*,java.lang.Math" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz Master | <%=(String)request.getParameter("victim") %></title>
<%@include file="resources.jsp" %>
<script type="text/javascript">
	$(function() {
		$("#commonFriendsTable").dataTable();
		$("#quizTakenTable").dataTable();
		$("#achieveTable").dataTable();
	});
</script>
</head>
<body>
<%@include file="header.jsp" %>
<center><h1><%=(String)request.getParameter("victim") %></h1></center>
<div id="userInfoContent">
<% 
String user = (String)session.getAttribute("username");
String victim =(String)request.getParameter("victim");

String requestButton = "<form action=\"AddRequestServlet\" method=\"post\"><input name = victim type=\"hidden\" value=\""+victim+"\">  <input type=\"submit\" value=\"Request Friend\"></form> ";
FriendManager fMgr = (FriendManager)application.getAttribute("friendManager");
AccountManager acct = (AccountManager)application.getAttribute("manager");
AchievementManager achMGR = (AchievementManager)application.getAttribute("achievementManager");
QuizManager quizMGR = (QuizManager)application.getAttribute("quizManager");


if(!fMgr.areFriends(user, victim)){   //if not already friends...
	if(fMgr.requestSent(user, victim)){  //cases where requests were already made in either direction
		out.println("Status:  Friend request sent");		
	}
	else if(fMgr.requestSent(victim, user)){     
		out.println("Status:  This person has requested friendship. Please check your friend list to approve.");
	}
	else if(!user.equals(victim)){   //don't show request button if you are looking at your own info page
		out.println(requestButton);  
	}
	
}
else{  //already friends
	out.println("Status: Friends");
	String composeLink = "<form action=\"ComposeServlet\" method=\"post\"><input type=\"hidden\" name=\"toID\" value=\"" +victim+"\"><input type=\"submit\" value=\"Send Message\"></form>";
	out.println(composeLink);
}
%>
<div id="commonFriendsTableContent">
<h2>Friends in Common</h2>
<table id="commonFriendsTable">
<thead>
<tr>
<th>Name</th>
</tr>
</thead>
<tbody>
<% 
	ArrayList<String>userFriends = fMgr.getFriends(user);
	ArrayList<String>victimFriends = fMgr.getFriends(victim);
	ArrayList<String>common = new ArrayList<String>();
	for(int i=0; i<userFriends.size(); i++){           //populate array of friends in common
		if(victimFriends.contains(userFriends.get(i))){
			common.add(userFriends.get(i));
		}
	}
	
	//now show the common friends in a table
	for(int i=0; i<common.size();i++){
		String friendName = common.get(i);
		String linkButton = "<form action=\"UserSearchServlet\" method=\"post\"><input type=\"hidden\" name = \"victim\" value=\"" +friendName +"\"><input type=\"submit\" value=\""+friendName+"\"></form>";
		out.println("<tr>");
		out.println("<td> "+linkButton+"</td>");
		out.println("</tr>");
	}
	


%>
</tbody>
</table>
</div><br>

<% 

	out.println("<h2>Recent Quizzes Taken</h2>");
%>	

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
				if(acct.isPagePublic(victim) || fMgr.areFriends(user, victim) ){    //if the user page is public or view is a friend..show cool content
					AttemptManager attemptManager = (AttemptManager)application.getAttribute("attemptManager");
					Attempt[] attempts = attemptManager.getAllAttempts(victim);
					//Attempt[] attempts = null;
					if (attempts != null) {
					
						for (int i = 0; i < Math.min(5,attempts.length) ; i++) {
							out.println("<tr>");;
							int quizID = attempts[i].getQuizId();
							String quizName = quizMGR.getQuizName(quizID);
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
				}
				%>
		</tbody>
		</table>
		</div><br>
	
<% 
	out.println("<h2>Achievements</h2>");

	%>
	
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
	if(acct.isPagePublic(victim) || fMgr.areFriends(user, victim) ){    //if the user page is public or view is a friend..show cool content
		Achievement[] achList = achMGR.getAllAchievement(victim);
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
	}
%>
</tbody>
</table>
</div>
<br><br>
</div>
</body>
</html>