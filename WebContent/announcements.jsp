<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="announcement.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz Master</title>
<%@include file="resources.jsp" %>
</head>
<body>
<%@include file="header.jsp" %>
<center><h1>Welcome to Quiz Master!</h1></center>
<div id="allAnnouncements">
	<center><p style="font-size:25pt">Announcements</p></center>
	<div style="margin-left:300px; width:400px;">
	 <% 
		AnnouncementManager announceMGR = (AnnouncementManager)application.getAttribute("announcementManager");
		Announcement[] announce = announceMGR.getAllAnnouncement();
		if (announce != null) {
		
			for(int i=announce.length-1; i>=0; i--){
				String adminName = announce[i].getAdminId();
				String text = announce[i].getAnnouncementText();
				String subject = announce[i].getSubject();
				String time = announce[i].getPostTime().toString();
				out.println("<p style='font-size:20pt'>");
				out.println(subject);
				out.println("<br><span style='font-size:11pt'>Posted by " + adminName + " at " + time + "</span><br>");
				out.println("</p>");
				
				out.println("<p style='font-size:14pt'>");
				out.println(text);
				out.println("</p><br>");
			}
		
		}
	%>
	</div>
	<p style="font-size:20pt"></p>
</div>
</body>
</html>