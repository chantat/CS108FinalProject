package answer;

import java.util.ArrayList;
import java.util.HashMap;

import question.QuestionManager;

public class MultipleChoiceAnswer extends Answer {

	public MultipleChoiceAnswer(int questionId, ArrayList<String> answerList,
			int answerOrder, double score) {
		super(questionId, answerList, QuestionManager.MULTIPLE_CHOICE, answerOrder, score);
	}

	public static double scoreUserInput(ArrayList<Answer> correctAnswers, ArrayList<String> userInput) {
		double totalScore = 0;
		for(int j = 0; j < userInput.size(); j++){
			System.out.println("USERINPUT:" + userInput.get(j));
			if(userInput.get(j).equals("on")) totalScore+=correctAnswers.get(j).getScore();
		}
		
		return totalScore;
	}
	
}
