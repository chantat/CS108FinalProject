package mail;

import java.sql.*;
import java.util.*;

import mail.Message;

public class MailSystem {
	private DBConnection dbc;
	private String sqlStr;

	public MailSystem() {
		dbc = new DBConnection();
	}
	
	public void send(Message msg) {
		sqlStr = "INSERT INTO testMail(toID,fromID,subject,text,isRead) VALUES (";
		sqlStr += "\"" + msg.getToID() + "\",";
		sqlStr += "\"" + msg.getFromID() + "\",";
		sqlStr += "\"" + msg.getSubject() + "\",";
		sqlStr += "\"" + msg.getMessage() + "\",";
		sqlStr += msg.getIsRead();
		sqlStr += ");";
		//System.out.println(sqlStr);
		try {
			dbc.stmt.executeUpdate(sqlStr);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Message findMessage(String fromID, String timeStr) {
		Message msg = null;
		String toID, subject, message;
		Timestamp time;
		Boolean isRead;
		sqlStr = "SELECT * FROM ";
		sqlStr += "testMail"; //TODO: Change to real table
		sqlStr += " WHERE fromID=\"";
		sqlStr += fromID;
		sqlStr += "\" AND msgTime=\"";
		sqlStr += timeStr + "\"";
		//System.out.println(sqlStr);
		try {
			dbc.rs = dbc.stmt.executeQuery(sqlStr);
			dbc.rs.next();
				toID = dbc.rs.getString("toID");
				subject = dbc.rs.getString("subject");
				message = dbc.rs.getString("text");
				time = dbc.rs.getTimestamp("msgTime");
				isRead = dbc.rs.getBoolean("isRead");
				msg = new Message(toID, fromID, subject, message, time, isRead);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return msg;
	}

	// Inner class so that it has access to dbc
	public class Mailbox {
		String user, sqlStr;
		List<Message> inbox, outbox;

		public Mailbox(String userID) {
			user = userID;
			inbox = new ArrayList<Message>();
			outbox = new ArrayList<Message>();
		}

		public List<Message> loadInbox() {
			inbox.clear();
			String fromID, subject, message;
			Timestamp time;
			Boolean isRead;
			sqlStr = "SELECT * from ";
			sqlStr += "testMail "; //TODO: Change to real table
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
			return inbox;
		}

		public void loadOutbox() {
			outbox.clear();
			String toID, subject, message;
			Timestamp time;
			Boolean isRead;
			sqlStr = "SELECT * from ";
			sqlStr += "testMail "; //TODO: Change to real table
			sqlStr += "WHERE fromID = \"";
			sqlStr += user;
			sqlStr += "\"";
			try {
				dbc.rs = dbc.stmt.executeQuery(sqlStr);
				while (dbc.rs.next()) {
					toID = dbc.rs.getString("toID");
					subject = dbc.rs.getString("subject");
					message = dbc.rs.getString("text");
					time = dbc.rs.getTimestamp("msgTime");
					isRead = dbc.rs.getBoolean("isRead");
					outbox.add(new Message(toID, user, subject, message, time, isRead));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		public void printMessages() {
			System.out.println("----------Inbox----------");
			for (int i = 0; i < inbox.size(); i++) {
				System.out.println(inbox.get(i));
			}
			System.out.println("----------Outbox----------");
			for (int i = 0; i < outbox.size(); i++) {
				System.out.println(outbox.get(i));
			}
		}
		
		
	}
	
	public static void main(String[] args) {
		MailSystem ms = new MailSystem();
		String username = "ryan";
		Mailbox mb = ms.new Mailbox(username);
		System.out.println("Starting...");
		mb.loadInbox();
		mb.loadOutbox();
		mb.printMessages();
		System.out.println("...Finished");
		Message msg = new Message("maria",username,"Hello","This is my message to Maria");
		ms.send(msg);
		mb.loadOutbox();
		mb.printMessages();
	}
}
