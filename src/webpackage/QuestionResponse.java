package webpackage;

public class QuestionResponse extends Question {

	private String qText;
	private int numResponses;
	
	public QuestionResponse(int qID, String qText, int numResponses) {
		super(qID);
		this.qText = qText;
		this.numResponses = numResponses;
		type = 1;
	}
	
	public int getNumResponses() {
		return numResponses;
	}
	
	public String getQuestionText() {
		return qText;
	}
}
