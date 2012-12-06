package quiz;

import java.sql.Timestamp;

public class Review {
	private String reviewText;
	private int quizID;
	private String userID;
	private Timestamp timeRated;
	
	public Review(String reviewText, int quizID, String userID, Timestamp timeRated){
		this.reviewText=reviewText;
		this.quizID=quizID;
		this.userID=userID;
		this.timeRated=timeRated;
	}
	
	public String getReview(){
		return reviewText;
	}
	
	public int getQuizID(){
		return quizID;
	}
	
	public String userID(){
		return userID;
	}
	public Timestamp getTimeRated(){
		return timeRated;
	}
}
