package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import announcement.Announcement;

import webpackage.DBConnection;

public class AttemptManager {
	
	private static Statement stmnt;
	
	public AttemptManager(DBConnection con){
		stmnt = con.getStatement();	
	}
	
	public void createAttempt(String userId, int quizId, double score, int timeTaken, Timestamp endTime) {
		String query="INSERT INTO Attempts (userID, quizID, score, timeSpent, timeTaken) VALUES (";
		query += "\"" + userId + "\",";
		query += "" + quizId + ",";
		query += "" + score + ",";
		query += "" + timeTaken + ",'";
		query += endTime + "');";
		System.out.println(query); // for verification purposes
		try {
			stmnt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Is already sorted newest first!
	public Attempt[] getAllAttempts(String userID) {
		String command = "SELECT * FROM Attempts WHERE userID = \""+ userID + "\" ORDER BY timeTaken DESC;";
		
		Attempt ret[] = null;
		try {
			ResultSet rs = stmnt.executeQuery(command);
			int numEntries = DBConnection.getResultSetSize(rs);
			
			ret = new Attempt[numEntries];
			rs.first();
			for (int i = 0; i < numEntries; i++) {
				String user = rs.getString("userID");
				int quizID = rs.getInt("quizID");
				double score = rs.getDouble("score");
				int timeSpent = rs.getInt("timeSpent");
				Timestamp timeTaken = rs.getTimestamp("timeTaken");
				ret[i] = new Attempt(user, quizID, score, timeSpent, timeTaken);
				rs.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	public void deleteAllQuizAttempts(int quizID) {
		String command = "DELETE FROM Attempts WHERE quizID = \""+ quizID + "\";";
	
		try {
			stmnt.executeUpdate(command);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	
	public double getQuizHighScore(int quizID){
		String command = "SELECT * FROM Attempts WHERE quizID = \""+ quizID + "\" ORDER BY score DESC;";
		try {
			ResultSet rs = stmnt.executeQuery(command);
			if(rs.next()){
				return rs.getDouble("score"); //returns the topmost attempt's score (which should be the high score)			
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;			  //returns a score that is lower than possible if no scores exist for this quiz
	}

}
