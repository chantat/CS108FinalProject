package webpackage;

import java.util.*;

public class MultiChoiceMultiAnswerQuestion extends Question {

	private String qText;
	private ArrayList<String> qOptions;
	private int numOptions;
	
	public MultiChoiceMultiAnswerQuestion(int qID, String qText, ArrayList<String> qOptions) {
		super(qID);
		type=6;
		this.numOptions=qOptions.size();
		this.qOptions=qOptions;
	}
	
	public int getNumOptions() {
		return numOptions;
	}
	
	public String getQuestionText() {
		return qText;
	}
	
	public ArrayList<String> getQuestionOptions(){
		return qOptions;
	}

}
