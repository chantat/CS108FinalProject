<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page import="java.util.*, mail.*" %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Inbox</title>
</head>
<body>
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
<table>
<tr>
<th>Read</th>
<th>From</th>
<th>Subject</th>
<th>Time</th>
</tr>
<%
/* Display Inbox as a link to each message. */
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
<a href="compose.jsp">Compose</a>

<h1>Friend Requests</h1>
<% 
userPackage.FriendManager friendMgr = (userPackage.FriendManager)application.getAttribute("friendManager"); 
ArrayList<String> requests = friendMgr.getRequests(user);
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
<%}%>
</table>

<A HREF="http://localhost:8080/CS108FinalProject/userHomePage.jsp">Return to Home Page</A>
</body>
</html>