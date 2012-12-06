<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="mail.*, javax.swing.*" %>
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
<title>Compose</title>
</head>
<body>
<%!
String toAddr, subject, replyText; 
%>
<% 
if (request.getParameter("toID") == null) {
	toAddr = ""; 
} else {
	toAddr = (String)request.getParameter("toID");
}
if (request.getParameter("msgText") == null) {
	replyText = "";
} else {
	replyText = (String)request.getParameter("msgText");
}
if (request.getParameter("subject") == null) {
	subject = "";
} else {
	subject = (String)request.getParameter("subject");
}
%>
<h1>Compose New Message</h1>
<!--<script>
alert('You cannot send a message to a user you are not friends with' +
		'Check to make sure the username is spelled correctly');
</script>
-->
<form action="MailServlet" method="post">
<p>To: <input type="text" name="toID" value="<%= toAddr %>"/>
(Separate names with commas)</p>
<p>Subject: <input type="text" name="subject" value="<%= subject %>"/></p>
<p><textarea name="message" cols="50" rows="5"><%= replyText %></textarea></p>
<input type="submit" name="Send" value="Send"/>
<a href="inbox.jsp">
<button>Discard</button>
</a>
</form>
</body>
</html>