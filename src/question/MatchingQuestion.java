package question;

import java.util.*;



public class MatchingQuestion extends Question {
	
	public MatchingQuestion(int qID, String qText, int numAnswers) {
		super(qID, qText);
		this.numAnswers = numAnswers;
		this.isOrdered = true;
		this.type = QuestionManager.MATCHING;
	}

}
