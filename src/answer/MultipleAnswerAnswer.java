package answer;

import java.util.ArrayList;
import java.util.HashMap;

import question.QuestionManager;

public class MultipleAnswerAnswer extends Answer{
	public MultipleAnswerAnswer(int questionId, ArrayList<String> answerList, int answerOrder) {
		super(questionId, answerList, QuestionManager.MULTI_ANSWER, answerOrder, 1);
	}
	
	public static double scoreUserInput(ArrayList<Answer> correctAnswers, ArrayList<String> userInput) {
		System.out.println("MAScoreuserinput");
		double totalScore = 0;
		for (int i = 0; i < correctAnswers.size(); i++) {
			Answer answer = correctAnswers.get(i);
			ArrayList<String> answerList = answer.getAnswerList();
			int answerOrder = answer.getAnswerOrder();
			for (int j = 0; j < answerList.size(); j++) {
				String answerText = answerList.get(j);
				if (userInput.get(answerOrder).equals(answerText)) {
					totalScore += answer.getScore();
					break;
				}
			}
		}
		
		return totalScore;
	}
}
