<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, question.*, answer.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Matching Question</title>
<%@include file="resources.jsp" %>
<script type="text/javascript">
	
	var row = 1;
	$(document).ready(function() {
		$("#addItem").click(function () {
			var newAnswerField = $(document.createElement('div')).attr("id", row+"_answers");
			newAnswerField.append('<input type="text" name="' + row + '_left">');
			newAnswerField.append('<input type="text" name="' + row + '_right">');
			newAnswerField.appendTo('#AnswerForm');		
			row++;
		});
		
		$("#removeItem").click(function() {
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

<h1>Matching Question</h1>
<form id="AnswerForm" action="CreateMServlet" method="post">
Enter question: <input type="text" value="<% out.print(oldQuestion); %>" name="questionText"> <br>
<input type="submit" value="Submit">
Enter the items: <br>
<div id="0_answers"><input type="text" name="0_left"><input type="text" name="0_right"></div>
</form>
<input type="button" value="Add Item" id="addItem">
<input type="button" value="Remove Item" id="removeItem">
</body>
</html>