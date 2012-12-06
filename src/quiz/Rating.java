package quiz;

import java.sql.Timestamp;

public class Rating {
	private double rating;
	private int quizID;
	private String userID;
	private Timestamp timeRated;
	
	public Rating(double rating, int quizID, String userID, Timestamp timeRated){
		this.rating=rating;
		this.quizID=quizID;
		this.userID=userID;
		this.timeRated=timeRated;
	}
	
	public double getRating(){
		return rating;
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
