package mail;

import java.sql.Timestamp;

public class RequestMessage extends Message {
	
	public RequestMessage(String toID, String fromID, String subject, String message, Timestamp time, int status) {
		super(toID, fromID, subject, message, time, status, "Request");
	}
	
	public RequestMessage(String toID, String fromID) {
		super(toID, fromID, "Friend Request", fromID + " wants to be your friend!", "Request");
	}
}
