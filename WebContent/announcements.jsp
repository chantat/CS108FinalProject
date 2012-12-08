<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="announcement.*, java.util.*" %>
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
	 	CommentManager commentManager = (CommentManager)application.getAttribute("commentManager");
		Announcement[] announce = announceMGR.getAllAnnouncement();
		String currentUsername = (String)session.getAttribute("username");
		if (announce != null) {
		
			for(int i=announce.length-1; i>=0; i--){
				String adminName = announce[i].getAdminId();
				String text = announce[i].getAnnouncementText();
				String subject = announce[i].getSubject();
				String time = announce[i].getPostTime().toString();
				int announcementID = announce[i].getAnnouncementID();
				out.println("<p style='font-size:20pt'>");
				out.println(subject);
				out.println("<br><span style='font-size:11pt'>Posted by " + adminName + " at " + time + "</span><br>");
				out.println("</p>");
				
				out.println("<p style='font-size:14pt'>");
				out.println(text);
				out.println("</p><br>");
				
				ArrayList<Comment> announcementComments=commentManager.getAnnouncementComments(announcementID);
				for(int j = 0; j < announcementComments.size(); j++){
					String commentText=announcementComments.get(j).getCommentText();
					String commentUser=announcementComments.get(j).getUsername();
					String timeCommented=announcementComments.get(j).getTimeCommented().toString();
					System.out.println(j + " " + commentText + " " + commentUser);
					out.println("<p style='font-size:10pt'>");
					out.println(commentText);
					out.println("<br><span style='font-size:8pt'>Comment by " + commentUser + " at " + timeCommented + "</span><br>");
					out.println("</p>");
					out.println("<p style='font-size:10pt'>");
					out.println("</p><br>");
				}
				
				out.println("<form action=\"CommentServlet\" method=\"post\">");
				
				
				String hiddenInput = "<input type=\"hidden\" name=\"announcementID\" value=" + announcementID + ">";
				out.println(hiddenInput);
				String commentBox = "<br><textarea cols=\"30\" rows=\"5\" name=\"commentText\"";
				commentBox += "value=\"" + "\">";
				commentBox += "</textarea>";
				out.println(commentBox);
				out.println("<br><input type=\"submit\" value=\"Comment on the above announcement\"><br>");
				out.println("</form>");
			}
		
		}
	%>
	</div>
	<p style="font-size:20pt"></p>
</div>
</body>
</html>