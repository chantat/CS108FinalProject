package quiz;

import java.sql.*;

public class Attempt {
	private final String userId;
	private final int quizId;
	private final double score;
	private final Timestamp time;
	
	public Attempt(String userId, int quizId, double score) {
		this.userId = userId;
		this.quizId = quizId;
		this.score = score;
		this.time = null;
	}
	
	public Attempt(String userId, int quizId, double score, Timestamp time) {
		this.userId = userId;
		this.quizId = quizId;
		this.score = score;
		this.time = time;
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

	public Timestamp getTimeTaken(){
		if(time != null){
			return time;
		}
		else{
			System.out.println("Tried to retrieve an uninitialized Timestamp from this attempt");
			System.exit(1);
			return time;   //dummy...will never get here
		}

	}
}
