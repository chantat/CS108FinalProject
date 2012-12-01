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
AccountManager acct = (AccountManager)application.getAttribute("manager");
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
}


if(acct.isPagePublic(victim) || fMgr.areFriends(user, victim) ){    //if the user page is public or view is a friend..show cool content
	
	
	//FILL WITH TABLES N STUFF
	out.println("<h2>Achievements</h2>");
	


	out.println("<h2>Friends in Common</h2>");
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
	
}


%>

<A HREF="http://localhost:8080/CS108FinalProject/userHomePage.jsp">Return to Home Page</A>

</body>
</html>