package userPackage;

import java.sql.*;

import quiz.Attempt;

import webpackage.DBConnection;

public class AccountUtil {
	Statement stmnt;
	
	public AccountUtil(DBConnection con) {
		stmnt = con.getStatement();
	}
	
	public int getNumQuizCreated(String username) {
		String command = "SELECT * FROM Quiz WHERE authorID = \"" + username + "\" AND prevID = -1;";
		ResultSet rs = null;
		try {
			rs = stmnt.executeQuery(command);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return DBConnection.getResultSetSize(rs);
	}
	
	public int getNumQuizTaken(String username) {
		String command = "SELECT * FROM Attempts WHERE userID = \"" + username + "\";";
		ResultSet rs = null;
		try {
			rs = stmnt.executeQuery(command);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return DBConnection.getResultSetSize(rs);
	}
	
	public boolean isUsedPracticeMode(String username) {
		String command = "SELECT * FROM QuizUser WHERE username = \"" + username + "\";";
		
		ResultSet rs = null;
		Integer usedPracticeMode = 0;
		try {
			rs = stmnt.executeQuery(command);
			rs.next();
			usedPracticeMode = (Integer)rs.getObject("usedPracticeMode");
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return (usedPracticeMode == 1);
	}
	
	public boolean isHadHighScore(String username) {
		String command = "SELECT * FROM QuizUser WHERE username = \"" + username + "\";";
		
		ResultSet rs = null;
		Integer hadHighScore = 0;
		try {
			rs = stmnt.executeQuery(command);
			rs.next();
			hadHighScore = (Integer)rs.getObject("hadHighScore");
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return (hadHighScore == 1);
	}
	
	public Attempt[] getAllQuizAttempt(String username) {
		String command = "SELECT * FROM Attempts WHERE userID = \"" + username + "\";";
		
		Attempt ret[] = null;
		ResultSet rs = null;
		try {
			rs = stmnt.executeQuery(command);
			int numEntries = DBConnection.getResultSetSize(rs);
			
			ret = new Attempt[numEntries];
			rs.first();
			for (int i = 0; i < numEntries; i++) {
				String userId = rs.getString("userID");
				Integer quizId = rs.getInt("quizID");
				Timestamp timeTaken = rs.getTimestamp("timeTaken");
				
				ret[i] = new Attempt(userId, quizId, timeTaken);
				rs.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return ret;
	}
}
