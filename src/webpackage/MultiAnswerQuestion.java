package webpackage;

public class MultiAnswerQuestion extends Question {

	private boolean isOrdered;
	private int numResponses;
	private String qText;
	
	public MultiAnswerQuestion(int qID, String qText, int numResponses, boolean isOrdered) {
		super(qID);
		this.isOrdered=isOrdered;
		this.qText=qText;
		this.numResponses=numResponses;
		type=5;
	}

	public int getNumResponses() {
		return numResponses;
	}
	
	public String getQuestionText() {
		return qText;
	}
	
	public boolean isOrdered(){
		return isOrdered;
	}
	
	
}
