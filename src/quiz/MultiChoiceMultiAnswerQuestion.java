package quiz;

import java.util.*;


public class MultiChoiceMultiAnswerQuestion extends Question {

	
	public MultiChoiceMultiAnswerQuestion(int qID, String qText, int numAnswers) {
		super(qID);
		this.qText=qText;
		type=6;
		this.numAnswers=numAnswers;
	}

}
