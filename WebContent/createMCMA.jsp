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
			var newAnswerField = $(document.createElement('div')).attr("id", row+"_answers");
			newAnswerField.append('<label for="label_' + row + '" id="label_' + row + '">Answer #' + (row+1) + ' : </label><br>' +
					'<input type="text" id="label_' + row + '" name="'+row+'_answer_0"'+'><br>');
			newAnswerField.val(1);
			newAnswerField.appendTo('#AnswerForm');
			
			var newScoreField = $(document.createElement('div')).attr("id", row+"_isCorrect");
			newScoreField.append('<label for="label_' + row + '" id="label_' + row + '">Check if is correct answer: </label><br>' +
					'<input type="checkbox" id="label_' + row + '" name="'+row+'_isCorrect_0"'+'><br>');
			newScoreField.val(1);
			newScoreField.appendTo('#AnswerForm');
			
			row++;
		});
		
		$("#removeAnswer").click(function() {
			$("#" + (row-1) + "_answers").remove();
			$("#" + (row-1) + "_isCorrect").remove();
			if (row > 0) row--;
		});
	});
</script>

</head>
<body>
	<form id="AnswerForm" action="CreateMCMAServlet" method="post">
	Enter your question: <input type="text" name="questionText"> <br>
	<input type="submit" value="Submit">
	</form>
	<input type="button" value="Add Answer" id="addAnswer">
	<input type="button" value="Remove Answer" id="removeAnswer">
</body>
</html>