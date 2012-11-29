package mail;

import java.sql.*;
import java.util.*;

import mail.Message;
import webpackage.DBConnection;

public class MailSystem {
	private DBConnection dbc;
	private Statement stmt;
	private ResultSet rs;
	private String sqlStr;

	public MailSystem(DBConnection con) {
		dbc = con;
		stmt = dbc.getStatement();
	}
	
	public void send(Message msg) {
		sqlStr = "INSERT INTO Message(toID,fromID,subject,messageText,status) VALUES (";
		sqlStr += "\"" + msg.getToID() + "\",";
		sqlStr += "\"" + msg.getFromID() + "\",";
		sqlStr += "\"" + msg.getSubject() + "\",";
		sqlStr += "\"" + msg.getMessage() + "\",";
		sqlStr += msg.getStatus();
		sqlStr += ");";
		//System.out.println(sqlStr);
		try {
			stmt.executeUpdate(sqlStr);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void markAsRead(Message msg) {
		sqlStr = "UPDATE Message SET status=1 WHERE ";
		sqlStr += "fromID=\"";
		sqlStr += msg.getFromID();
		sqlStr += "\" AND messageTime=\"";
		sqlStr += msg.getTime() + "\"";
		try {
			stmt.executeUpdate(sqlStr);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Message findMessage(String fromID, String timeStr) {
		Message msg = null;
		String toID, subject, message;
		Timestamp time;
		int status;
		sqlStr = "SELECT * FROM ";
		sqlStr += "Message";
		sqlStr += " WHERE fromID=\"";
		sqlStr += fromID;
		sqlStr += "\" AND messageTime=\"";
		sqlStr += timeStr + "\"";
		//System.out.println(sqlStr);
		try {
			rs = stmt.executeQuery(sqlStr);
			rs.next();
				toID = rs.getString("toID");
				subject = rs.getString("subject");
				message = rs.getString("messageText");
				time = rs.getTimestamp("messageTime");
				status = rs.getInt("status");
				msg = new Message(toID, fromID, subject, message, time, status);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	public List<Message> getInboxForUser(String user) {
		List<Message> inbox = new ArrayList<Message>();
		String fromID, subject, message;
		Timestamp time;
		int status;
		sqlStr = "SELECT * from ";
		sqlStr += "Message ";
		sqlStr += "WHERE toID = \"";
		sqlStr += user;
		sqlStr += "\"";
		try {
			rs = stmt.executeQuery(sqlStr);
			while (rs.next()) {
				fromID = rs.getString("fromID");
				subject = rs.getString("subject");
				message = rs.getString("messageText");
				time = rs.getTimestamp("messageTime");
				status = rs.getInt("status");
				inbox.add(new Message(user, fromID, subject, message, time, status));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return inbox;
	}
	
	public List<Request> getRequestsForUser(String user) {
		List<Request> requests = new ArrayList<Request>();
		String fromID;
		Timestamp time;
		int status;
		sqlStr = "SELECT * from ";
		sqlStr += "Request ";
		sqlStr += "WHERE toID = \"";
		sqlStr += user;
		sqlStr += "\"";
		try {
			rs = stmt.executeQuery(sqlStr);
			while (rs.next()) {
				fromID = rs.getString("fromID");
				time = rs.getTimestamp("requestTime");
				status = rs.getInt("status");
				requests.add(new Request(user, fromID, status, time));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return requests;
	}
	
	public List<Challenge> getChallengesForUser(String user) {
		List<Challenge> challenges = new ArrayList<Challenge>();
		int quizID;
		String fromID;
		Timestamp time;
		int status;
		sqlStr = "SELECT * from ";
		sqlStr += "Challenge ";
		sqlStr += "WHERE toID = \"";
		sqlStr += user;
		sqlStr += "\"";
		try {
			rs = stmt.executeQuery(sqlStr);
			while (rs.next()) {
				quizID = rs.getInt("quizID");
				fromID = rs.getString("fromID");
				time = rs.getTimestamp("challengeTime");
				status = rs.getInt("status");
				challenges.add(new Challenge(quizID, user, fromID, status, time));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return challenges;
	}
	
	

//	// Inner class so that it has access to dbc
//	public class Mailbox {
//		String user, sqlStr;
//		List<Message> inbox, outbox;
//
//		public Mailbox(String userID) {
//			user = userID;
//			inbox = new ArrayList<Message>();
//			outbox = new ArrayList<Message>();
//		}
//		
//		public void loadInbox() {
//			inbox = getInboxForUser(user);
//		}
//
//		
//		
//		// Working copy of loadInbox()
////		public List<Message> loadInbox() {
////			inbox.clear();
////			String fromID, subject, message;
////			Timestamp time;
////			int status;
////			sqlStr = "SELECT * from ";
////			sqlStr += "Message ";
////			sqlStr += "WHERE toID = \"";
////			sqlStr += user;
////			sqlStr += "\"";
////			try {
////				rs = stmt.executeQuery(sqlStr);
////				while (rs.next()) {
////					fromID = rs.getString("fromID");
////					subject = rs.getString("subject");
////					message = rs.getString("messageText");
////					time = rs.getTimestamp("messageTime");
////					status = rs.getInt("status");
////					inbox.add(new Message(user, fromID, subject, message, time, status));
////				}
////			} catch (SQLException e) {
////				e.printStackTrace();
////			}
////			return inbox;
////		}
//
//		public void loadOutbox() {
//			outbox.clear();
//			String toID, subject, message;
//			Timestamp time;
//			int status;
//			sqlStr = "SELECT * from ";
//			sqlStr += "Message ";
//			sqlStr += "WHERE fromID = \"";
//			sqlStr += user;
//			sqlStr += "\"";
//			try {
//				rs = stmt.executeQuery(sqlStr);
//				while (rs.next()) {
//					toID = rs.getString("toID");
//					subject = rs.getString("subject");
//					message = rs.getString("messageText");
//					time = rs.getTimestamp("messageTime");
//					status = rs.getInt("status");
//					outbox.add(new Message(toID, user, subject, message, time, status));
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//
//		public void printMessages() {
//			System.out.println("----------Inbox----------");
//			for (int i = 0; i < inbox.size(); i++) {
//				System.out.println(inbox.get(i));
//			}
//			System.out.println("----------Outbox----------");
//			for (int i = 0; i < outbox.size(); i++) {
//				System.out.println(outbox.get(i));
//			}
//		}
//	}
}
