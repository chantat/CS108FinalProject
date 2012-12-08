<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, question.*, answer.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Multiple Answer Question</title>
<%@include file="resources.jsp" %>
<script type="text/javascript">
	
	var id = 1;
	$(document).ready(function() {
		$("#addAnswer").click(function () {
			var row = parseInt($('#numRows').val());
			var newAnswerField = $(document.createElement('div')).attr("id", row+"_answers");
			newAnswerField.append('<input type="hidden" id="' + row + 'numEqAn" value="1">');
			newAnswerField.append('<label for="label_' + row + '" id="label_' + row + '">Answer #' + (row+1) + ' : </label><br>' +
					'<input type="text" id="label_' + row + '" name="'+row+'_answer_0"'+'><br>');
			newAnswerField.appendTo('#AnswerForm');
			
			var newAnswerButton = $(document.createElement('input'));
			newAnswerButton.attr("type", "button");
			newAnswerButton.attr("name", row);
			newAnswerButton.attr("value", "Add Equivalent Answer");
			newAnswerButton.appendTo(newAnswerField);
			
			newAnswerField.after('<p></p>');
			
			newAnswerButton.click(function () {
				var numQs = parseInt($("#" + $(this).attr("name") + "numEqAn").val());
				var curLabel = $("#label_" + $(this).attr("name"));
				curLabel.append('<br><input type="text" value="New Answer" id="' + id + '" name="' + $(this).attr("name") + '_answer_' + numQs + '"><br>');
				$("#" + $(this).attr("name") + "numEqAn").val((numQs+1)+"");
				id++;
			});
			
			$('#numRows').val(row + 1);
		});
		
		$("#removeAnswer").click(function() {
			var row = parseInt($('#numRows').val());
			$("#" + (row-1) + "_answers").remove();
			if (row > 0){
				$('#numRows').val(row - 1);
			}
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
	ArrayList<Answer> oldAnswerList = new ArrayList<Answer>();
	String orderString = "";
	String oldNumAnswerString = "";
	
	if (questionIndex != -1) {
		oldQuestion = pendingQuestions.get(questionIndex).getQText();
		oldAnswerList = pendingAnswers.get(questionIndex);
		oldNumAnswerString = "" + pendingQuestions.get(questionIndex).getNumAnswers();
		if (oldAnswerList.get(0).getAnswerOrder() != -1) {
			orderString = "checked";
		}
	}
	
%>
<center>
<h1>Multiple Answer Question</h1>
<form id="AnswerForm" action="CreateMAServlet" method="post">
Enter your question: <input type="text" value="<% out.print(oldQuestion); %>" name="questionText"> <br>
<input type="checkbox" name="isOrdered" value="isOrdered" <% out.print(orderString); %>>Order Matters<br><br>
Enter required number of answers: <input type="text" name="numAnswers" value="<% out.print(oldNumAnswerString); %>"> <br>
<input type="submit" value="Submit"><br><br>
<% for(int i = 0; i < oldAnswerList.size(); i++) { 
	ArrayList<String> answerTexts = oldAnswerList.get(i).getAnswerList(); %>
	<div id="<% out.print(i); %>_answers"> 
		<input type="hidden" id="<% out.print(i); %>numEqAn" value="<% out.print(answerTexts.size()); %>">
		<label for="label_<% out.print(i); %>" id="label_<% out.print(i); %>">Answer #<% out.print(i + 1); %> : 
		<% for (int j = 1; j < answerTexts.size(); j++) { %>
			<br><input type="text" value="<% out.print(answerTexts.get(j)); %>" name="<% out.print(i); %>_answer_<% out.print(j); %>"><br>
		<% } %>
		</label><br>
		<input type="text" id="label_0" value="<% out.print(answerTexts.get(0)); %>" name="<% out.print(i); %>_answer_0"><br>
		<input type="button" id="button<% out.print(i); %>" name="<% out.print(i); %>" value="Add Equivalent Answer">
		<script type="text/javascript">
			$("#button<%=i%>").click(function () {
				var numQs = parseInt($("#" + $(this).attr("name") + "numEqAn").val());
				var curLabel = $("#label_" + $(this).attr("name"));
				curLabel.append('<br><input type="text" value="New Answer" name="' + $(this).attr("name") + '_answer_' + numQs + '"><br>');
				$("#" + $(this).attr("name") + "numEqAn").val((numQs+1)+"");
			});
		</script>
	</div>
<% } %>
</form>
<input type="hidden" id="numRows" value="<% out.print(oldAnswerList.size()); %>">
<input type="button" value="Add Unique Answer" id="addAnswer">
<input type="button" value="Remove Answer" id="removeAnswer">
</center>
</body>
</html>