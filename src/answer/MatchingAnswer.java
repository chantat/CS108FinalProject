package answer;

import java.util.*;

import question.QuestionManager;

public class MatchingAnswer extends Answer {
	public MatchingAnswer(int questionId, String answerText, int answerOrder) {
		super(questionId, new ArrayList<String>(Arrays.asList(new String[] {answerText})), 
				QuestionManager.MATCHING, answerOrder, 1);
	}
	
	public static double scoreUserInput(ArrayList<Answer> correctAnswers, ArrayList<String> userInput) {
		HashMap<String, Integer> userInputSet = new HashMap<String, Integer>();
		for (int i = 0; i < userInput.size(); i++) {
			userInputSet.put(userInput.get(i), i);
		}
		
		double totalScore = 0;
		for (int i = 0; i < correctAnswers.size(); i++) {
			Answer answer = correctAnswers.get(i);
			String answerText = answer.getAnswerList().get(0);
			String tokens[] = answerText.split("#");
			String rightAnswerText = tokens[1];
			
			if(userInput.get(i).equals(rightAnswerText)) {
				totalScore += answer.getScore();
			}
		}
		
		return totalScore;
	}
}
