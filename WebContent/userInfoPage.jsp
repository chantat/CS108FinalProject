<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Public Info Page</title>
</head>
<body>



<form action="AddFriendServlet" method="post">
<input name = victim type="hidden" value="<%= request.getAttribute("victim")%>">
<input type="submit" value="Add Friend">
</form>


</body>
</html>