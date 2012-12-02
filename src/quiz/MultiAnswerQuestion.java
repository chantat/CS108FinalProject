package quiz;


public class MultiAnswerQuestion extends Question {

	private String qText;
	
	public MultiAnswerQuestion(int qID, String qText, int numAnswers, boolean isOrdered) {
		super(qID);
		this.isOrdered=isOrdered;
		this.qText=qText;
		this.numAnswers=numAnswers;
		type=5;
	}
	
	public String getQuestionText() {
		return qText;
	}
	
	public boolean isOrdered(){
		return isOrdered;
	}
	
	
}
