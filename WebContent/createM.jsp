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
	
	$(document).ready(function() {
		$("#addItem").click(function () {
			var row = parseInt($('#numAnswers').val());
			var newAnswerField = $(document.createElement('div')).attr("id", row+"_answers");
			newAnswerField.append('<input type="text" name="' + row + '_left">');
			newAnswerField.append('<input type="text" name="' + row + '_right">');
			newAnswerField.appendTo('#AnswerForm');		
			$('#numAnswers').val(row + 1);
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
	ArrayList<String> oldAnswerList = new ArrayList<String>();
	
	if (questionIndex != -1) {
		oldQuestion = pendingQuestions.get(questionIndex).getQText();
		for (int i = 0; i < pendingAnswers.get(questionIndex).size(); i++) {
			oldAnswerList.add(pendingAnswers.get(questionIndex).get(i).getAnswerList().get(0));
		}
	}
	
%>

<h1>Matching Question</h1>
<form id="AnswerForm" action="CreateMServlet" method="post">
Enter question: <input type="text" value="<% out.print(oldQuestion); %>" name="questionText"> <br>
<input type="submit" value="Submit"> <br>
Enter the items: <br>
<% for(int i = 0; i < oldAnswerList.size(); i++) {
	String answerText = oldAnswerList.get(i);
	String tokens[] = answerText.split("#");
	String leftText = tokens[0];
	String rightText = tokens[1];
%>
	<div id="<%out.print(i);%>_answers"><input type="text" name="<%out.print(i);%>_left" value="<%out.print(leftText);%>"><input type="text" name="<%out.print(i);%>_right" value="<%out.print(rightText);%>"> </div>
<% } %>
<input id = 'numAnswers' type='hidden' value='<%out.print(oldAnswerList.size());%>'>
</form>
<input type="button" value="Add Item" id="addItem">
</body>
</html>