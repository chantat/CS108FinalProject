package webpackage;

import java.util.ArrayList;

public class MultipleChoiceQuestion extends Question {

		private String qText;
		
		public MultipleChoiceQuestion(int qID, String qText) {
			super(qID);
			this.qText = qText;
			type = 3;
			this.numAnswers=1;
		}
		
		public String getQuestionText() {
			return qText;
		}


}
