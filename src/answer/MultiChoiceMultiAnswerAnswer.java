package answer;

import java.util.ArrayList;

import question.QuestionManager;

public class MultiChoiceMultiAnswerAnswer extends Answer {

	public MultiChoiceMultiAnswerAnswer(int questionId,
			ArrayList<String> answerList, int answerOrder,
			double score) {
		super(questionId, answerList, QuestionManager.MULTI_CHOICE_MULTI_ANSWER, answerOrder, score);
	}
	
	public static double scoreUserInput(ArrayList<Answer> correctAnswers, ArrayList<String> userInput) {
		return 0;
	}

}
