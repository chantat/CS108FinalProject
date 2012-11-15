package webpackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginConnectionSetup {
	Statement stmt;  //use the statement's methods to run MySQL commands
	Connection con;
	static String account = "ccs108adchang1"; // replace with your account static String password = "..."; // replace with your password static String server = "mysql-user.stanford.edu";
	static String database = "c_cs108_adchang1"; // replace with your db
	static String sourcefile = "~/products.sql";   //source file 
	static String tableName = "users";     //the particular table we want
	static String password = "ceemeiru";
	
	
	public LoginConnectionSetup(){
		
		try{
			Class.forName("com.mysql.jdbc.Driver"); 
			con = DriverManager.getConnection( "jdbc:mysql://mysql-user.stanford.edu", account ,password);
			stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
		}
		catch (SQLException e) {
			System.out.println("SQL Exception while trying to set up the JDBC connection");
	//		e.printStackTrace();
			return;
		}
		catch (ClassNotFoundException e) {
			System.out.println("Class Not Found Exception while trying to set up the JDBC connection");
	//		e.printStackTrace();
			return;
		}
	}
	
	public Statement getStatement(){
		return stmt;
	}
	public String getTableName(){
		return tableName;
	}
	
}
