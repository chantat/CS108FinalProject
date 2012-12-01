package webpackage;
import java.sql.*;

public class DBConnection {
	
	private static String account = "ccs108adchang1";
	private static String password = "ceemeiru";
	private static String server = "mysql-user.stanford.edu";
	private static String database = "c_cs108_adchang1";
	
	private Connection conn;
	
	public DBConnection() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://"+server,account,password);

		} 
		catch (SQLException e) {

			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
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
	
	static public int getResultSetSize(ResultSet rs) {
		if (rs == null) {
			return 0;
		} else {
			int rowCount = 0;
			try {
				rs.last();
				rowCount = rs.getRow();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return rowCount;
		}
	}
	
	static public int getResultSetColumnNum(ResultSet rs){
		if (rs == null) {
			return 0;
		} else {
			int colnum=0;
			try {
				ResultSetMetaData rsmd = rs.getMetaData();
				colnum = rsmd.getColumnCount();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return colnum;
		}
		
	}
	
	
}
