package quiz;

import java.sql.*;

public class Attempt {
	private final String userId;
	private final int quizId;
	private final Timestamp timeTaken;
	
	public Attempt(String userId, int quizId, Timestamp timeTaken) {
		this.userId = userId;
		this.quizId = quizId;
		this.timeTaken = timeTaken;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public int getQuizId() {
		return quizId; 
	}
	
	public Timestamp getTimeTaken() {
		return timeTaken;
	}
}
