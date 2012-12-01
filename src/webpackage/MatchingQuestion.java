package webpackage;

import java.util.*;

public class MatchingQuestion extends Question {

	private ArrayList<String> column1;
	private ArrayList<String> column2;
	
	public MatchingQuestion(int qID, ArrayList<String> column1, ArrayList<String> column2) {
		super(qID);
		type=7;
		this.column1=column1;
		this.column2=column2;		
	}
	
	public ArrayList<String> getColumn1() {
		return column1;
	}
	
	public ArrayList<String> getColumn2() {
		return column2;
	}

}
