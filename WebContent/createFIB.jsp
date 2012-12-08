<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, answer.*, question.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Fill in the Blank Question</title>
<%@include file="resources.jsp" %>
<script type="text/javascript">
	
	$(document).ready(function() {
		$("#addEquivalentAnswer").click(function () {
			var row = parseInt($('#numEquivalentAnswers').val());
			var newAnswerField = $(document.createElement('div')).attr("id", row+"_answers");
			newAnswerField.append('<input type="text" id="label_' + row + '" name="' + row + '_answer_0"><br>');
			newAnswerField.appendTo('#AnswerForm');	
			$('#numEquivalentAnswers').val(row + 1);
		});
	});
</script>
</head>
<body>
<%@include file="header.jsp" %>
<% 
	ArrayList<Question> pendingQuestions = (ArrayList<Question>)session.getAttribute("pendingQuestions");
	ArrayList<ArrayList<Answer>> pendingAnswers = (ArrayList<ArrayList<Answer>>)session.getAttribute("pendingAnswers");
	int questionIndex = (Integer)session.getAttribute("editPendingQuestionIndex");
	
	String oldQuestion = "";
	ArrayList<String> oldAnswerList = new ArrayList<String>();
	
	if (questionIndex != -1) {
		oldQuestion = pendingQuestions.get(questionIndex).getQText();
		oldAnswerList = pendingAnswers.get(questionIndex).get(0).getAnswerList();
	}
	
%>
<center>
<h1>Fill in the Blank Question</h1>
<form id="AnswerForm" action="CreateFIBServlet" method="post">
Enter your statement, with ### where the blank goes: <input type="text" value="<% out.print(oldQuestion); %>" name="questionText"> <br>
<input type="submit" value="Submit"> <br><br>
Enter your answer(s): <br>
<% for(int i = 0; i < oldAnswerList.size(); i++) { %>
<div id="<%out.print(i);%>_answers"><input type="text" name="<%out.print(i);%>_answer_0" value="<%out.print(oldAnswerList.get(i));%>"> </div>
<% } %>
<input id = 'numEquivalentAnswers' type='hidden' value='<%out.print(oldAnswerList.size());%>'>
</form>
<input type="button" value="Add Equivalent Answer" id="addEquivalentAnswer">
</center>
</body>
</html>