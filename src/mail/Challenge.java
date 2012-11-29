package mail;

import java.sql.Timestamp;

public class Challenge {
	private int quizID;
	private String toID;
	private String fromID;
	private int status;
	private Timestamp time;
	
	
	public Challenge(int quizID, String toID, String fromID, int status, Timestamp time) {
		this.quizID = quizID;
		this.toID = toID;
		this.fromID = fromID;
		this.status = status;
		this.time = time;
	}
	
	public Challenge(int quizID, String toID, String fromID) {
		this.quizID = quizID;
		this.toID = toID;
		this.fromID = fromID;
		this.status = 0;
	}
	
	public int getQuizID() {
		return quizID;
	}
	
	public String getToID() {
		return toID;
	}
	
	public String getFromID() {
		return fromID;
	}
	
	public int getStatus() {
		return status;
	}
	
	public Timestamp getTime() {
		return time;
	}
}
