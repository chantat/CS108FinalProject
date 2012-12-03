package question;

public class Question {
	private int qID;
	protected int type;
	protected int numAnswers;
	protected String qText;
	protected boolean isOrdered;
	
	public Question(int qID, String questionText) {
		this.qID = qID;
		this.isOrdered = false;
		this.qText=questionText;
	}
	
	public void setID(int qID) {
		this.qID = qID;
	}
	
	public int getID() {
		return qID;
	}
	
	public int getType() {
		return type;
	}
	
	public int getNumAnswers() {
		return numAnswers;
	}
	
	public String getQText() {
		return qText;
	}
}
