package userPackage;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import webpackage.DBConnection;

public class FriendManager{
	Statement stmnt;
	String tableName = "Friend";
	String requestTableName = "FriendRequest";
	String Friend1ColumnName;
	String Friend2ColumnName;
	ResultSet testRS;
	ResultSetMetaData testRSMD;
	public FriendManager(DBConnection con) {
		stmnt = con.getStatement();
		try {
			testRS = stmnt.executeQuery("SELECT * FROM "+tableName+"\"");
			testRSMD = testRS.getMetaData();
			Friend1ColumnName = testRSMD.getColumnName(0);
			Friend2ColumnName = testRSMD.getColumnName(0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void addFriend(String person1,String person2){
		try {
			stmnt.executeUpdate("INSERT INTO "+tableName+" VALUES(\""+person1+"\",\""+person2+"\")");
			stmnt.executeUpdate("INSERT INTO "+tableName+" VALUES(\""+person2+"\",\""+person1+"\")");
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
		
}