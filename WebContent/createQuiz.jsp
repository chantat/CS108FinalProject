<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, quiz.*, question.*, answer.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Quiz</title>
<%@include file="resources.jsp" %>
<script type="text/javascript">

	$(document).ready(function() {
		$("#addTag").click(function () {
			var id = parseInt($('#numTags').val());
			var newTagField = $(document.createElement('div')).attr("id", id+"_tag");
			newTagField.append('<input type="text" name="' + id + '_tag_0">');
			newTagField.appendTo('#tags');		
			$('#numTags').val(id + 1);
		});
	});
</script>
</head>
<body>
<%@include file="header.jsp" %>
<% 
ArrayList<Question> pendingQuestions = (ArrayList<Question>)session.getAttribute("pendingQuestions");
ArrayList<ArrayList<Answer>> pendingAnswers = (ArrayList<ArrayList<Answer>>)session.getAttribute("pendingAnswers");
String pendingQuizName = (String)session.getAttribute("pendingQuizName");
String pendingQuizDescription = (String)session.getAttribute("pendingQuizDescription");
String pendingCategory = (String)session.getAttribute("pendingCategory");
ArrayList<String> pendingTags = (ArrayList<String>)session.getAttribute("pendingTags");
Boolean pendingIsRandomized = (Boolean)session.getAttribute("pendingIsRandomized");;
Boolean pendingIsFlashcard = (Boolean)session.getAttribute("pendingIsFlashcard");
Boolean pendingAllowsPractice = (Boolean)session.getAttribute("pendingAllowsPractice");
Boolean pendingImmediateFeedback = (Boolean)session.getAttribute("pendingImmediateFeedback");
%>

<form action="CreateQuizServlet" method="post">
Quiz Name: <input type="text" name="quizName" value="<% out.print(pendingQuizName); %>"> <br>
Description: <input type="text" name="description" value="<% out.print(pendingQuizDescription); %>">  <br>
Category: <input type="text" name="category" value="<% out.print(pendingCategory); %>"> <br>
Tags: <div id="tags">

<% for(int i = 0; i < pendingTags.size(); i++) { %>
	<div id="<%out.print(i);%>_tag"><input type="text" name="<%out.print(i);%>_tag_0" value="<%out.print(pendingTags.get(i));%>"> </div>
<% } %>
<input id = 'numTags' type='hidden' value='<%out.print(pendingTags.size());%>'>

</div> <input type="button" value="Add Tag" id="addTag"> <br>
<%
String checkString = "";
if (pendingIsRandomized) {
	checkString = "checked";
}
%>
<input type="checkbox" name="isRandomized" value="isRandomized" <% out.print(checkString); %>>Randomize question order<br>
<%
checkString = "";
if (pendingIsFlashcard) {
	checkString = "checked";
}
%>
<input type="checkbox" name="isFlashcard" value="isFlashcard" <% out.print(checkString); %>>Show one question per page<br>
<%
checkString = "";
if (pendingImmediateFeedback) {
	checkString = "checked";
}
%>
<input type="checkbox" name="immediateFeedback" value="immediateFeedback" <% out.print(checkString); %>>Give feedback after each page (Only in Flashcard Mode)<br>
<%
checkString = "";
if (pendingAllowsPractice) {
	checkString = "checked";
}
%>
<input type="checkbox" name="allowsPractice" value="allowsPractice" <% out.print(checkString); %>>Allow practice mode<br>
<input type="submit" value="Create Quiz">
</form>
<br><br>

<table border="1">
<% 

for (int i = 0; i < pendingQuestions.size(); i++) {
	Question question = pendingQuestions.get(i);
	String editButtonString = "<form action=\"EditQuestionServlet\" method=\"post\">";
	editButtonString += "<input type=\"hidden\" name = \"questionIndex\" value=\""+ i + "\">";
	editButtonString += "<input type=\"submit\" value=\"Edit\">";
	editButtonString += "</form>";
	out.println("<tr>");
	out.println("<td> " + question.getQText() + "</td>");
	out.println("<td> " + QuestionManager.getTypeName(question.getType()) + "</td>");
	out.println("<td> " + editButtonString + "</td>");
	out.println("</tr>");
}


String dropDownString = "<select name=\"questionType\">";
for (int i = 0; i < QuestionManager.getNumType(); i++) {
	int type = i + 1;
	dropDownString += "<option value=\"" + type + "\">" + QuestionManager.getTypeName(type) + "</option>";
}
dropDownString += "</select>";

String addButtonString = "<input type=\"hidden\" name = \"questionIndex\" value=\"-1\">";
addButtonString += "<input type=\"submit\" value=\"Add Question\">";

out.println("<form action=\"EditQuestionServlet\" method=\"post\">");
out.println("<tr>");
out.println("<td> " + " " + "</td>");
out.println("<td> " + dropDownString + "</td>");
out.println("<td> " + addButtonString + "</td>");
out.println("</tr>");
out.println("</form>");
%>	
</table>
</body>
</html>