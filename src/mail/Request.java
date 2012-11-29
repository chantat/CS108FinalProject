package mail;

import java.sql.*;

public class Request {
	private String toID;
	private String fromID;
	private int status;
	private Timestamp time;
	
	public Request(String toID, String fromID, int status, Timestamp time) {
		this.toID = toID;
		this.fromID = fromID;
		this.status = status;
		this.time = time;
	}
	
	public Request(String toID, String fromID) {
		this.toID = toID;
		this.fromID = fromID;
		this.status = 0;
	}
	
	public String getToID() {
		return toID;
	}
	
	public String getFromID() {
		return fromID;
	}
	
	public int getStatus() {
		return status;
	}
	
	public Timestamp getTime() {
		return time;
	}

}
