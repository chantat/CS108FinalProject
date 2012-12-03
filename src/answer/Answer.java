package answer;

import java.util.*;

public class Answer {
	private int numAnswers;
	private double currentScore=0.0;
	private HashMap <String, String> answers;
	private HashMap <String, Double> answerScores;
	private HashSet<String> alreadyUsedAnswers;
	private int qType;
	
	
	public Answer(int qType, int numAnswers, HashMap<String, String> answers, HashMap<String, Double> answerScores){
		this.numAnswers=numAnswers;
		this.qType=qType;
		this.answers=answers;
		this.alreadyUsedAnswers=new HashSet<String>();
		this.answerScores=answerScores;
	}
	
	private boolean validateGuess(String entry, String entry2){ //entry2 only for matching
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
	}
	
	public double scoreGuess(String entry, String entry2){
		validateGuess(entry, entry2);
		return currentScore;
	}
	
	public double getPossibleScore(){
		double sum=0.0;
		for (Map.Entry<String, Double> entry : answerScores.entrySet()){
			sum+=entry.getValue();
		}
		if(qType==7) sum/=2.0;
		return sum;
	}
	
}
