package webpackage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import webpackage.*;


public class Quiz {
	
	
	//private static Connection con;
	private static String database;
	private int quizID;
	private static int currentQuizId=0;
	
	public Quiz(DBConnection con, int quizID, String authorID, boolean isRandomizable, boolean isFlashcard, boolean immediateFeedback, boolean allowsPractice, int previousID, String quizDescription, String category, ArrayList questionIDs, ArrayList tags){
		this.quizID=quizID;
		putQuizInDatabase(con, quizID, authorID, isRandomizable, previousID, isFlashcard, allowsPractice, immediateFeedback, quizDescription, category);
		putQuestionsInDatabase(con, questionIDs);
		setTags(con, tags);
	}
	
	private void putQuizInDatabase(DBConnection con, int quizID, String authorID, boolean isRandomizable, int previousID, boolean isFlashcard, boolean allowsPractice, boolean immediateFeedback, String quizDescription, String category){
		String query="INSERT INTO Quiz (quizID, authorID, isRandomized, prevID, isFlashcard, allowsPractice, immediateFeedback, description, category) VALUES (" + quizID + ", \"" + authorID + "\", " + isRandomizable + ", " + previousID + ", " + isFlashcard + ", " + allowsPractice + ", " + immediateFeedback + ", \"" + quizDescription + "\", \"" + category + "\");";
		System.out.println(query); //DEBUGGING
		try {
			Statement stmt = con.getStatement();
			stmt.executeQuery("USE " + database);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void putQuestionsInDatabase(DBConnection con, ArrayList<Integer> questionIDs){
		for(int i = 0; i < questionIDs.size(); i++){
			String query="INSERT INTO QuizQuestion (quizID, qID) VALUES (" + this.quizID + ", " + questionIDs.get(i) + ");";
			System.out.println(query); //DEBUGGING
			try {
				Statement stmt = con.getStatement();
				stmt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void setTags(DBConnection con, ArrayList<String> tags){
		for(int i = 0; i < tags.size(); i++){
			String tag=tags.get(i);
			String query="INSERT INTO Tag (quizID, tag) VALUES (" + this.quizID + ", \"" + tags.get(i) + "\");";
			System.out.println(query); //DEBUGGING
			try {
				Statement stmt = con.getStatement();
				stmt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private int getQuizID(DBConnection con){
		if(quizID <= 0 || quizID > currentQuizId) return 0;
		ResultSet rs = null;
		try{
			Statement stmt=con.getStatement();
			rs=stmt.executeQuery("SELECT * FROM Quiz WHERE quizID = '" + quizID + "'");
			rs.beforeFirst();
			rs.next();
			int quizID = (Integer) rs.getObject(1);
			return quizID;
		}catch (SQLException e){
			e.printStackTrace();
		}
		return 0;
	}
	
	
	private String getAuthorID(DBConnection con, int quizID){
		if(quizID <= 0 || quizID > currentQuizId) return "";
		ResultSet rs = null;
		try{
			Statement stmt=con.getStatement();
			rs=stmt.executeQuery("SELECT * FROM Quiz WHERE quizID = '" + quizID + "'");
			rs.beforeFirst();
			rs.next();
			String authorID = (String) rs.getObject(2);
			return authorID;
		}catch (SQLException e){
			e.printStackTrace();
		}
		return "";
	}
	
	private String getCategory(DBConnection con, int quizID){
		if(quizID <= 0 || quizID > currentQuizId) return null;
		ResultSet rs = null;
		try{
			Statement stmt=con.getStatement();
			rs=stmt.executeQuery("SELECT * FROM Quiz WHERE quizID = '" + quizID + "'");
			rs.beforeFirst();
			rs.next();
			String category = (String) rs.getObject(9);
			return category;
		}catch (SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
	private static ArrayList<String> getTags(DBConnection con, int quizID){
		ArrayList<String> tags = new ArrayList<String>();
		if(quizID <= 0 || quizID > currentQuizId) return null;
		ResultSet rs = null;
		try{
			Statement stmt=con.getStatement();
			rs=stmt.executeQuery("SELECT * FROM Quiz WHERE quizID = '" + quizID + "'");
			rs.beforeFirst();
			while(rs.next()){
				String tag = (String) rs.getObject(2);
				tags.add(tag);
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return tags;
	}
	
	private static String getDescription(DBConnection con, int quizID){
		if(quizID <= 0 || quizID > currentQuizId) return null;
		ResultSet rs = null;
		try{
			Statement stmt=con.getStatement();
			rs=stmt.executeQuery("SELECT * FROM Quiz WHERE quizID = '" + quizID + "'");
			rs.beforeFirst();
			rs.next();
			String category = (String) rs.getObject(8);
			return category;
		}catch (SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
	private static ArrayList<Integer> getQuestionList(DBConnection con, int quizID){
		ArrayList<Integer> questions = new ArrayList<Integer>();
		if(quizID <= 0 || quizID > currentQuizId) return null;
		ResultSet rs = null;
		try{
			Statement stmt=con.getStatement();
			rs=stmt.executeQuery("SELECT * FROM QuizQuestion WHERE quizID = '" + quizID + "'");
			rs.beforeFirst();
			while(rs.next()){
				int questionID = (Integer) rs.getObject(2);
				questions.add(questionID);
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return questions;
	}
	
	private static boolean isRandomized(DBConnection con, int quizID){
		if(quizID <= 0 || quizID > currentQuizId) return false;
		ResultSet rs = null;
		try{
			Statement stmt=con.getStatement();
			rs=stmt.executeQuery("SELECT * FROM Quiz WHERE quizID = '" + quizID + "'");
			rs.beforeFirst();
			rs.next();
			Boolean randomized = (Boolean) rs.getObject(3);
			return randomized;
		}catch (SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	private static boolean isFlashcard(DBConnection con, int quizID){
		if(quizID <= 0 || quizID > currentQuizId) return false;
		ResultSet rs = null;
		try{
			Statement stmt=con.getStatement();
			rs=stmt.executeQuery("SELECT * FROM Quiz WHERE quizID = '" + quizID + "'");
			rs.beforeFirst();
			rs.next();
			Boolean flashcard = (Boolean) rs.getObject(5);
			return flashcard;
		}catch (SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	private static boolean allowsPractice(DBConnection con, int quizID){
		if(quizID <= 0 || quizID > currentQuizId) return false;
		ResultSet rs = null;
		try{
			Statement stmt=con.getStatement();
			rs=stmt.executeQuery("SELECT * FROM Quiz WHERE quizID = '" + quizID + "'");
			rs.beforeFirst();
			rs.next();
			Boolean practice = (Boolean) rs.getObject(6);
			return practice;
		}catch (SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	private static boolean immediateFeedback(DBConnection con, int quizID){
		if(quizID <= 0 || quizID > currentQuizId) return false;
		ResultSet rs = null;
		try{
			Statement stmt=con.getStatement();
			rs=stmt.executeQuery("SELECT * FROM Quiz WHERE quizID = '" + quizID + "'");
			rs.beforeFirst();
			rs.next();
			Boolean randomized = (Boolean) rs.getObject(7);
			return randomized;
		}catch (SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	private static int getNewerQuizID(DBConnection con, int quizID){
		if(quizID <= 0 || quizID > currentQuizId) return 0;
		ResultSet rs = null;
		try{
			Statement stmt=con.getStatement();
			rs=stmt.executeQuery("SELECT * FROM Quiz WHERE prevID = '" + quizID + "'");
			rs.beforeFirst();
			if(!rs.next()) return 0;
			if(rs.getObject(4).equals(null)) return 0;
			Integer older = (Integer) rs.getObject(1);
			return older;
		}catch (SQLException e){
			e.printStackTrace();
		}
		return 0;
	}
	
	private static int getOlderQuizID(DBConnection con, int quizID){
		if(quizID <= 0 || quizID > currentQuizId) return 0;
		ResultSet rs = null;
		try{
			Statement stmt=con.getStatement();
			rs=stmt.executeQuery("SELECT * FROM Quiz WHERE quizID = '" + quizID + "'");
			if(rs==null) return 0;
			rs.beforeFirst();
			if(!rs.next()) return 0;
			Integer newer = (Integer) rs.getObject(4);
			return newer;
		}catch (SQLException e){
			e.printStackTrace();
		}
		return 0;
	}
	
	
}
