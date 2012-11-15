package webpackage;

public class Question {
	private int qID;
	protected int type;
	
	public Question(int qID) {
		this.qID = qID;
	}
	
	public int getID() {
		return qID;
	}
	
	public int getType() {
		return type;
	}
}
