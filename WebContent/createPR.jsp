<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, question.*, answer.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Picture-Response Question</title>
<%@include file="resources.jsp" %>
<script type="text/javascript">
	
	var row = 1;
	$(document).ready(function() {
		$("#addEquivalentAnswer").click(function () {
			var newAnswerField = $(document.createElement('div')).attr("id", row+"_answers");
			newAnswerField.append('<input type="text" id="label_' + row + '" name="' + row + '_answer_0"><br>');
			newAnswerField.appendTo('#AnswerForm');		
			row++;
		});
		
		$("#removeAnswer").click(function() {
			$("#" + (row-1) + "_answers").remove();
			if (row > 0) row--;
		});
	});
</script>
</head>
<body>
<% 
	ArrayList<Question> pendingQuestions = (ArrayList<Question>)session.getAttribute("pendingQuestions");
	ArrayList<ArrayList<Answer>> pendingAnswers = (ArrayList<ArrayList<Answer>>)session.getAttribute("pendingAnswers");
	int questionIndex = (Integer)session.getAttribute("editPendingQuestionIndex");
	
	String oldQuestion = "";
	
	if (questionIndex != -1) {
		oldQuestion = pendingQuestions.get(questionIndex).getQText();
	}
	
%>

<h1>Picture-Response Question</h1>
<form id="AnswerForm" action="CreatePRServlet" method="post">
Enter the URL of your picture: <input type="text" name="picURL"/> <br>
Enter your question: <input type="text" value="<% out.print(oldQuestion); %>" name="questionText"> <br>
<input type="submit" value="Submit">
Enter your answer: <br>
<div id="0_answers"><input type="text" value="New Answer" id="label_0" name="0_answer_0"></div>
</form>
<input type="button" value="Add Equivalent Answer" id="addEquivalentAnswer">
<input type="button" value="Remove Answer" id="removeAnswer">
</body>
</html>