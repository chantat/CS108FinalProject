package announcement;

import java.sql.Timestamp;

public class Comment {

	private int announcementID;
	private String commentText;
	private String username;
	private Timestamp timeCommented;
	
	public Comment(int announcementID, String commentText, String username, Timestamp timeCommented){
		this.announcementID=announcementID;
		this.commentText=commentText;
		this.username=username;
		this.timeCommented=timeCommented;
	}
	
	public int getAnnouncementID(){
		return announcementID;
	}
	
	public String getCommentText(){
		return commentText;
	}
	
	public String getUsername(){
		return username;
	}
	
	public Timestamp getTimeCommented(){
		return timeCommented;
	}
}
