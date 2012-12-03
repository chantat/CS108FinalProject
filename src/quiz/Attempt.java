package quiz;

import java.sql.*;

public class Attempt {
	private final String userId;
	private final int quizId;
	private final double score;
	private final Timestamp timeTaken;
	
	public Attempt(String userId, int quizId, double score, Timestamp timeTaken) {
		this.userId = userId;
		this.quizId = quizId;
		this.timeTaken = timeTaken;
		this.score = score;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public int getQuizId() {
		return quizId; 
	}
	
	public double getScore() {
		return score;
	}
	
	public Timestamp getTimeTaken() {
		return timeTaken;
	}
}
