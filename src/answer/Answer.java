package answer;

import java.util.*;

import question.QuestionManager;

public class Answer implements Comparable {
	private int questionId;
	private int answerOrder;
	private ArrayList<String> answerList;
	private int questionType;
	private double score;
	
	public Answer(int questionId, ArrayList<String> answerList, int questionType, int answerOrder, double score) {
		this.questionId = questionId;
		this.answerList = answerList;
		this.questionType = questionType;
		this.answerOrder = answerOrder;
		this.score = score;
	}
	
	public int getQuestionId() {
		return questionId;
	}
	
	public int getAnswerOrder() {
		return answerOrder;
	}
	
	public ArrayList<String> getAnswerList() {
		return answerList;
	}
	
	public int getQuestionType() {
		return questionType;
	}
	
	public double getScore() {
		return score;
	}
	
	public static double scoreUserInput(ArrayList<Answer> correctAnswers, ArrayList<String> userInput) {
		if (correctAnswers.get(0).getQuestionType() == QuestionManager.MATCHING) {
			return MatchingAnswer.scoreUserInput(correctAnswers, userInput);
		} else if (correctAnswers.get(0).getQuestionType() == QuestionManager.MULTI_ANSWER) {
			if (correctAnswers.get(0).getAnswerOrder() != -1) {
				return MultipleAnswerAnswer.scoreUserInput(correctAnswers, userInput);
			}
		} else if(correctAnswers.get(0).getQuestionType() == QuestionManager.MULTI_CHOICE_MULTI_ANSWER) {
			return MultiChoiceMultiAnswerAnswer.scoreUserInput(correctAnswers, userInput);
		}
		
		HashMap<String, Integer> userInputSet = new HashMap<String, Integer>();
		for (int i = 0; i < userInput.size(); i++) {
			userInputSet.put(userInput.get(i).toLowerCase(), i);
		}
		
		double totalScore = 0;
		for (int i = 0; i < correctAnswers.size(); i++) {
			Answer answer = correctAnswers.get(i);
			ArrayList<String> answerList = answer.getAnswerList();
			for (int j = 0; j < answerList.size(); j++) {
				String answerText = answerList.get(j).toLowerCase();
				if (userInputSet.containsKey(answerText)) {
					totalScore += correctAnswers.get(i).getScore();
					break;
				}
			}
		}
		
		return totalScore;
	}

	public static double getPossibleScore(ArrayList<Answer> answers){
		double totalScore = 0; 
		for (int i = 0; i < answers.size(); i++) {
			totalScore += answers.get(i).getScore();
		}
		return totalScore;
	}
	
	@Override
	public int compareTo(Object o) {
		Answer a = (Answer)o;
		Integer t = answerOrder;
		return t.compareTo(a.getAnswerOrder());
	}
	// OLD CODE
	/*private boolean validateGuess(String entry, String entry2){ //entry2 only for matching
	boolean isCorrectGuess=false;
	if(qType != 7){
		if(answers.containsKey(entry) && !alreadyUsedAnswers.contains(answers.get(entry))){
			isCorrectGuess=true;
			alreadyUsedAnswers.add(answers.get(entry));
			currentScore+=answerScores.get(entry);
		}
	}else if(qType == 7){
		if(answers.get(entry).equals(entry2) && !alreadyUsedAnswers.contains(entry2)){
			isCorrectGuess=true;
			alreadyUsedAnswers.add(entry2);
			currentScore+=answerScores.get(entry);
		}else if(answers.get(entry2).equals(entry) && !alreadyUsedAnswers.contains(entry)){
			isCorrectGuess=true;
			alreadyUsedAnswers.add(entry);
			currentScore+=answerScores.get(entry2);
		}
	}
	return isCorrectGuess;
}*/


/*public double scoreGuess(String entry, String entry2){
	validateGuess(entry, entry2);
	return currentScore;
}

*/
}
