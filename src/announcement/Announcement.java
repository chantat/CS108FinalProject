package announcement;

import java.sql.*;

public class Announcement {
	private String adminId;
	private String subject;
	private String text;
	private int announcementID;
	private Timestamp postTime;
	
	public Announcement(String adminId, String subject, String text,int announcementID, Timestamp postTime) {
		this.adminId = adminId;
		this.subject = subject;
		this.text = text;
		this.postTime = postTime;
		this.announcementID=announcementID;
	}
	
	public int getAnnouncementID(){
		return announcementID;
	}
	
	public String getAnnouncementText() {
		return text;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public String getAdminId() {
		return adminId;
	}
	
	public Timestamp getPostTime() {
		return postTime;
	}
}
