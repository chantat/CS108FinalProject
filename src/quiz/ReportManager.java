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
		
		public void createReport(int quizId, int occurence, Timestamp date) {
			if(!isReportExist(quizId)){
				String query="INSERT INTO Reported (quizID, occurence, date) VALUES (";
				query += "\"" + quizId + "\",";
				query += "" + occurence + ",";
				query += date + "');";
				System.out.println(query); // for verification purposes
				try {
					stmnt.executeUpdate(query);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else{
				System.out.println("Trying to add a Report that already exists");
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
		
		public int getReportOccurence(int quizID){
			if(isReportExist(quizID)){
				String command = "SELECT * FROM Reported WHERE quizID = "+ quizID + ";";
				try {
					ResultSet rs = stmnt.executeQuery(command);
					rs.next();
					int occur = rs.getInt("occurence");
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
					int occur = rs.getInt("occurence");
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
		public void incrementOccurence(int quizID){
			if(isReportExist(quizID)){
				try {
					int currentOccur = getReportOccurence(quizID);
					int newOccur = currentOccur++;
					String command = "UPDATE Reported SET occurence = "+newOccur+" WHERE quizID = "+quizID+";";
					stmnt.executeUpdate(command);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
		
}