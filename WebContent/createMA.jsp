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
	
	var row = 0;
	
	$(document).ready(function() {
		$("#addAnswer").click(function () {
			var newAnswerField = $(document.createElement('div')).attr("id", row+"_answers");
			newAnswerField.append('<label id="label_' + row + '">Answer #' + (row+1) + ' : </label><br>' +
					'<input type="text" name="'+row+'_answer_0"'+'><br>');
			newAnswerField.val(1);
			newAnswerField.appendTo('#AnswerForm');
			
			var newAnswerButton = $(document.createElement('input')).attr("id", "addEquiv");
			newAnswerButton.attr("type", "button");
			newAnswerButton.attr("name", row);
			newAnswerButton.attr("value", "Add Equivalent Answer");
			newAnswerButton.appendTo(newAnswerField);
			
			newAnswerField.after('<p></p>');
			
			newAnswerButton.click(function () {
				var numQs = newAnswerField.val();
				var curLabel = $("#label_" + $(this).attr("name"));
				curLabel.append('<br><input type="text" name="' + $(this).attr("name") + '_answer_' + numQs + '"><br>');
				newAnswerField.val(numQs+1);
			});
			
			row++;
		});
		
		$("#removeAnswer").click(function() {
			$("#" + (row-1) + "_answers").remove();
			if (row > 0) row--;
		});
	});
	
	/*function addNew() {
		var mainContainer = document.getElementById('AnswerForm');
		
		// Create a new div for holding text and button input elements
		var newDiv = document.createElement('div');
		// Create a new text input
		var newText = document.createElement('input');
		newText.type = "input"; 
		newText.value = counter;
		// Create a new button input
		var newDelButton = document.createElement('input');
		newDelButton.type = "button";
		newDelButton.value = "Delete";
	
		// Append new text input to the newDiv
		newDiv.appendChild(newText);
		// Append new button input to the newDiv
		newDiv.appendChild(newDelButton);
		// Append newDiv input to the mainContainer
		mainContainer.appendChild(newDiv);
		counter++;
				
		// Add a handler to button for deleting the newDiv from the mainContainer
		newDelButton.onclick = function() {
			mainContainer.removeChild(newDiv);
		}
	}*/
</script>

</head>
<body>
	<form id="AnswerForm" action="CreateMAServlet" method="post">
	Enter your question: <input type="text" name="questionText"> <br>
	<input type="submit" value="Submit">
	</form>
	<input type="button" value="Add Unique Answer" id="addAnswer">
	<input type="button" value="Remove Answer" id="removeAnswer">
</body>
</html>