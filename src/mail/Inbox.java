package mail;

import java.sql.*;
import java.util.*;

import mail.DBConnection;
import mail.Message;

/* Necessary functionality
 * Get all messages to a user
 * Get all messages from a user
 * Find a message with a subject (?)
 * 
 */

public class Inbox {
	String user, sqlStr;
	DBConnection dbc;
	List<Message> inbox;
	
	public Inbox(String userID) {
		user = userID;
		dbc = new DBConnection(); //Remove if Inbox is made inner class of MailSystem
		inbox = new ArrayList<Message>();
	}
	
	public List<Message> getMessages() {
		List<Message> messages = new ArrayList<Message>();
		String fromID, subject, message;
		Timestamp time;
		Boolean isRead;
		sqlStr = "SELECT * from ";
		sqlStr += "testMail "; //Change to real table
		sqlStr += "WHERE toID = \"";
		sqlStr += user;
		sqlStr += "\"";
		try {
			dbc.rs = dbc.stmt.executeQuery(sqlStr);
			while (dbc.rs.next()) {
				fromID = dbc.rs.getString("fromID");
				subject = dbc.rs.getString("subject");
				message = dbc.rs.getString("text");
				time = dbc.rs.getTimestamp("msgTime");
				isRead = dbc.rs.getBoolean("isRead");
				inbox.add(new Message(user, fromID, subject, message, time, isRead));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return messages;
	}
	
	public void printMessages() {
		for (int i = 0; i < inbox.size(); i++) {
			System.out.println(inbox.get(i));
		}
	}
	
	
	public static void main(String[] args) {
		String username = "ryan";
		Inbox in = new Inbox(username);
		System.out.println("Starting...");
		in.getMessages();
		in.printMessages();
		System.out.println("...Finished");
	}
}
