package quiz;


public class QuestionResponse extends Question {

	private String qText;
	
	public QuestionResponse(int qID, String qText) {
		super(qID);
		this.qText = qText;
		this.numAnswers=1;
		type = 1;
	}
	
	public String getQuestionText() {
		return qText;
	}
}
