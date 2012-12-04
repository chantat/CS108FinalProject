package answer;

import java.util.ArrayList;

import question.QuestionManager;

public class MultipleChoiceAnswer extends Answer {

	public MultipleChoiceAnswer(int questionId, ArrayList<String> answerList,
			int answerOrder, double score) {
		super(questionId, answerList, QuestionManager.MULTIPLE_CHOICE, answerOrder, score);
	}

}
