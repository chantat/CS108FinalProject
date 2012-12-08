package announcement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.*;

import forum.ForumPost;

import webpackage.DBConnection;

public class CommentManager {

	private Statement stmnt;
	
	public CommentManager(DBConnection con) {
		stmnt = con.getStatement();		
	}
	
	public void insertCommentIntoDatabase(int announcementID, String commentText, String username, Timestamp timeCommented){
		String command = "INSERT INTO Comment VALUES (";
		command += "" + announcementID + ",";
		command += "\"" + commentText + "\",";
		command += "\"" + username + "\",";
		command += "'" + timeCommented + "');";
		//System.out.println(command); // for verification purposes
		
		try {
			stmnt.executeUpdate(command);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Comment> getAnnouncementComments(int announcementID){
		ArrayList<Comment> sortedAnnouncementComments=new ArrayList<Comment>();
		
		String command = "SELECT * FROM Comment ";
		command += "WHERE announcementID = " + announcementID + " ORDER BY commentTime ASC;";
		System.out.println(command); //TODO remove, for verification purposes
		try {
			ResultSet rs = stmnt.executeQuery(command);
			int numComments = DBConnection.getResultSetSize(rs);
			if(numComments != 0) rs.first();
			for(int i = 0; i < numComments; i++){
				String commentText = rs.getString("commentText");
				String userID = rs.getString("userID");
				Timestamp commentTime = rs.getTimestamp("commentTime");
				Comment comment =new Comment(announcementID, commentText, userID, commentTime);
				sortedAnnouncementComments.add(comment);
				System.out.println("retrieved from comment table: " + announcementID + " " + commentText + " " + userID + " " + commentTime); // TODO remore, for verification purposes
				rs.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return sortedAnnouncementComments;
	}
	
}
