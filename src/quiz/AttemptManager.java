package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
		//System.out.println(query); // for verification purposes
		try {
			stmnt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Is already sorted newest first!
	public  Attempt[] getAllAttempts(String userID) {
		String command = "SELECT * FROM Attempts WHERE userID = \""+ userID + "\" ORDER BY timeTaken DESC;";
		
		Attempt ret[] = null;
		try {
			ResultSet rs = stmnt.executeQuery(command);
			int numEntries = DBConnection.getResultSetSize(rs);
			//System.out.println(command);
			
			ret = new Attempt[numEntries];
			if(numEntries != 0) rs.first();
			for (int i = 0; i < numEntries; i++) {
				String user = rs.getString("userID");
				int quizID = rs.getInt("quizID");
				double score = rs.getDouble("score");
				score=Double.parseDouble(String.format("%.2f", score));
				int timeSpent = rs.getInt("timeSpent");
				Timestamp timeTaken = rs.getTimestamp("timeTaken");
<<<<<<< HEAD
				score=Double.parseDouble(String.format("%.2f", score));
				System.out.println("RS" + user + " " + quizID + " " + score + " " + timeSpent + " " + timeTaken);
=======
				//System.out.println("RS" + user + " " + quizID + " " + score + " " + timeSpent + " " + timeTaken);
>>>>>>> d070448875a273fda9bd1ec7f70208ff847ce6b3
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
	
	public double getAverageScore(int quizID){
		double totalScore = 0.0;
		double numAttempts=0;
		
		String command = "SELECT * FROM Attempts WHERE quizID = \""+ quizID + "\";";
		//System.out.println(command); //TODO remove; for verification purposes
		try {
			ResultSet rs = stmnt.executeQuery(command);
			numAttempts = DBConnection.getResultSetSize(rs);
			//System.out.println(numAttempts);
			if(numAttempts==0) return -1.0;
			rs.first();
			for(int i = 0; i < numAttempts; i++){
				Double score=rs.getDouble("score");
				totalScore+=score;
				rs.next();
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		double score=totalScore/numAttempts;
		score=Double.parseDouble(String.format("%.2f", score));
		return score;
	}
	
	public int getNumAttempts(int quizID){
		int numAttempts=0;
		String command = "SELECT COUNT(*) FROM Attempts WHERE quizID = \""+ quizID + "\";";
		//System.out.println(command); //TODO remove; for verification purposes
		try {
			ResultSet rs = stmnt.executeQuery(command);
			numAttempts = DBConnection.getResultSetSize(rs);
			//System.out.println("NUMATTEMPTS: " + numAttempts);		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return numAttempts;
	}
	
	public ArrayList<Attempt> getTopHighScorersEver(int quizID){
		ArrayList<Attempt> topHighScorers=new ArrayList<Attempt>();
		ArrayList<String> users=new ArrayList<String>();
		
		String command = "SELECT * FROM Attempts WHERE quizID = \""+ quizID + "\" ORDER BY score DESC, timeSpent ASC;";
		//System.out.println(command); //TODO remove; for verification purposes
		try {
			ResultSet rs = stmnt.executeQuery(command);
			int numScores = DBConnection.getResultSetSize(rs);
			//System.out.println(numScores);
			if(numScores!=0) rs.first();
			for(int i = 0; i < numScores; i++){
				String username=rs.getString("userID");
				Double score=rs.getDouble("score");
				Timestamp timeTaken=rs.getTimestamp("timeTaken");
				int timeSpent=rs.getInt("timeSpent");
				int index=users.indexOf(username);
				//System.out.println(score + " " + username + " " + timeTaken + " " + timeSpent + " " + index); // TODO remove, for testing purposes
				if(index==-1){
					users.add(username);
					Attempt attempt=new Attempt(username, quizID, score, timeSpent, timeTaken);
					topHighScorers.add(attempt);
				}
				if(topHighScorers.size()==NUM_HIGH_SCORERS) return topHighScorers;
				rs.next();
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return topHighScorers;
	}

	public ArrayList<Attempt> getUserPastPerformance(int quizID, String username, String parameter){
		ArrayList<Attempt> sortedAttempts = new ArrayList<Attempt>();
		
		String command = "SELECT * FROM Attempts WHERE quizID = \""+ quizID + "\" AND userID = \""+ username + "\" ORDER BY " + parameter + " DESC, timeTaken DESC";
		try {
			ResultSet rs = stmnt.executeQuery(command);
			int numAttempts = DBConnection.getResultSetSize(rs);
			if(numAttempts!=0) rs.first();
			for(int i = 0; i < numAttempts; i++){
				Double score=rs.getDouble("score");
				score=Double.parseDouble(String.format("%.2f", score));
				Timestamp timeTaken=rs.getTimestamp("timeTaken");
				int timeSpent=rs.getInt("timeSpent");
				Attempt attempt=new Attempt(username, quizID, score, timeSpent, timeTaken);
				sortedAttempts.add(attempt);
				rs.next();
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
		String command = "SELECT * FROM Attempts WHERE quizID = \""+ quizID + "\" AND timeTaken > " + "'" + timeToCompareTo + "'" + " ORDER BY score DESC, timeTaken DESC";
		try {
			ResultSet rs = stmnt.executeQuery(command);
			int numAttempts = DBConnection.getResultSetSize(rs);
			if(numAttempts!=0) rs.first();
			for(int i = 0; i < numAttempts; i++){
				String username=rs.getString("userID");
				Double score=rs.getDouble("score");
				score=Double.parseDouble(String.format("%.2f", score));
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
				rs.next();
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
			if(numAttempts!=0) rs.first();
			for(int i = 0; i < numAttempts; i++){
				String username=rs.getString("userID");
				Double score=rs.getDouble("score");
				score=Double.parseDouble(String.format("%.2f", score));
				Timestamp timeTaken=rs.getTimestamp("timeTaken");
				int timeSpent=rs.getInt("timeSpent");
				Attempt attempt=new Attempt(username, quizID, score, timeSpent, timeTaken);
				mostRecentPerformers.add(attempt);
				if(mostRecentPerformers.size()== NUM_HIGH_SCORERS) return mostRecentPerformers;
				rs.next();
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
