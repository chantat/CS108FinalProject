package webpackage;

public class FillInTheBlank extends Question {

	private String qText;
	
	public FillInTheBlank(int qID, String qText){
		super(qID);
		this.qText = qText;
		this.numAnswers=1;
		type = 2;
	}
	
	public String getQuestionText() {
		return qText;
	}

}
