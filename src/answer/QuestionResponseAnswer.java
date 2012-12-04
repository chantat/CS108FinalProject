package answer;

import java.util.ArrayList;

import question.*;

public class QuestionResponseAnswer extends Answer{
	public QuestionResponseAnswer(int questionId, ArrayList<String> answerList) {
		super(questionId, answerList, QuestionManager.QUESTION_RESPONSE, -1, 1);
	}
}
