package webpackage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


public class Quiz {
	
	/*import from servlet, dummy for now*/
	private static Connection con;
	private static String account = "ccs108msalexis";
	private static String password = "...";
	private static String server = "mysql-user.stanford.edu";
	private static String database = "c_cs108_msalexis";
	
	
	private int authorID;
	private int flags=0;
	private int quizID;
	private static int currentQuizId=0;
	
	public Quiz(int authorID, boolean isRandomizable, boolean isFlashcard, boolean immediateFeedback, boolean allowsPractice, int previousID, String quizDescription, String category, ArrayList questionIDs, ArrayList tags){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection
			( "jdbc:mysql://" + server, account ,password);
			Statement stmt = con.createStatement();
		}catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		currentQuizId++;
		quizID=currentQuizId;
		putQuizInDatabase(quizID, authorID, isRandomizable, previousID, isFlashcard, allowsPractice, immediateFeedback, quizDescription, category);
		putQuestionsInDatabase(questionIDs);
		setTags(tags);
	}
	
	private void putQuizInDatabase(int quizID, int authorID, boolean isRandomizable, int previousID, boolean isFlashcard, boolean allowsPractice, boolean immediateFeedback, String quizDescription, String category){
		String query="INSERT INTO Quiz (quizID, authorID, isRandomized, prevID, isFlashcard, allowsPractice, immediateFeedback, description, category) VALUES (" + quizID + ", " + authorID + ", " + isRandomizable + ", " + previousID + ", " + isFlashcard + ", " + allowsPractice + ", " + immediateFeedback + ", " + quizDescription + ", " + category + ", " + ")";
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void putQuestionsInDatabase(ArrayList<Integer> questionIDs){
		for(int i = 0; i < questionIDs.size(); i++){
			String query="INSERT INTO QuizQuestion (quizID, qID) VALUES (" + this.quizID + ", " + questionIDs.get(i) + ")";
			try {
				Statement stmt = con.createStatement();
				stmt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void setTags(ArrayList<String> tags){
		for(int i = 0; i < tags.size(); i++){
			String tag=tags.get(i);
			String query="INSERT INTO Tag (quizID, tag) VALUES (" + this.quizID + ", " + tags.get(i) + ")";
			try {
				Statement stmt = con.createStatement();
				stmt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private int getAuthorID(int quizID){
		if(quizID <= 0 || quizID > currentQuizId) return 0;
		ResultSet rs = null;
		try{
			Statement stmt=con.createStatement();
			rs=stmt.executeQuery("SELECT * FROM Quiz WHERE quizID = '" + quizID + "'");
			rs.beforeFirst();
			rs.next();
			Integer authorID = (Integer) rs.getObject(2);
			return authorID;
		}catch (SQLException e){
			e.printStackTrace();
		}
		return 0;
	}
	
	private String getCategory(int quizID){
		if(quizID <= 0 || quizID > currentQuizId) return null;
		ResultSet rs = null;
		try{
			Statement stmt=con.createStatement();
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
	
	private static ArrayList<String> getTags(int quizID){
		ArrayList<String> tags = new ArrayList<String>();
		if(quizID <= 0 || quizID > currentQuizId) return null;
		ResultSet rs = null;
		try{
			Statement stmt=con.createStatement();
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
	
	private static String getDescription(int quizID){
		if(quizID <= 0 || quizID > currentQuizId) return null;
		ResultSet rs = null;
		try{
			Statement stmt=con.createStatement();
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
	
	private static ArrayList<Integer> getQuestionList(int quizID){
		ArrayList<Integer> questions = new ArrayList<Integer>();
		if(quizID <= 0 || quizID > currentQuizId) return null;
		ResultSet rs = null;
		try{
			Statement stmt=con.createStatement();
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
	
	private static boolean isRandomized(int quizID){
		if(quizID <= 0 || quizID > currentQuizId) return false;
		ResultSet rs = null;
		try{
			Statement stmt=con.createStatement();
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
	private static boolean isFlashcard(int quizID){
		if(quizID <= 0 || quizID > currentQuizId) return false;
		ResultSet rs = null;
		try{
			Statement stmt=con.createStatement();
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
	private static boolean allowsPractice(int quizID){
		if(quizID <= 0 || quizID > currentQuizId) return false;
		ResultSet rs = null;
		try{
			Statement stmt=con.createStatement();
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
	private static boolean immediateFeedback(int quizID){
		if(quizID <= 0 || quizID > currentQuizId) return false;
		ResultSet rs = null;
		try{
			Statement stmt=con.createStatement();
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
	
	private static int getNewerQuizID(int quizID){
		if(quizID <= 0 || quizID > currentQuizId) return 0;
		ResultSet rs = null;
		try{
			Statement stmt=con.createStatement();
			rs=stmt.executeQuery("SELECT * FROM Quiz WHERE quizID = '" + quizID + "'");
			rs.beforeFirst();
			rs.next();
			if(rs.getObject(4).equals(null)) return 0;
			Integer older = (Integer) rs.getObject(4);
			return older;
		}catch (SQLException e){
			e.printStackTrace();
		}
		return 0;
	}
	
	private static int getOlderQuizID(int quizID){
		if(quizID <= 0 || quizID > currentQuizId) return 0;
		ResultSet rs = null;
		try{
			Statement stmt=con.createStatement();
			rs=stmt.executeQuery("SELECT * FROM Quiz WHERE prevID = '" + quizID + "'");
			rs.beforeFirst();
			rs.next();
			Integer newer = (Integer) rs.getObject(1);
			return newer;
		}catch (SQLException e){
			e.printStackTrace();
		}
		return 0;
	}
	
}
