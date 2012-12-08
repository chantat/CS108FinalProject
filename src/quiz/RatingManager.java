package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import webpackage.DBConnection;

public class RatingManager {

final public static int NUM_MOST_POPULAR_QUIZZES = 5;
final public static int RATING_MAXIMUM = 5;
private static Statement stmnt;
	
	public RatingManager(DBConnection con){
		stmnt = con.getStatement();	
	}
	
	public Rating[] getMostPopularQuizzes(){
		Rating[] mostPopularQuizIDs=null;
		String command = "SELECT DISTINCT quizID FROM Rating;";
		int[] ratedQuizzes=null;
		Rating[] quizRatings =null;
		try {
			ResultSet rs = stmnt.executeQuery(command);
			int numQuizzes = DBConnection.getResultSetSize(rs);
			quizRatings =new Rating[numQuizzes];
			ratedQuizzes=new int[numQuizzes];
			rs.first();
			//System.out.println(numQuizzes + " NUMQUIZZES");
			for(int i = 0; i < numQuizzes; i++){
				int quizID= rs.getInt("quizID");
				ratedQuizzes[i]=quizID;
				rs.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < ratedQuizzes.length; i++){
			quizRatings[i]=new Rating(getAverageRating(ratedQuizzes[i]), ratedQuizzes[i], null, null);
		}
		Arrays.sort(quizRatings, new Comparator<Rating>() {
		    public int compare(Rating r1, Rating r2) {
		        return (int)(r2.getRating() - r1.getRating());
		    }
		});
		if(quizRatings.length < NUM_MOST_POPULAR_QUIZZES){
			mostPopularQuizIDs=new Rating[quizRatings.length];
			for(int i = 0; i < quizRatings.length; i++){
				mostPopularQuizIDs[i]=quizRatings[i];
			}
		}else{
			mostPopularQuizIDs=new Rating[NUM_MOST_POPULAR_QUIZZES];
			for(int i = 0; i < NUM_MOST_POPULAR_QUIZZES; i++){
				mostPopularQuizIDs[i]=quizRatings[i];
			}
		}
		return mostPopularQuizIDs;
	}
	
	public double getAverageRating(int quizID){
		String command = "SELECT userID, rating, ratingTime FROM Rating WHERE quizID = \""+ quizID + "\";";
		ArrayList<String> distinctUsers = new ArrayList<String>();
		ArrayList<Integer> ratings = new ArrayList<Integer>();
		ArrayList<Timestamp> timestamps = new ArrayList<Timestamp>();
		
		try {
			ResultSet rs = stmnt.executeQuery(command);
			int numRatings = DBConnection.getResultSetSize(rs);
			if(numRatings == 0) return -1;
			rs.first();
			for (int i = 0; i < numRatings; i++) {
				String user = rs.getString("userID");
				int rating = rs.getInt("rating");
				Timestamp timestamp = rs.getTimestamp("ratingTime");
				int index = distinctUsers.indexOf(user);
				if(index != -1 && timestamps.get(index).before(timestamp)){
					//System.out.println(quizID + "removing: " + timestamps.get(index) + " " + timestamp + " " + rating + " " + user);
					distinctUsers.remove(index);
					ratings.remove(index);
					timestamps.remove(index);
					distinctUsers.add(user);
					ratings.add(rating);
					timestamps.add(timestamp);
				}else if(index == -1){
					distinctUsers.add(user);
					ratings.add(rating);
					timestamps.add(timestamp);
					//System.out.println(quizID + "adding: " + timestamp + " " + rating);
				}
				rs.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		double sum=0.0;
		for(int i = 0; i < ratings.size(); i++){
			sum+=ratings.get(i);
		}
		return ((sum)/ratings.size());
	}
	
	public void createRating(String userId, int quizId, int rating, Timestamp time) {
		String query="INSERT INTO Rating (userID, quizID, rating, ratingTime) VALUES (";
		query += "\"" + userId + "\",";
		query += "" + quizId + ",";
		query += "" + rating + ", '";
		query += time + "');";
		//System.out.println(query); // for verification purposes
		try {
			stmnt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public double getTotalAverageRating(){
		String command = "SELECT AVG(rating) FROM Rating;";
		
		try {
			ResultSet rs = stmnt.executeQuery(command);			
			if(rs.next()){
				return rs.getDouble("AVG(rating)");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;  //case where no ratings exist yet
	}
	
	public  int getNumStars(String username, int stars){  //returns number of X-star ratings given by user
		String quote = "\"";
		String command = "SELECT * FROM Rating WHERE userID = "+quote+username+quote+" AND rating = "+stars+";";
		try {
			ResultSet rs = stmnt.executeQuery(command);
			int numStars = DBConnection.getResultSetSize(rs);
			return numStars;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
