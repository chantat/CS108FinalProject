package webpackage;

public class FillInTheBlank extends Question {

	private String qText;
	private int numResponses;
	
	public FillInTheBlank(int qID, String qText, int numResponses) {
		super(qID);
		this.qText = qText;
		this.numResponses = numResponses;
		type = 2;
	}
	
	public int getNumResponses() {
		return numResponses;
	}
	
	public String getQuestionText() {
		return qText;
	}

}
