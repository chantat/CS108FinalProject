package userPackage;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import webpackage.DBConnection;

public class FriendManager{
	Statement stmnt;
	String tableName = "Friend";
	String requestTableName = "FriendRequest";
	String Friend1ColumnName;
	String Friend2ColumnName;
	String Request1ColumnName;
	String Request2ColumnName;
	
	String quote = "\"";
	String comma = ",";
	ResultSet testRS;
	ResultSetMetaData testRSMD;
	public FriendManager(DBConnection con) {
		stmnt = con.getStatement();
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
			stmnt.executeUpdate("INSERT INTO "+requestTableName+" VALUES(\""+requestor+"\",\""+receiver+"\")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	public ArrayList<String> getFriends(String user){
		ArrayList<String>friends = new ArrayList<String>();
		
		//get result set of all rows of people who are your friend
		String command = "SELECT * FROM "+tableName+" WHERE "+Friend1ColumnName+" = "+quote+user+quote+";";
		try {
			testRS = stmnt.executeQuery(command);
			while(testRS.next()){
				friends.add((String)testRS.getObject(Friend2ColumnName));  //add the friend's name to the list		
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return friends;  //returns an empty list if no friends are found :(
	
	}
	
	public ArrayList<String> getRequests(String user){
		ArrayList<String>requests = new ArrayList<String>();
		
		//get result set of all rows where someone has requested YOU to be their friend
		String quote = "\"";
		String command = "SELECT * FROM "+requestTableName+" WHERE "+Request2ColumnName+" = "+quote+user+quote+";";

//TEST
	System.out.println("getRequest command is "+command);	
		
		try {
			testRS = stmnt.executeQuery(command);
			while(testRS.next()){
				requests.add((String)testRS.getObject(Request1ColumnName));  //add the requestor's name to the list		
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return requests;  //returns an empty list if no friends are found :(
	
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