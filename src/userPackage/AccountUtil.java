package userPackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
}
