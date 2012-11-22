package userPackage;
import webpackage.*;
import java.util.*;
import java.security.*;
import java.sql.*;


public class AccountManager {
	Statement stmnt;
	String tableName = "QuizUser";
	String adminColumnName;
	String userColumnName;
	String passColumnName;
	String publicPerfColumnName;
	String deactColumnName;
	ResultSet testRS;
	ResultSetMetaData testRSMD;

	PasswordManager pm;

	public static final int USER = 0;
    public static final int PASS = 1;
    public static final int ADMIN = 2;
    public static final int PUBPERF = 3;
    public static final int DEACT = 4;



	public AccountManager(DBConnection con) {
		pm = new PasswordManager();
		
		stmnt = con.getStatement();
		try {
			testRS = stmnt.executeQuery("SELECT * FROM "+tableName+"\"");
			testRSMD = testRS.getMetaData();
			userColumnName = testRSMD.getColumnName(USER);
			passColumnName = testRSMD.getColumnName(PASS);
			adminColumnName = testRSMD.getColumnName(ADMIN);
			publicPerfColumnName = testRSMD.getColumnName(PUBPERF);
			deactColumnName = testRSMD.getColumnName(DEACT);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addAccount(String username, String password){	 //error checking for existing account should be done by caller
		String salt = pm.generateSalt();
		String hashedPassword = pm.generateHexStringFromString(password + salt);	
		
		String command = "INSERT INTO "+tableName+" VALUES(\"" + username + "\",\"" + hashedPassword + "\",\"" + salt + "\",0,0,0);";
		try {
			stmnt.executeUpdate(command);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteAccount(String username){
		String command = "UPDATE "+tableName+" SET "+deactColumnName+" = 1 WHERE "+userColumnName+" = \""+username+"\"";
		try {
			stmnt.executeUpdate(command);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getPassHash(String name) {
		ResultSet rs;
		String storedPassHash = null;
		
		try {
			String command = "SELECT * FROM " + tableName + " WHERE username = \"" + name + "\";";
			rs = stmnt.executeQuery(command);
			rs.next();
			storedPassHash = rs.getString("password");
			assert(!storedPassHash.equals(""));   //make sure we don't have empty String returned...
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return storedPassHash;
	}
	
	public String getSalt(String name) {
		ResultSet rs;
		String salt = null;
		try {
			rs = stmnt.executeQuery("SELECT * FROM " + tableName + " WHERE username = \"" + name + "\";");
			rs.next();
			salt = rs.getString("salt");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return salt;
	}
	
	public boolean containsAccount(String name) {
		try {
			ResultSet rs;
			ResultSetMetaData rsmd;
			int rows;
			rs = stmnt.executeQuery("SELECT * FROM "+tableName+" WHERE "+userColumnName+" = \""+name+"\";");
			rsmd = rs.getMetaData();
			rs.last();   //move to last row to get count of total number of rows
			rows = rs.getRow();   //note the row #s start at 1, not 0, for ResultSets
			assert(rows<=1);    //check if rows exceed 1 (means we have duplicate entries)	
			return (rows==1);    //return true if we have exactly 1 row entry that has this username and not deactivated
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean isDeact(String name){
		try {
			ResultSet rs;
			rs = stmnt.executeQuery("SELECT * FROM "+tableName+" WHERE "+userColumnName+" = \""+name+"\";");
			rs.next();
			Integer deactFlag = (Integer)rs.getObject(DEACT);
			if(deactFlag==1){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void reactivate(String name){
		try {
			String command = "UPDATE "+tableName+" SET "+deactColumnName+" = 0 WHERE "+userColumnName+" = \""+name+"\";";
			stmnt.executeUpdate(command);
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
	public boolean passwordMatch(String username, String password){
		// first check to see if the name even exists
		if (!this.containsAccount(username)) {
			return false;
		}
		
		// get stored password hash
		String hashedPassword = getPassHash(username);
		
		// compute the input password hash
		String salt = getSalt(username);
		String hashedInputPassword = pm.generateHexStringFromString(password + salt);
		
		return hashedPassword.equals(hashedInputPassword); 
	}	

	public void promoteAdmin(String name){   //sets admin flag in user's database entry to 1
		String command = "UPDATE "+tableName+" SET "+adminColumnName+" = 1 WHERE "+userColumnName+" = \""+name+"\";";
		try {
			stmnt.executeUpdate(command);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void demoteAdmin(String name){   //sets admin flag in user's database entry to 1
		try {
			String command = "UPDATE "+tableName+" SET "+adminColumnName+" = 0 WHERE "+userColumnName+" = \""+name+"\";";
			stmnt.executeUpdate(command);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public class PasswordManager {
		private final char[] CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!".toCharArray();
		private final int SALT_LEN = 10;
		
		private Random rng;
		
		public PasswordManager() {
			rng = new Random();
		}
		
		public String generateSalt() {
			char[] salt = new char[SALT_LEN];
			for (int i = 0; i < SALT_LEN; i++) {
				salt[i] = CHARS[rng.nextInt(CHARS.length)];
			}
			return new String(salt);
		}
		
		public String generateHexStringFromString(String password) {
			MessageDigest md = null;
			try {
				md = MessageDigest.getInstance("SHA");
			} catch (NoSuchAlgorithmException e) { 
				e.printStackTrace();
			}
			
			byte[] bytePassword = password.getBytes();
			md.update(bytePassword);
			byte[] hash = md.digest();
				
			return hexToString(hash);
		}
		
		/*
		 Given a byte[] array, produces a hex String,
		 such as "234a6f". with 2 chars for each byte in the array.
		 (provided code)
		*/
		public String hexToString(byte[] bytes) {
			StringBuffer buff = new StringBuffer();
			for (int i=0; i<bytes.length; i++) {
				int val = bytes[i];
				val = val & 0xff;  // remove higher bits, sign
				if (val<16) buff.append('0'); // leading 0
				buff.append(Integer.toString(val, 16));
			}
			return buff.toString();
		}

	}
}
