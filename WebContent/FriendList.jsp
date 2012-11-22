<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import="java.util.*, userPackage.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Friend List</title>
</head>
<body>
<h1>Friends List</h1>


<table border="1">

<%
String user = (String)session.getAttribute("username");
FriendManager friendMgr = (FriendManager)application.getAttribute("friendManager");
ArrayList<String> friends = friendMgr.getFriends(user);
ArrayList<String> requests = friendMgr.getRequests(user);
for(int i=0; i<friends.size();i++){
	String friendName = friends.get(i);
	out.println("<tr>");
	out.println("<td> "+friendName+"</td>");
	String removeButton = "<form action=\"RemoveFriendServlet\" method=\"post\"><input type=\"hidden\" name = \"victim\" value=\"" +friendName +"\"><input type=\"submit\" value=\"Remove From Friend List\"></form>";
	out.println("<td> "+removeButton+"</td>");
	out.println("</tr>");
}

%>

</table>

<h1>Friends Requests</h1>


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




</body>
</html>