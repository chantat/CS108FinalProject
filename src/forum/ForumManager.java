package forum;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.*;

import userPackage.AccountUtil;
import webpackage.DBConnection;

public class ForumManager {

	private Statement stmnt;
	
	public ForumManager(DBConnection connection) {
		stmnt = connection.getStatement();
	}
	
	public void createForumPost(String userID, int threadID, String postText, Timestamp timePosted){
		String command = "INSERT INTO Forum VALUES (";
		command += "\"" + userID + "\",";
		command += threadID + ",";
		command += "\"" + postText + "\",";
		command += "'" + timePosted + "');";
		//System.out.println(command); // for verification purposes
		
		try {
			stmnt.executeUpdate(command);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<ForumPost> getForumPosts(int quizID){
		ArrayList<ForumPost> forumPosts=new ArrayList<ForumPost>();
		
		String command = "SELECT * FROM Forum ";
		command += "WHERE quizID = \"" + quizID + "\" ORDER BY date ASC;";
		//System.out.println(command); //TODO remove, for verification purposes
		command += "WHERE quizID = \"" + quizID + "\" ORDER BY date DESC;";
		//System.out.println(command); //TODO remove, for verification purposes
		try {
			ResultSet rs = stmnt.executeQuery(command);
			int numPosts = DBConnection.getResultSetSize(rs);
			if(numPosts != 0) rs.first();
			for(int i = 0; i < numPosts; i++){
				String user = rs.getString("userID");
				String postText = rs.getString("postText");
				Timestamp datePosted = rs.getTimestamp("date");
				ForumPost currentPost=new ForumPost(user, quizID, postText, datePosted);
				forumPosts.add(currentPost);
				//System.out.println(user+ " " + postText + " " + datePosted + " " + currentPost);
				rs.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return forumPosts;
	}
	
}
