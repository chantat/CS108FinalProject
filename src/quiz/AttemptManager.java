package quiz;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import webpackage.DBConnection;

public class AttemptManager {
	
	private DBConnection con;
	
	public AttemptManager(DBConnection con){
		this.con=con;
	}
	
	public Attempt createAttempt(String userId, int quizId, int score, Timestamp timeTaken){
		String query="INSERT INTO Attemps (userID, quizID, score, timeTaken) VALUES (" + userId + ", \"" + quizId + "\", " + score + "\", " + timeTaken + "\");";
		System.out.println(query); // for verification purposes
		try {
			Statement stmt = con.getStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new Attempt(userId, quizId, score, timeTaken);
	}

}
