<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, question.*, answer.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Multiple Answer Question</title>

<script type="text/javascript">
	var counter = 0;
	function addNew() {
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
	}
</script>

</head>
<body >
	<form id="AnswerForm"></form>
	<input type="button" value="Add" onClick="addNew()">
</body>
</html>