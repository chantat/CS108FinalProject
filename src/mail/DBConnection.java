package mail;

import java.sql.*;

public class DBConnection {
	static String account = "ccs108rysatt";
	static String password = "ahseixuu";
	static String server = "mysql-user.stanford.edu";
	static String database = "c_cs108_rysatt";
	private Connection con;
	Statement stmt;
	ResultSet rs;
	
	
	/**
	 * Encapsulates setting up the connection to the DB. 
	 * Statement and ResultSet are accessible from within the package.
	 */
	public DBConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://" + server, account, password);
			stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
