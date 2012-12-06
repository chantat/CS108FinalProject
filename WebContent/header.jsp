<div id="header" class="bluegloss-gradient" >
</div>

<div id="content">
	<text id="title"><a href="announcements.jsp"><span style="color:white">Quiz Master</span></a></text>
	
	
	<div id="rightContent">
		<text id="headerUser">
			<a href='quizHomepage.jsp'>All Quizzes</a>
			<span style="color:#fff">|</span>
			<%
				if (session.getAttribute("mode") != null && session.getAttribute("mode").equals("normal")) out.println("<a href='userHomePage.jsp'>"+
						session.getAttribute("username") + "</a>");
				else out.println("<a href='userLogin.jsp'>Login</a>");
			%>
				<span style="color:#fff">|</span>
			<%
				if (session.getAttribute("mode") != null && session.getAttribute("mode").equals("normal")) {
					out.println("<a href='logout.jsp'>Logout</a>");
				}
				else out.println("<a href='accountCreate.jsp'>Register</a>");
			%>
		</text>
	</div>
</div>