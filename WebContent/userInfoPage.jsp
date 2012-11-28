<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="java.util.*, userPackage.*" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Public Info Page</title>
</head>
<body>
<h1><%=(String)request.getParameter("victim") %>'s Info Page</h1>
<% 
String user = (String)session.getAttribute("username");
String victim =(String)request.getParameter("victim");
String requestButton = "<form action=\"AddRequestServlet\" method=\"post\"><input name = victim type=\"hidden\" value=\""+victim+"\">  <input type=\"submit\" value=\"Request Friend\"></form> ";
FriendManager fMgr = (FriendManager)application.getAttribute("friendManager");
if(!fMgr.areFriends(user, victim)){
	if(fMgr.requestSent(user, victim)){
		out.println("Status:  Friend request sent");
		
	}
	else if(fMgr.requestSent(victim, user)){
		out.println("Status:  Friend request received.  Please check you inbox and approve if desired.");
	}
	else{
		out.println(requestButton);  //only show a request button if they are not already friends
	}
	
}

%>



</body>
</html>