package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import webpackage.DBConnection;

public class ReportManager {
	private static Statement stmnt;
		
		public ReportManager(DBConnection con){
			stmnt = con.getStatement();	
		}
		
		public void createReport(int quizId, int occurrence, Timestamp date) {
			if(!isReportExist(quizId)){
				String query="INSERT INTO Reported (quizID, occurrence, date) VALUES (";
				query += "\"" + quizId + "\",";
				query += "" + occurrence + ",'";
				query += date + "');";
				//System.out.println(query); // for verification purposes
				try {
					stmnt.executeUpdate(query);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else{
				//System.out.println("Trying to add a Report that already exists");
			}
		}
		
		public void deleteReport(int quizID){
			String command = "DELETE FROM Reported WHERE quizID = "+quizID+";";
			try {
				stmnt.executeUpdate(command);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		public boolean isReportExist(int quizID){
			String command = "SELECT * FROM Reported WHERE quizID = "+ quizID + ";";
			try {
				ResultSet rs = stmnt.executeQuery(command);
				int numEntries = DBConnection.getResultSetSize(rs);
				if(numEntries>=1){
					return true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return false;
			
		}
		
		public int getReportOccurrence(int quizID){
			if(isReportExist(quizID)){
				String command = "SELECT * FROM Reported WHERE quizID = "+ quizID + ";";
				try {
					ResultSet rs = stmnt.executeQuery(command);
					rs.next();
					int occur = rs.getInt("occurrence");
					return occur;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}
			return 0;
		}
		
		public Report[] getAllReported(){
			String command = "SELECT * FROM Reported;";
			Report reports[] = null;
			try {
				ResultSet rs = stmnt.executeQuery(command);
				int numEntries = DBConnection.getResultSetSize(rs);
				
				reports = new Report[numEntries];
				rs.first();
				for (int i = 0; i < numEntries; i++) {
					int quizID = rs.getInt("quizID");
					int occur = rs.getInt("occurrence");
					Timestamp date = rs.getTimestamp("date");
					reports[i] = new Report(quizID, occur, date);
					rs.next();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return reports;
			
		}
		
	/*	
		public boolean isReportExistFromUser(String userID, int quizID){
			String command = "SELECT * FROM Attempts WHERE userID = \""+ userID + "\" AND quizID = "+quizID+";";
			try {
				ResultSet rs = stmnt.executeQuery(command);
				int numEntries = DBConnection.getResultSetSize(rs);
				if(numEntries>=1){
					return true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return false;
			
		}
	*/	
		public void incrementOccurrence(int quizID){
			if(isReportExist(quizID)){
				try {
					
	
					int currentOccur = getReportOccurrence(quizID);
					int newOccur = currentOccur+1;
					
	//TEST
	//System.out.println("INCREMENTING OCCURRENCE "+currentOccur+" and new is "+newOccur);
					String command = "UPDATE Reported SET occurrence = "+newOccur+" WHERE quizID = "+quizID+";";
					stmnt.executeUpdate(command);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
		
}
