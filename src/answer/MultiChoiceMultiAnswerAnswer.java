package answer;

import java.util.ArrayList;
import java.util.HashSet;

import question.QuestionManager;

public class MultiChoiceMultiAnswerAnswer extends Answer {

	public MultiChoiceMultiAnswerAnswer(int questionId,
			ArrayList<String> answerList, int answerOrder,
			double score) {
		super(questionId, answerList, QuestionManager.MULTI_CHOICE_MULTI_ANSWER, answerOrder, score);
	}
	
	public static double scoreUserInput(ArrayList<Answer> correctAnswers, ArrayList<String> userInput) {
		HashSet<String> correctAnswerSet = new HashSet<String>();
		for (int i = 0; i < correctAnswers.size(); i++) {
			if (correctAnswers.get(i).getScore() == 1) {
				correctAnswerSet.add(correctAnswers.get(i).getAnswerList().get(0));
			}
		}
		
		if (correctAnswerSet.size() != userInput.size()) {
			return 0;
		}
		
		for (int i = 0; i < userInput.size(); i++) {
			if (!correctAnswerSet.contains(userInput.get(i))) {
				return 0;
			}
		}
		return 1;
	}
}
