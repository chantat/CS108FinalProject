package question;

import java.util.*;



public class MatchingQuestion extends Question {
	
	public MatchingQuestion(int qID, String qText, int numMatches) {
		super(qID, qText);
		type=7;
		//this.qText=qText;
		this.numAnswers=numMatches;
	}

}
