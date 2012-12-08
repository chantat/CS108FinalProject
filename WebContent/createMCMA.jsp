<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, question.*, answer.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Multi-Choice Multi-Answer Question</title>
<%@include file="resources.jsp" %>
<script type="text/javascript">
	
	var row = 0;
	var id = 1;
	$(document).ready(function() {
		$("#addAnswer").click(function () {
			var row = parseInt($('#numAnswers').val());
			var newAnswerField = $(document.createElement('div')).attr("id", row+"_answers");
			newAnswerField.append('<input type="checkbox" name="'+row+'_isCorrect_0">' + 
					'<input type="text" name="'+row+'_answer_0"><br>');
			newAnswerField.appendTo('#AnswerForm');
			$('#numAnswers').val(row + 1);
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
	HashSet<Integer> correctAnswerSet = new HashSet<Integer>();
	ArrayList<String> oldAnswerList = new ArrayList<String>();
	
	if (questionIndex != -1) {
		oldQuestion = pendingQuestions.get(questionIndex).getQText();
		for (int i = 0; i < pendingAnswers.get(questionIndex).size(); i++) {
			Answer answer = pendingAnswers.get(questionIndex).get(i);
			String answerText = answer.getAnswerList().get(0);
			oldAnswerList.add(answerText);
			if (answer.getScore() == 1) {
				correctAnswerSet.add(i);
			}
		}
	}
	
%>
<center>
<h1>Multiple-Choice Multiple-Answer Question</h1>
<form id="AnswerForm" action="CreateMCMAServlet" method="post">
Enter your question: <input type="text" value="<% out.print(oldQuestion); %>" name="questionText"> <br>
<input type="submit" value="Submit"> <br><br>
Enter your choices and select the correct answers: <br>
<% for(int i = 0; i < oldAnswerList.size(); i++) {
	String answerText = oldAnswerList.get(i);
	String checkString = "";
	if (correctAnswerSet.contains(i)) {
		checkString = "checked";
	}
%>
	<div id="<%out.print(i);%>_answers">
		<input type="checkbox" name="<% out.print(i); %>_isCorrect_0" <% out.print(checkString); %>> 
		<input type="text" name="<%out.print(i);%>_answer_0" value="<% out.print(oldAnswerList.get(i)); %>">
	</div>
<% } %>
<input id = 'numAnswers' type='hidden' value='<%out.print(oldAnswerList.size());%>'>
</form>
<input type="button" value="Add Answer" id="addAnswer">
</center>
</body>
</html>