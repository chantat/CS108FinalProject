package answer;

import java.util.ArrayList;
import java.util.HashMap;

import question.QuestionManager;

public class MultipleAnswerAnswer extends Answer{
	public MultipleAnswerAnswer(int questionId, ArrayList<String> answerList, int answerOrder) {
		super(questionId, answerList, QuestionManager.MULTI_ANSWER, answerOrder, 1);
	}
	
	public static double scoreUserInput(ArrayList<Answer> correctAnswers, ArrayList<String> userInput) {
		double totalScore = 0;
		for (int i = 0; i < correctAnswers.size(); i++) {
			Answer answer = correctAnswers.get(i);
			ArrayList<String> answerList = answer.getAnswerList();
			int answerOrder = answer.getAnswerOrder();
			for (int j = 0; j < answerList.size(); j++) {
				String answerText = answerList.get(j).toLowerCase();
				String userInputText = userInput.get(answerOrder).toLowerCase();
				if (userInputText.equals(answerText)) {
					totalScore += answer.getScore();
					break;
				}
			}
		}
		
		return totalScore;
	}
}
