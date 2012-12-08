package mail;

import java.sql.*;
import java.util.*;

import mail.Message;
import webpackage.DBConnection;

public class MailSystem {
	private static DBConnection dbc;

	public MailSystem(DBConnection con) {
		dbc = con;
	}
	
	public void send(Message msg) {
		Statement stmt = dbc.getStatement();
		String sqlStr = "INSERT INTO Message(toID,fromID,subject,messageText,status,msgType) VALUES (";
		sqlStr += "\"" + msg.getToID() + "\",";
		sqlStr += "\"" + msg.getFromID() + "\",";
		sqlStr += "\"" + msg.getSubject() + "\",";
		sqlStr += "\"" + msg.getMessage() + "\",";
		sqlStr += msg.getStatus() + ",";
		sqlStr += "\"" + msg.getType() + "\"";
		sqlStr += ");";
		//System.out.println(sqlStr);
		try {
			stmt.executeUpdate(sqlStr);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(String toID, String fromID, String timeStr) {
		Statement stmt = dbc.getStatement();
		String sqlStr = "DELETE FROM Message WHERE ";
		sqlStr += "toID=\"";
		sqlStr += toID;
		sqlStr += "\" AND fromID=\"";
		sqlStr += fromID;
		sqlStr += "\" AND messageTime=\"";
		sqlStr += timeStr + "\"";
		//System.out.println(sqlStr);
		try {
			stmt.executeUpdate(sqlStr);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void send(ChallengeMessage msg) {
		Statement stmt = dbc.getStatement();
		String sqlStr = "INSERT INTO Message(toID,fromID,subject,messageText,status,msgType) VALUES (";
		sqlStr += "\"" + msg.getToID() + "\",";
		sqlStr += "\"" + msg.getFromID() + "\",";
		sqlStr += "\"" + msg.getSubject() + "\",";
		sqlStr += "\"" + msg.getMessage() + "\",";
		sqlStr += msg.getStatus() + ",";
		sqlStr += "\"" + msg.getType() + "\"";
		sqlStr += ");";
		//System.out.println(sqlStr);
		try {
			stmt.executeUpdate(sqlStr);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sqlStr = "INSERT INTO Challenge(toID,fromID,status,quizID,score) VALUES (";
		sqlStr += "\"" + msg.getToID() + "\",";
		sqlStr += "\"" + msg.getFromID() + "\",";
		sqlStr += msg.getStatus() + ",";
		sqlStr += "\"" + msg.getQuizID() + "\",";
		sqlStr += "\"" + msg.getScore() + "\"";
		sqlStr += ")";
		try {
			stmt.executeUpdate(sqlStr);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void markAsRead(Message msg) {
		Statement stmt = dbc.getStatement();
		String sqlStr = "UPDATE Message SET status=1 WHERE ";
		sqlStr += "toID=\"";
		sqlStr += msg.getToID();
		sqlStr += "\" AND fromID=\"";
		sqlStr += msg.getFromID();
		sqlStr += "\" AND messageTime=\"";
		sqlStr += msg.getTime() + "\"";
		//System.out.println(sqlStr);
		try {
			stmt.executeUpdate(sqlStr);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public  int numChallengesSent(String fromID){
		Statement stmt = dbc.getStatement();
		ResultSet rs;
		String quote = "\"";
		String command = "SELECT * FROM Challenge WHERE fromID = "+quote+fromID+quote+";";
		try {
			rs = stmt.executeQuery(command);
			return DBConnection.getResultSetSize(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public  int numMessageSent(String fromID){
		Statement stmt = dbc.getStatement();
		ResultSet rs;
		String quote = "\"";
		String command = "SELECT * FROM Message WHERE fromID = "+quote+fromID+quote+";";
		try {
			rs = stmt.executeQuery(command);
			return DBConnection.getResultSetSize(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public Message findMessage(String toID, String fromID, String timeStr) {
		Statement stmt = dbc.getStatement();
		ResultSet rs;
		Message msg = null;
		String subject, message, msgType;
		Timestamp time;
		int status;
		String sqlStr = "SELECT * FROM ";
		sqlStr += "Message";
		sqlStr += " WHERE fromID=\"";
		sqlStr += fromID;
		sqlStr += "\" AND toID=\"";
		sqlStr += toID;
		sqlStr += "\" AND messageTime=\"";
		sqlStr += timeStr + "\"";
		System.out.println(sqlStr);
		try {
			rs = stmt.executeQuery(sqlStr);
			rs.next();
				subject = rs.getString("subject");
				message = rs.getString("messageText");
				time = rs.getTimestamp("messageTime");
				status = rs.getInt("status");
				msgType = rs.getString("msgType");
				msg = new Message(toID, fromID, subject, message, time, status, msgType);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//System.out.println("findMessage: " + msg.getMessage());
		return msg;
	}
	
	public ChallengeMessage findChallenge(String toID, String fromID, String timeStr) {
		Statement stmt = dbc.getStatement();
		ResultSet rs;
		ChallengeMessage chlg = null;
		String subject = "";
		String message = "";
		Timestamp time = null;
		int status = -1;
		int quizID = -1;
		double score = -1;
		String sqlStr = "SELECT * FROM ";
		sqlStr += "Message";
		sqlStr += " WHERE fromID=\"";
		sqlStr += fromID;
		sqlStr += "\" AND toID=\"";
		sqlStr += toID;
		sqlStr += "\" AND messageTime=\"";
		sqlStr += timeStr + "\"";
		//System.out.println(sqlStr);
		try {
			rs = stmt.executeQuery(sqlStr);
			rs.next();
			subject = rs.getString("subject");
			message = rs.getString("messageText");
			time = rs.getTimestamp("messageTime");
			status = rs.getInt("status");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sqlStr = "SELECT * FROM ";
		sqlStr += "Challenge";
		sqlStr += " WHERE fromID=\"";
		sqlStr += fromID;
		sqlStr += "\" AND toID=\"";
		sqlStr += toID;
		sqlStr += "\" AND challengeTime=\"";
		sqlStr += timeStr + "\"";
		try {
			rs = stmt.executeQuery(sqlStr);
			rs.next();
			quizID = rs.getInt("quizID");
			score = rs.getDouble("score");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		chlg = new ChallengeMessage(toID, fromID, subject, message, time, status, quizID, score);
		return chlg;
	}
	
//	public int findChallengeQuizID(String fromID, String timeStr) {
//		int quizID = -1;
//		sqlStr = "SELECT * FROM ";
//		sqlStr += "Challenge";
//		sqlStr += " WHERE fromID=\"";
//		sqlStr += fromID;
//		sqlStr += "\" AND challengeTime=\"";
//		sqlStr += timeStr + "\"";
//		//System.out.println(sqlStr);
//		try {
//			rs = stmt.executeQuery(sqlStr);
//			rs.next();
//				quizID = rs.getInt("quizID");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return quizID;
//	}
//	
//	public double findChallengeScore(String fromID, String timeStr) {
//		double score = 0;
//		sqlStr = "SELECT * FROM ";
//		sqlStr += "Challenge";
//		sqlStr += " WHERE fromID=\"";
//		sqlStr += fromID;
//		sqlStr += "\" AND challengeTime=\"";
//		sqlStr += timeStr + "\"";
//		//System.out.println(sqlStr);
//		try {
//			rs = stmt.executeQuery(sqlStr);
//			rs.next();
//				score = rs.getDouble("score");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return score;
//	}
	
	public List<Message> getInboxForUser(String user) {
		Statement stmt = dbc.getStatement();
		ResultSet rs;
		List<Message> inbox = new ArrayList<Message>();
		String fromID, subject, message, msgType;
		Timestamp time;
		int status;
		String sqlStr = "SELECT * from ";
		sqlStr += "Message ";
		sqlStr += "WHERE toID = \"";
		sqlStr += user;
		sqlStr += "\" ORDER BY ";
		sqlStr += "messageTime DESC;";
		try {
			rs = stmt.executeQuery(sqlStr);
			while (rs.next()) {
				fromID = rs.getString("fromID");
				subject = rs.getString("subject");
				message = rs.getString("messageText");
				time = rs.getTimestamp("messageTime");
				status = rs.getInt("status");
				msgType = rs.getString("msgType");
				if (msgType.equals("Challenge")) {
					System.out.println("ADDING CHALLENGE TO INBOX");
					inbox.add(findChallenge(user, fromID, time.toString()));
//					int quizID = findChallengeQuizID(fromID, time.toString());
//					double score = findChallengeScore(fromID, time.toString());
//					inbox.add(new ChallengeMessage(user, fromID, subject, message, time, status, quizID, score));
				} else {
					System.out.println("ADDING MESSAGE TO INBOX");
					inbox.add(new Message(user, fromID, subject, message, time, status, msgType));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Inbox size: " + inbox.size());
		return inbox;
	}
	
	public int getUnreadForUser(String user) {
		Statement stmt = dbc.getStatement();
		ResultSet rs;
		int numUnread = 0;
		String sqlStr = "SELECT * from ";
		sqlStr += "Message ";
		sqlStr += "WHERE toID = \"";
		sqlStr += user;
		sqlStr += "\" AND status = 0";
		try {
			rs = stmt.executeQuery(sqlStr);
			while (rs.next()) {
				numUnread++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return numUnread;
	}
	
	public List<Request> getRequestsForUser(String user) {
		Statement stmt = dbc.getStatement();
		ResultSet rs;
		List<Request> requests = new ArrayList<Request>();
		String fromID;
		Timestamp time;
		int status;
		String sqlStr = "SELECT * from ";
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
		Statement stmt = dbc.getStatement();
		ResultSet rs;
		List<Challenge> challenges = new ArrayList<Challenge>();
		int quizID;
		String fromID;
		Timestamp time;
		int status;
		String sqlStr = "SELECT * from ";
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
