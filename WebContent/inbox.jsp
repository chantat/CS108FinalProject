<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page import="java.util.*" %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Inbox</title>
</head>
<body>
<%!
mail.MailSystem ms;
mail.MailSystem.Mailbox mb;
List<mail.Message> inbox;
mail.Message msg;
String user;
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
user = "ryan"; //TODO: get user from session context
ms = (mail.MailSystem) application.getAttribute("mailSystem");
mb = ms.new Mailbox(user);
inbox = mb.loadInbox();
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
<table>
<tr>
<th>Read</th>
<th>From</th>
<th>Time</th>
</tr>
<tr>
<td>Fill with rows</td>
</tr>
</table>

<h1>Challenges</h1>
<table>
<tr>
<th>Read</th>
<th>From</th>
<th>Quiz</th>
<th>Time</th>
</tr>
<tr>
<td>Fill with rows</td>
</tr>
</table>
</body>
</html>