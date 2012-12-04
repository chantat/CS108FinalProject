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
	
	public void createAttempt(String userId, int quizId, double score, Timestamp time) {
		String query="INSERT INTO Attempts (userID, quizID, score, timeTaken) VALUES (";
		query += "\"" + userId + "\",";
		query += "\"" + quizId + "\",";
		query += "\"" + score + "\",";
		query += "\"" + time + ");";
		System.out.println(query); // for verification purposes
		try {
			stmnt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
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
				Timestamp time = rs.getTimestamp("timeTaken");
				ret[i] = new Attempt(user, quizID, score, time);
				rs.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	
	

}
