package quiz;

import java.sql.*;

public class Attempt {
	private final String userId;
	private final int quizId;
	private final double score;
	private final int timeSpent;
	private final Timestamp timeTaken;
	
	public Attempt(String userId, int quizId, double score, int timeSpent) {
		this.userId = userId;
		this.quizId = quizId;
		this.score = score;
		this.timeSpent = timeSpent;
		this.timeTaken = null;
	}
	
	public Attempt(String userId, int quizId, double score, int timeSpent, Timestamp timeTaken) {
		this.userId = userId;
		this.quizId = quizId;
		this.score = score;
		this.timeSpent = timeSpent;
		this.timeTaken = timeTaken;
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

	public int getTimeSpent(){
		return timeSpent;	
	}
	public Timestamp getTimeTaken(){
		if(timeTaken != null){
			return timeTaken;
		}
		else{
			System.out.println("Tried to retrieve an uninitialized Timestamp from this attempt");
			System.exit(1);
			return timeTaken;   //dummy...will never get here
		}

	}
}
