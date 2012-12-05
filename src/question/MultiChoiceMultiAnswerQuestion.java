package question;

import java.util.*;



public class MultiChoiceMultiAnswerQuestion extends Question {

	
	public MultiChoiceMultiAnswerQuestion(int qID, String qText) {
		super(qID, qText);
		//this.qText=qText;
		this.type = 6;
		this.numAnswers = 1;
	}

}
