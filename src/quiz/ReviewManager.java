package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import webpackage.DBConnection;

public class ReviewManager {
	
	private static Statement stmnt;
	final public static int NUM_MOST_RECENT_REVIEWS = 5;
	
	public ReviewManager(DBConnection con){
		stmnt = con.getStatement();	
	}
	
	public void createReview(String userId, int quizId, String review, Timestamp time) {
		if(review.isEmpty()) return;
		String query="INSERT INTO Review (userID, quizID, review, reviewTime) VALUES (";
		query += "\"" + userId + "\",";
		query += "" + quizId + ",";
		query += "\"" + review + "\", '";
		query += time + "');";
		System.out.println(query); // for verification purposes
		try {
			stmnt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Review> getAllReviews(int quizID){
		ArrayList<Review> allReviews=new ArrayList<Review>();
		String command = "SELECT * FROM Review WHERE quizID = \""+ quizID + "\" ORDER BY reviewTime DESC;";
		try{
			ResultSet rs = stmnt.executeQuery(command);
			int numReviews = DBConnection.getResultSetSize(rs);
			rs.first();
			for(int i = 0; i < numReviews; i++){
				String user = rs.getString("userID");
				Timestamp timestamp = rs.getTimestamp("reviewTime");
				String reviewText = rs.getString("review");
				Review review = new Review(reviewText, quizID, user, timestamp);
				allReviews.add(review);
				rs.next();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return allReviews;
	}
	
	public ArrayList<Review> getMostRecentReviews(int quizID){
		ArrayList<Review> mostRecentReviews=new ArrayList<Review>();
		String command = "SELECT * FROM Review WHERE quizID = \""+ quizID + "\" ORDER BY reviewTime DESC;";
		try{
			ResultSet rs = stmnt.executeQuery(command);
			int numReviews = DBConnection.getResultSetSize(rs);
			rs.first();
			for(int i = 0; i < Math.min(numReviews, NUM_MOST_RECENT_REVIEWS); i++){
				String user = rs.getString("userID");
				Timestamp timestamp = rs.getTimestamp("reviewTime");
				String reviewText = rs.getString("review");
				Review review = new Review(reviewText, quizID, user, timestamp);
				mostRecentReviews.add(review);
				rs.next();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return mostRecentReviews;
	}
	
}
