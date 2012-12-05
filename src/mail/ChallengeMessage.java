package mail;

import java.sql.Timestamp;

public class ChallengeMessage extends Message {
	private int quizID;
	private double score;
	
	public ChallengeMessage(String toID, String fromID, String subject, String message, Timestamp time, int status, int quizID, double score) {
		super(toID, fromID, subject, message, time, status, "Challenge");
		this.quizID = quizID;
		this.score = score;
	}
	
	public ChallengeMessage(String toID, String fromID, String message, int quizID, double score) {
		super(toID, fromID, "Challenge!", message, "Challenge");
		this.quizID = quizID;
		this.score = score;
	}
	
	public int getQuizID() {
		return quizID;
	}
	
	public double getScore() {
		return score;
	}
}
