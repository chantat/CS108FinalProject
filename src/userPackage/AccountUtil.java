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
		Boolean usedPracticeMode = false;
		try {
			rs = stmnt.executeQuery(command);
			rs.next();


			//usedPracticeMode = (Integer)rs.getObject("usedPracticeMode");
			usedPracticeMode = rs.getInt("usedPracticeMode");

		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return usedPracticeMode;
	}
	
	public boolean isHadHighScore(String username) {
		String command = "SELECT * FROM QuizUser WHERE username = \"" + username + "\";";
		
		ResultSet rs = null;
		Boolean hadHighScore = false;
		try {
			rs = stmnt.executeQuery(command);
			rs.next();

			//hadHighScore = (Integer)rs.getObject("hadHighScore");
			hadHighScore = rs.getInt("hadHighScore");

		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return hadHighScore;
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
				Integer score = rs.getInt("score");
				Timestamp timeTaken = rs.getTimestamp("timeTaken");
				
				ret[i] = new Attempt(userId, quizId, score, timeTaken);
				rs.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return ret;
	}
}
