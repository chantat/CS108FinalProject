package question;



public class MultiAnswerQuestion extends Question {
	public MultiAnswerQuestion(int qID, String qText, int numAnswers, boolean isOrdered) {
		super(qID, qText);
		this.isOrdered = isOrdered;
		this.numAnswers = numAnswers;
		this.type = QuestionManager.MULTI_ANSWER;
	}
	
	public boolean isOrdered(){
		return isOrdered;
	}
}
