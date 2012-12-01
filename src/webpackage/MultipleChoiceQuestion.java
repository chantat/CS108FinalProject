package webpackage;

import java.util.ArrayList;

public class MultipleChoiceQuestion extends Question {

		private String qText;
		private int numChoices;
		private ArrayList<String> answerChoices;
		
		public MultipleChoiceQuestion(int qID, String qText, ArrayList<String> answerChoices) {
			super(qID);
			this.qText = qText;
			this.numChoices = answerChoices.size(); //
			this.answerChoices=answerChoices;
			type = 3;
		}
		
		public int getNumChoices() {
			return numChoices;
		}
		
		public String getQuestionText() {
			return qText;
		}
		
		public ArrayList<String> getAnswerChoices() {
			return answerChoices;
		}


}
