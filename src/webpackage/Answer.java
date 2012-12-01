package webpackage;

import java.util.*;

public class Answer {
	private int qID;
	private int questionType;
	private double score;
	private int numAnswers;
	private ArrayList<String> acceptableAnswers;
	private HashMap <String, ArrayList<String>> multiAnswers;
	
	public Answer(Question question, double score, ArrayList<String> acceptableAnswers, HashMap multiAnswers, int numAnswers){
		this.qID=question.getID();
		this.questionType=question.getType();
		this.score=score;
		this.numAnswers=numAnswers;
		if(questionType == 5 || questionType==7){
			this.multiAnswers=new HashMap<String, ArrayList<String>>();
			this.multiAnswers=multiAnswers;
		}else{
			this.acceptableAnswers=new ArrayList<String>();
			for(int i = 0; i < acceptableAnswers.size(); i++){
				this.acceptableAnswers.add(acceptableAnswers.get(i).toLowerCase()); //for comparison purposes
			}
		}
	}
}
