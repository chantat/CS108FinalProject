package announcement;


import java.sql.*;

import webpackage.DBConnection;

public class AnnouncementManager {
	private Statement stmnt;
	private ResultSet rs;
	
	
	public AnnouncementManager(DBConnection con) {
		stmnt = con.getStatement();		
	}
	
	private int getCurrentAnnouncementID(){
		int currentAnnouncementID = 0;
		String query = "SELECT MAX(announcementID) FROM Announcement;";
		ResultSet rs = null;
		try{
			rs = stmnt.executeQuery(query);
			if (rs.next()) {
				  currentAnnouncementID = rs.getInt(1) + 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return currentAnnouncementID;
	}
	
	public void createAnnouncement(String adminId, String subject, String text, Timestamp timeAnnounced) {
		int announcementID=getCurrentAnnouncementID();
		String command = "INSERT INTO Announcement VALUES (";
		command += "\"" + adminId + "\",";
		command += "\"" + text + "\",";
		command += "\"" + subject + "\",";
		command += announcementID + ",";
		command += "'" + timeAnnounced + "');";
		
		try {
			stmnt.executeUpdate(command);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void createAnnouncement(String adminId, String subject, String text) {
		int announcementID=getCurrentAnnouncementID();
		Timestamp timeAnnounced=new Timestamp(System.currentTimeMillis());
		String command = "INSERT INTO Announcement VALUES (";
		command += "\"" + adminId + "\",";
		command += "\"" + text + "\",";
		command += "\"" + subject + "\",";
		command += announcementID + ",";
		command += "'" + timeAnnounced + "');";
		
		try {
			stmnt.executeUpdate(command);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Announcement[] getAllAnnouncement() {
		String command = "SELECT * FROM Announcement;";
		System.out.println(command); //TODO remove, just checking it's working
		
		Announcement ret[] = null;
		try {
			rs = stmnt.executeQuery(command);
			int numEntries = DBConnection.getResultSetSize(rs);
			
			ret = new Announcement[numEntries];
			if(numEntries!= 0)rs.first();
			for (int i = 0; i < numEntries; i++) {
				String adminId = rs.getString("adminID");
				String text = rs.getString("announcementText");
				String subject = rs.getString("subject");
				int announcementID = rs.getInt("announcementID");
				Timestamp postTime = rs.getTimestamp("announcementTime");
				ret[i] = new Announcement(adminId, subject, text, announcementID, postTime);
				rs.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
}
