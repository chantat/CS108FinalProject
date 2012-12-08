package userPackage;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;

import achievement.Achievement;
import achievement.AchievementManager;

import webpackage.DBConnection;
import quiz.*;

public class FriendManager{
	static Statement stmnt;
	static String tableName = "Friend";
	static String requestTableName = "Request";
	String Friend1ColumnName;
	String Friend2ColumnName;
	String Request1ColumnName;
	String Request2ColumnName;
	
	String quote = "\"";
	String comma = ",";
	ResultSet testRS;
	ResultSetMetaData testRSMD;
	DBConnection connect;
	
	
	
	
	public FriendManager(DBConnection con) {
		stmnt = con.getStatement();
		connect = con;
		
		
		try {
			testRS = stmnt.executeQuery("SELECT * FROM "+tableName);   //get the column names from Friend table
			testRSMD = testRS.getMetaData();
			Friend1ColumnName = testRSMD.getColumnName(1);
			Friend2ColumnName = testRSMD.getColumnName(2);
			
			testRS = stmnt.executeQuery("SELECT * FROM "+requestTableName);  //get the column names from Requests table
			testRSMD = testRS.getMetaData();
			Request1ColumnName = testRSMD.getColumnName(1);
			Request2ColumnName = testRSMD.getColumnName(2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	
	public boolean areFriends(String person1,String person2){
		String command = "SELECT * FROM "+tableName+ " WHERE "+Friend1ColumnName+" = "+quote+person1+quote+ " AND "+Friend2ColumnName+ " = "+quote+person2+quote+";";				
		try {
			testRS = stmnt.executeQuery(command);
			if(testRS.next()){
					return true;   //if the search returned a result (should only have 1 row) then they are indeed friends
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return false;  
		
	}
	
	public boolean requestSent(String user,String victim){
		String command = "SELECT * FROM "+requestTableName+ " WHERE "+Request1ColumnName+" = "+quote+user+quote+ " AND "+Request2ColumnName+ " = "+quote+victim+quote+";";	
		try {
			testRS = stmnt.executeQuery(command);
			if(testRS.next()){
					return true;   //if the search returned a result (should only have 1 row) then they are indeed friends
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return false;  
		
		
		
	}
	
	void addFriend(String person1,String person2){
		try {
			
			
			//update friend table
			stmnt.executeUpdate("INSERT INTO "+tableName+" VALUES(\""+person1+"\",\""+person2+"\")");
			stmnt.executeUpdate("INSERT INTO "+tableName+" VALUES(\""+person2+"\",\""+person1+"\")");
			String removeRequest1 = "DELETE FROM "+requestTableName+" WHERE "+Request1ColumnName+ " = "+ quote+person1+quote + "AND "+Request2ColumnName+ " = "+ quote+person2+quote +";";
			String removeRequest2 = "DELETE FROM "+requestTableName+" WHERE "+Request1ColumnName+ " = "+ quote+person2+quote + "AND "+Request2ColumnName+ " = "+ quote+person1+quote +";";
			
			//delete associated requests
			stmnt.executeUpdate(removeRequest1);
			stmnt.executeUpdate(removeRequest2);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	void removeFriend(String person1,String person2){
		try {
			
			String removeFriend1 = "DELETE FROM "+tableName+" WHERE "+Friend1ColumnName+ " = "+ quote+person1+quote + "AND "+Friend2ColumnName+ " = "+ quote+person2+quote +";";
			String removeFriend2 = "DELETE FROM "+tableName+" WHERE "+Friend1ColumnName+ " = "+ quote+person2+quote + "AND "+Friend2ColumnName+ " = "+ quote+person1+quote +";";
			stmnt.executeUpdate(removeFriend1);
			stmnt.executeUpdate(removeFriend2);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	void requestFriend(String requestor,String receiver){
		try {
			stmnt.executeUpdate("INSERT INTO "+requestTableName+"(fromID,toID) VALUES(\""+requestor+"\",\""+receiver+"\")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	public static ArrayList<String> getFriends(String user){
		ArrayList<String>friends = new ArrayList<String>();
		String quote = "\"";
		//get result set of all rows of people who are your friend
		String command = "SELECT * FROM "+tableName+" WHERE userID1 = "+quote+user+quote+";";
		try {
			ResultSet testRS = stmnt.executeQuery(command);
			while(testRS.next()){
				friends.add((String)testRS.getObject("userID2"));  //add the friend's name to the list		
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return friends;  //returns an empty list if no friends are found :(
	
	}
	
	public static ArrayList<String> getRequests(String user){
		ArrayList<String>requests = new ArrayList<String>();
		
		//get result set of all rows where someone has requested YOU to be their friend
		String quote = "\"";
		String command = "SELECT * FROM Request WHERE toID = "+quote+user+quote+";";

//TEST
	System.out.println("getRequest command is "+command);	
		
		try {
			
			ResultSet RS = stmnt.executeQuery(command);
			while(RS.next()){
				requests.add((String)RS.getObject("fromID"));  //add the requestor's name to the list		
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return requests;  //returns an empty list if no friends are found :(
	
	}
	
	public void swapAttempts(ArrayList<Attempt> attemptList, int prim, int sec){
		Attempt tempPrim = attemptList.get(prim);
		Attempt tempSec = attemptList.get(sec);
		attemptList.remove(prim);
		attemptList.add(prim,tempSec);
		attemptList.remove(sec);
		attemptList.add(sec,tempPrim);
		
	}
	
	public ArrayList<Attempt> sortAttemptsByTime(ArrayList<Attempt> attemptList){
		for(int primary=0;primary < attemptList.size()-1;primary++){
			for(int secondary=primary+1;secondary<attemptList.size();secondary++){
				if(attemptList.get(primary).getTimeTaken().before(attemptList.get(secondary).getTimeTaken()) ){
					swapAttempts(attemptList,primary,secondary);
				}
				
			}
			
		}
		return attemptList;
		
	}
	
	public ArrayList<Attempt> getFriendRecentAttempts(String user){
		AttemptManager attemptMGR= new AttemptManager(connect);
		ArrayList<String> friendList = getFriends(user);
		ArrayList<Attempt> recentAttempts = new ArrayList<Attempt>();
		//get most recent attempt from each friend
		for(int i=0;i<friendList.size();i++){   //for each friend
			Attempt[] friendAttempts = attemptMGR.getAllAttempts(friendList.get(i));
			if(friendAttempts.length!=0){ 
				recentAttempts.add(friendAttempts[0]);
			}
		}
		recentAttempts = sortAttemptsByTime(recentAttempts);
		return recentAttempts;
	
	}
	
	public ArrayList<Achievement> getFriendRecentAchievements(String user){
		ArrayList<Achievement> recentAchieve = new ArrayList<Achievement>();
		ArrayList<String> friendList = getFriends(user);
		AccountUtil acctUtil = new AccountUtil(connect);
		AchievementManager achieveMGR = new AchievementManager(connect, acctUtil);
		for(int i=0;i<friendList.size();i++){   //for each friend
			ArrayList<Achievement> friendAchieveList = achieveMGR.getAllTimedAchievement(friendList.get(i));
			ArrayList<Achievement> sortedTimefriendAchieveList = achieveMGR.sortAchievementByTime(friendAchieveList);
			if(friendAchieveList.size()!=0){  //check that this friend has some achievements
				recentAchieve.add(friendAchieveList.get(0));
			}
		}
		recentAchieve = achieveMGR.sortAchievementByTime(recentAchieve);
		return recentAchieve;
		
	}
	
	
	public void dumpFriendTable(){
		String tableName = "Friend";
		ResultSet rs;
		int rows=0;
		String command = "SELECT * FROM "+tableName;
		try {
			rs = stmnt.executeQuery(command);
			rs.last();   //move to last row to get count of total number of rows
			rows = rs.getRow();   //note the row #s start at 1, not 0, for ResultSets
			rs.beforeFirst();
		
			for(int i=1;i<=rows;i++){
				rs.next();
				String friend1 = (String)rs.getObject(1);
			    String friend2 = (String)rs.getObject(2);
			   
			    System.out.println("friend 1 is" +  friend1);
			    System.out.println("friend 2 is" +  friend2);
	
			}
		

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	public void dumpRequestTable(){
		String tableName = "FriendRequest";
		ResultSet rs;
		int rows=0;
		String command = "SELECT * FROM "+tableName;
		try {
			rs = stmnt.executeQuery(command);
			rs.last();   //move to last row to get count of total number of rows
			rows = rs.getRow();   //note the row #s start at 1, not 0, for ResultSets
			rs.beforeFirst();
		
			for(int i=1;i<=rows;i++){
				rs.next();
				String friend1 = (String)rs.getObject(1);
			    String friend2 = (String)rs.getObject(2);
			   
			    System.out.println("Request originator is" +  friend1);
			    System.out.println("Request recipient is" +  friend2);
	
			}
		

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
		
}