<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Compose</title>
</head>
<body>
<h1>Compose New Message</h1>
<form action="MailServlet" method="post">
<p>To: <input type="text" name="toID"/></p>
<p>Subject: <input type="text" name="subject"/></p>
<p><textarea name="message" cols="50" rows="5"></textarea></p>
<input type="submit" name="Send" value="Send"/>
<a href="inbox.jsp">
<button>Discard</button>
</a>
</form>
</body>
</html>