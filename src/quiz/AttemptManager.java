package quiz;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import webpackage.DBConnection;

public class AttemptManager {
	
	private static Statement stmnt;
	
	public AttemptManager(DBConnection con){
		stmnt = con.getStatement();	
	}
	
	public void createAttempt(String userId, int quizId, double score) {
		String query="INSERT INTO Attempts (userID, quizID, score) VALUES (";
		query += "\"" + userId + "\",";
		query += quizId + ",";
		query += score + ");";
		System.out.println(query); // for verification purposes
		try {
			stmnt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
