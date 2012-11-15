package webpackage;
import java.util.*;
import java.security.*;
import java.sql.*;


public class AccountManager{
	Statement stmnt;
	String tableName;
	String adminColumnName;
	String userColumnName;
	String passColumnName;
	String publicPerfColumnName;
	ResultSet testRS;
	ResultSetMetaData testRSMD;
	public AccountManager(LoginConnectionSetup login) {
		stmnt = login.getStatement();
		tableName = login.getTableName();
		try {
			testRS = stmnt.executeQuery("SELECT * FROM "+tableName+"\"");
			testRSMD = testRS.getMetaData();
			userColumnName = testRSMD.getColumnName(0);
			passColumnName = testRSMD.getColumnName(1);
			adminColumnName = testRSMD.getColumnName(2);
			publicPerfColumnName = testRSMD.getColumnName(3);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addAccount(String name,String password){	 //error checking for existing account should be done by caller
		String hash = Generate(password);	
		try {
			stmnt.executeUpdate("INSERT INTO "+tableName+" VALUES(\""+name+"\",\""+hash+"\",0,0)");  //username,passhash,isAdminflag,publicPerformace flag...
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getPassHash(String name){
		ResultSet rs;
		ResultSetMetaData rsmd;
		String storedPassHash;
		
		try {
			rs = stmnt.executeQuery("SELECT * FROM "+tableName+" WHERE username = \""+name+"\";");
			rs.next();
			storedPassHash = rs.getString("passwordHash");
			assert(!storedPassHash.equals(""));   //make sure we don't have empty String returned...
			return storedPassHash;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";     //technically should never get here unless there's a problem...
	}
	public boolean containsAccount(String name){
		try {
			ResultSet rs;
			ResultSetMetaData rsmd;
			int rows;
			rs = stmnt.executeQuery("SELECT * FROM "+tableName+" WHERE "+userColumnName+" = \""+name+"\";");
			rsmd = rs.getMetaData();
			rs.last();   //move to last row to get count of total number of rows
			rows = rs.getRow();   //note the row #s start at 1, not 0, for ResultSets
			assert(rows<=1);    //check if rows exceed 1 (means we have duplicate entries)
			return (rows==1);    //return true if we have exactly 1 row entry that has this username
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean passwordMatch(String name, String password){
		if(!this.containsAccount(name)) return false;    //first check to see if the name even exists
		String hash = Generate(password);   //hash of the candidate password
		return(getPassHash(name).equals(hash));  //do a comparison with database's hash 
	}
	
	
	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	// possible test values:
		// a 86f7e437faa5a7fce15d1ddcb9eaeaea377667b8
		// fm adeb6f2a18fe33af368d91b09587b68e3abcb9a7
		// a! 34800e15707fae815d7c90d49de44aca97e2d759
		// xyz 66b27417d37e024c46526c2f6d358a754fc552f3
		
	public static String Generate(String password){
		MessageDigest md= null;
		try{md = MessageDigest.getInstance("SHA");}
		catch(NoSuchAlgorithmException e){ e.printStackTrace();}
		byte[] bytePassword = password.getBytes();
		md.update(bytePassword);
		byte[] rawHash = md.digest();
		String readableHash = hexToString(rawHash);	
		return readableHash;
	}

	public void promoteAdmin(String name){   //sets admin flag in user's database entry to 1
		try {
			stmnt.executeUpdate("UPDATE "+tableName+" SET "+adminColumnName+" = 1 WHERE "+userColumnName+" = \""+name+"\"");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void demoteAdmin(String name){   //sets admin flag in user's database entry to 1
		try {
			stmnt.executeUpdate("UPDATE "+tableName+" SET "+adminColumnName+" = 0 WHERE "+userColumnName+" = \""+name+"\"");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
