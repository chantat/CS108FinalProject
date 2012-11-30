<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*, userPackage.* ,announcement.*, achievement.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Home Page</title>
</head>
<body>
<h1><%= session.getAttribute("username") %>'s Home Page</h1>

<a href="inbox.jsp">Inbox</a>


<form action="UserSearchServlet" method="post">
User Search: <input type="text" name="victim"> 
<input type="submit" value="Search for User">
</form>


<% 
/*
<h2>Friend List</h2>
<A HREF="http://localhost:8080/CS108FinalProject/FriendList.jsp">See Friend List</A>
*/
%>

<h2>Friends List</h2>


<table border="1">

<%
String user = (String)session.getAttribute("username");
FriendManager friendMgr = (FriendManager)application.getAttribute("friendManager");
AccountManager acct = (AccountManager)application.getAttribute("manager");


//TEST
System.out.println("Friend Table");
friendMgr.dumpFriendTable();
System.out.println("Req Table");
friendMgr.dumpRequestTable();


ArrayList<String> friends = friendMgr.getFriends(user);
ArrayList<String> requests = friendMgr.getRequests(user);
for(int i=0; i<friends.size();i++){
	String friendName = friends.get(i);
	String linkButton = "<form action=\"UserSearchServlet\" method=\"post\"><input type=\"hidden\" name = \"victim\" value=\"" +friendName +"\"><input type=\"submit\" value=\""+friendName+"\"></form>";
	out.println("<tr>");
	out.println("<td> "+linkButton+"</td>");
	String removeButton = "<form action=\"RemoveFriendServlet\" method=\"post\"><input type=\"hidden\" name = \"victim\" value=\"" +friendName +"\"><input type=\"submit\" value=\"Remove From Friend List\"></form>";
	out.println("<td> "+removeButton+"</td>");
	out.println("</tr>");
}

%>

</table>

<h2>Friend Requests Received</h2>


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




<h2>Announcements</h2>
<table border="1">
<% 
AnnouncementManager announceMGR = (AnnouncementManager)application.getAttribute("announcementManager");
Announcement[] announce = announceMGR.getAllAnnouncement();

for(int i=0; i<announce.length;i++){
	out.println("<tr>");
	String adminName = announce[i].getAdminId();
	String text = announce[i].getAnnouncementText();
	String subject = announce[i].getSubject();
	String time = announce[i].getPostTime().toString();
	out.println("<td> "+adminName+"</td>");
	out.println("<td> "+subject+"</td>");
	out.println("<td> "+time+"</td>");
	out.println("<td> "+text+"</td>");
	out.println("</tr>");
}



%>

</table>



<h2>Quiz List</h2>

<h2>Newest Quizzes</h2>


<h2>Popular Quizzes</h2>


<h2>Recently Taken Quizzes</h2>

<h2>Friends' Recent Activity</h2>

<h2>Achievements</h2>

<table border="1">
<% 


AchievementManager achMGR = (AchievementManager)application.getAttribute("achievementManager");
Achievement[] achList = achMGR.getAllAchievement(user);

for(int i=0; i<achList.length;i++){
	if(achList[i].getIsAchieved()){
		out.println("<tr>");
		String achName = achList[i].getName();
		String describe = achList[i].getDescription();
		
		out.println("<td> "+achName+"</td>");
		out.println("<td> "+describe+"</td>");
		out.println("</tr>");
	}
}




%>
</table>

<h2>Privacy Preferences</h2>

<form action="ChangePrivacyServlet" method="post">
<input type="checkbox" name="privacy1" value="Public" <%if(acct.isPerfPublic(user)) out.print("checked");  %>>I want my quiz scores public<br>
<input type="checkbox" name="privacy2" value="Public" <%if(acct.isPagePublic(user)) out.print("checked");  %>>I want my user page to be visible to all<br>
<input type="submit" value="Submit">

</form>


<%    //enables Admin page if user is Admin

if(acct.isAdmin(user)){
	String returnLink = "<A HREF=\"http://localhost:8080/CS108FinalProject/admin.jsp\">Administration Page</A>";
	out.println(returnLink);
}
%>
<form action="LogOutServlet" method="post">
<input type="submit" value="Log Out">
</form>


</body>
</html>