package answer;

import java.util.ArrayList;

import question.QuestionManager;

public class FillInTheBlankAnswer extends Answer{
	public FillInTheBlankAnswer(int questionId, ArrayList<String> answerList) {
		super(questionId, answerList, QuestionManager.FILL_IN_THE_BLANK, -1, 1);
	}
}
