package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.*;

import announcement.Announcement;

import webpackage.DBConnection;

public class AttemptManager {
	final public static int NUM_HIGH_SCORERS = 5;
	final public static long ONE_HOUR_MILLISECONDS = 60*60*1000;
	final public static int CUTOFF_HOURS=1;
	
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
			System.out.println(command);
			
			ret = new Attempt[numEntries];
			rs.first();
			for (int i = 0; i < numEntries; i++) {
				String user = rs.getString("userID");
				int quizID = rs.getInt("quizID");
				double score = rs.getDouble("score");
				int timeSpent = rs.getInt("timeSpent");
				Timestamp timeTaken = rs.getTimestamp("timeTaken");
				System.out.println("RS" + user + " " + quizID + " " + score + " " + timeSpent + " " + timeTaken);
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
			e.printStackTrace();
		}
		

	}
	
	public ArrayList<Attempt> getTopHighScorersEver(int quizID){
		ArrayList<Attempt> topHighScorers=new ArrayList<Attempt>();
		ArrayList<String> users=new ArrayList<String>();
		
		String command = "SELECT * FROM Attempts WHERE quizID = \""+ quizID + "\" ORDER BY score DESC;";
		System.out.println(command); //TODO remove; for verification purposes
		try {
			ResultSet rs = stmnt.executeQuery(command);
			int numScores = DBConnection.getResultSetSize(rs);
			System.out.println(numScores);
			for(int i = 0; i < numScores; i++){
				String username=rs.getString("userID");
				Double score=rs.getDouble("score");
				Timestamp timeTaken=rs.getTimestamp("timeTaken");
				int timeSpent=rs.getInt("timeSpent");
				System.out.println(score + " " + username + " " + timeTaken + " " + timeSpent);
				int index=users.indexOf(username);
				if(index==-1){
					users.add(username);
					Attempt attempt=new Attempt(username, quizID, score, timeSpent, timeTaken);
					topHighScorers.add(attempt);
				}
				if(topHighScorers.size()==NUM_HIGH_SCORERS) return topHighScorers;
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return topHighScorers;
	}

	public ArrayList<Attempt> getUserPastPerformance(int quizID, String username, String parameter){
		ArrayList<Attempt> sortedAttempts = new ArrayList<Attempt>();
		
		String command = "SELECT * FROM Attempts WHERE quizID = \""+ quizID + "\" AND userID = \""+ username + "\" ORDER BY " + parameter + " DESC";
		try {
			ResultSet rs = stmnt.executeQuery(command);
			int numAttempts = DBConnection.getResultSetSize(rs);
			for(int i = 0; i < numAttempts; i++){
				Double score=rs.getDouble("score");
				Timestamp timeTaken=rs.getTimestamp("timeTaken");
				int timeSpent=rs.getInt("timeSpent");
				Attempt attempt=new Attempt(username, quizID, score, timeSpent, timeTaken);
				sortedAttempts.add(attempt);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return sortedAttempts;
	}
	
	public ArrayList<Attempt> getTopPerformersByTime(int quizID){
		ArrayList<Attempt> topPerformers=new ArrayList<Attempt>();
		ArrayList<String> users=new ArrayList<String>();
		Timestamp timeToCompareTo=new Timestamp(System.currentTimeMillis()-CUTOFF_HOURS * ONE_HOUR_MILLISECONDS);
		String command = "SELECT * FROM Attempts WHERE quizID = \""+ quizID + "\" AND WHERE timeTaken < " + "'" + timeToCompareTo + "'" + " ORDER BY score DESC";
		try {
			ResultSet rs = stmnt.executeQuery(command);
			int numAttempts = DBConnection.getResultSetSize(rs);
			for(int i = 0; i < numAttempts; i++){
				String username=rs.getString("userID");
				Double score=rs.getDouble("score");
				Timestamp timeTaken=rs.getTimestamp("timeTaken");
				int timeSpent=rs.getInt("timeSpent");
				Attempt attempt=new Attempt(username, quizID, score, timeSpent, timeTaken);
				int index=users.indexOf(username);
				if(index != -1 && topPerformers.get(index).getScore() < score){
					topPerformers.set(index, attempt);
				}else{
					topPerformers.add(attempt);
					users.add(username);
				}
				if(topPerformers.size()==NUM_HIGH_SCORERS) return topPerformers;
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return topPerformers;
	}
		
	public ArrayList<Attempt> getMostRecentPerformers(int quizID){
		ArrayList<Attempt> mostRecentPerformers=new ArrayList<Attempt>();
		String command = "SELECT * FROM Attempts WHERE quizID = \""+ quizID + "\" ORDER BY timeTaken DESC";
		try {
			ResultSet rs = stmnt.executeQuery(command);
			int numAttempts = DBConnection.getResultSetSize(rs);
			for(int i = 0; i < numAttempts; i++){
				String username=rs.getString("userID");
				Double score=rs.getDouble("score");
				Timestamp timeTaken=rs.getTimestamp("timeTaken");
				int timeSpent=rs.getInt("timeSpent");
				Attempt attempt=new Attempt(username, quizID, score, timeSpent, timeTaken);
				mostRecentPerformers.add(attempt);
				if(mostRecentPerformers.size()== NUM_HIGH_SCORERS) return mostRecentPerformers;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return mostRecentPerformers;
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
