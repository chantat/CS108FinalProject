package question;



public class FillInTheBlank extends Question {

	public FillInTheBlank(int qID, String qText){
		super(qID, qText);
		this.numAnswers=1;
		this.type = 2;
	}
	
	public String getQuestionText() {
		return qText;
	}
}
