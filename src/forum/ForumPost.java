package forum;

import java.sql.Timestamp;

public class ForumPost {

	private String userID;
	private int threadID;
	private String postText;
	private Timestamp timePosted;
	
	public ForumPost(String userID, int threadID, String postText, Timestamp timePosted){
		this.userID=userID;
		this.threadID=threadID;
		this.postText=postText;
		this.timePosted=timePosted;
	}
	
	public ForumPost(String userID, int threadID, String postText){
		this.userID=userID;
		this.threadID=threadID;
		this.postText=postText;
		this.timePosted=null;
	}
	
	public String getUserID(){
		return userID;
	}
	
	public int getThreadID(){
		return threadID;
	}
	
	public String getPostText(){
		return postText;
	}
	
	public Timestamp getTimePosted(){
		return timePosted;
	}
}
