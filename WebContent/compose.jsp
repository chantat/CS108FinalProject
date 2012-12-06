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
<%!String toAddr; %>
<% 
if (request.getParameter("toID") == null) {
	toAddr = ""; 
} else {
	toAddr = (String)request.getParameter("toID");
}
%>
<h1>Compose New Message</h1>
<!--<script>
alert('You cannot send a message to a user you are not friends with' +
		'Check to make sure the username is spelled correctly');
</script>
-->
<form action="MailServlet" method="post">
<p>To: <input type="text" name="toID" value="<%= toAddr %>"/></p>
<p>Subject: <input type="text" name="subject"/></p>
<p><textarea name="message" cols="50" rows="5"></textarea></p>
<input type="submit" name="Send" value="Send"/>
<a href="inbox.jsp">
<button>Discard</button>
</a>
</form>
</body>
</html>