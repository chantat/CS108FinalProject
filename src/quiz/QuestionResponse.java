package quiz;


public class QuestionResponse extends Question {

	public QuestionResponse(int qID, String qText) {
		super(qID, qText);
		this.numAnswers = 1;
		this.type = 1;
	}
	
	public String getQuestionText() {
		return qText;
	}
}
