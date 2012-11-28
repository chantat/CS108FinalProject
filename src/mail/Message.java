package mail;

import java.sql.*;

public class Message {
	private String toID;
	private String fromID;
	private String subject;
	private String message;
	private Timestamp time;
	private boolean isRead;
	
	public Message(String toID, String fromID, String subject, String message, Timestamp time, boolean isRead) {
		this.toID = toID;
		this.fromID = fromID;
		this.subject = subject;
		this.message = message;
		this.time = time;
		this.isRead = isRead;
	}
	
	/*
	 * Alternate constructor for sending a new message.
	 * mysql will set timestamp, and new messages are assumed to be unread.
	 */
	public Message(String toID, String fromID, String subject, String message) {
		this.toID = toID;
		this.fromID = fromID;
		this.subject = subject;
		this.message = message;
		this.isRead = false;
	}
	
	public String getToID() {
		return toID;
	}
	
	public String getFromID() {
		return fromID;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public String getMessage() {
		return message;
	}
	
	public Timestamp getTime() {
		return time;
	}
	
	public boolean getIsRead() {
		return isRead;
	}
	
	public void markAsRead() {
		isRead = true;
		//TODO: Connect to DB and mark
	}
	
	public String toString() {
		String str;
		str = "To: " + toID + "\n";
		str += "From: " + fromID + "\n";
		str += "Subject: " + subject + "\n";
		str += "Time: " + time + "\n";
		str += "Read? " + isRead + "\n";
		str += message;
		return str;
	}
}
