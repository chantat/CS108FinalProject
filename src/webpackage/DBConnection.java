package webpackage;
import java.sql.*;

public class DBConnection {
	
	private static String account = "ccs108sarya";
	private static String password = "";
	private static String server = "mysql-user.stanford.edu";
	private static String database = "c_cs108_sarya";
	
	private Connection conn;
	
	public DBConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://"+server,account,password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Statement getStatement() {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeQuery("USE " + database);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stmt;
	}
}