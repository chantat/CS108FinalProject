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
	String publicPageColumnName;
	String deactColumnName;
	ResultSet testRS;
	ResultSetMetaData testRSMD;

	PasswordManager pm;

	public static final int USER = 1;
    public static final int PASS = 2;
    public static final int SALT = 3;
    public static final int ADMIN = 4;
    public static final int PUBPERF = 5;
    public static final int PUBPAGE =6;
    public static final int DEACT = 7;
    



	public AccountManager(DBConnection con) {
		pm = new PasswordManager();
		
		stmnt = con.getStatement();
		try {
			String command = "SELECT * FROM "+tableName;
			testRS = stmnt.executeQuery(command);
			testRSMD = testRS.getMetaData();
			userColumnName = testRSMD.getColumnName(USER);
			passColumnName = testRSMD.getColumnName(PASS);
			adminColumnName = testRSMD.getColumnName(ADMIN);
			publicPerfColumnName = testRSMD.getColumnName(PUBPERF);
			publicPageColumnName = testRSMD.getColumnName(PUBPAGE);
			deactColumnName = testRSMD.getColumnName(DEACT);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void addAccount(String username, String password, int perfPriv, int userPriv){	 //error checking for existing account should be done by caller
		int isAdmin = 0;
		int isDeact = 0;
		String salt = pm.generateSalt();
		String hashedPassword = pm.generateHexStringFromString(password + salt);	
		
		String command1 = "INSERT INTO ";
		String command2 =" VALUES(";
		String qCq ="\",\"";
		String quote = "\"";
		String comma = ",";
		
		String combinedCommand1 = command1+tableName+command2+quote+username+qCq+hashedPassword+qCq+salt+quote;
		String combinedCommand2 = comma+isAdmin+comma+perfPriv+comma+userPriv+comma+isDeact+comma+"0"+comma+"0"+");";
		String command = combinedCommand1+combinedCommand2;
		
	//TEST
		System.out.println("account creation command is "+ command);
		
		try {
			stmnt.executeUpdate(command);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deactAccount(String username){
		String quote = "\"";
		String command = "UPDATE "+tableName+" SET "+deactColumnName+" = 1 WHERE "+userColumnName+" = "+quote+username+quote+";";
		try {
			stmnt.executeUpdate(command);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteAccount(String username){
		String quote = "\"";
		String command = "DELETE "+tableName+" WHERE "+userColumnName+" LIKE "+quote+username+quote+";";
		try {
			stmnt.executeUpdate(command);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteAllAccount(String username){
		String quote = "\"";
		String command = "DELETE "+tableName+" WHERE "+userColumnName+" = "+quote+"%"+quote+";";
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
			int rows;
			rs = stmnt.executeQuery("SELECT * FROM "+tableName+" WHERE "+userColumnName+" = \""+name+"\";");
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
	
	public boolean isPagePublic(String name){
		try {
			ResultSet rs;
			rs = stmnt.executeQuery("SELECT * FROM "+tableName+" WHERE "+userColumnName+" = \""+name+"\";");
			rs.next();
			Boolean pubFlag = (Boolean)rs.getObject(PUBPAGE);
			if(pubFlag==true){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	public boolean isPerfPublic(String name){
		try {
			ResultSet rs;
			rs = stmnt.executeQuery("SELECT * FROM "+tableName+" WHERE "+userColumnName+" = \""+name+"\";");
			rs.next();
			Boolean pubFlag = (Boolean)rs.getObject(PUBPERF);
			if(pubFlag==true){
				return true;
			}
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
			Boolean deactFlag = (Boolean)rs.getObject(DEACT);		
			if(deactFlag==true){
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
	
	public void dumpTable(){
		String tableName = "QuizUser";
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
				String user = (String)rs.getObject(USER);
			    String pass = (String)rs.getObject(PASS);
			    String salt = (String)rs.getObject(SALT);
			    Boolean admin = (Boolean)rs.getBoolean(ADMIN);
			    Boolean pubperf = (Boolean)rs.getBoolean(PUBPERF);
			    Boolean pubpage = (Boolean)rs.getBoolean(PUBPAGE);
			    Boolean deact = (Boolean)rs.getBoolean(DEACT);
			    System.out.println(user);
			    System.out.println(pass);
			    System.out.println(salt);
			    System.out.println(admin);
			    System.out.println(pubperf);
			    System.out.println(pubpage);
			    System.out.println(deact);
			
			}
		

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
