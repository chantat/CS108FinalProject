package announcement;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import webpackage.DBConnection;

public class AnnouncementManager {
	Statement stmnt;
	ResultSet rs;
	
	
	public AnnouncementManager(DBConnection con) {
		stmnt = con.getStatement();		
	}
	
	public void createAnnouncement(String adminId, String subject, String text) {
		String command = "INSERT INTO Announcement(adminID, announcementText, subject) VALUES (";
		command += "\"" + adminId + "\",";
		command += "\"" + text + "\",";
		command += "\"" + subject + "\");";
		
		try {
			stmnt.executeUpdate(command);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Announcement[] getAllAnnouncement() {
		String command = "SELECT * FROM Announcement;";
		
		Announcement ret[] = null;
		try {
			rs = stmnt.executeQuery(command);
			int numEntries = DBConnection.getResultSetSize(rs);
			
			ret = new Announcement[numEntries];
			rs.first();
			for (int i = 0; i < numEntries; i++) {
				String adminId = rs.getString("adminID");
				String text = rs.getString("announcementText");
				String subject = rs.getString("subject");
				ret[i] = new Announcement(adminId, subject, text);
				rs.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
}
