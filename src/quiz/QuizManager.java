package quiz;

import java.sql.*;
import java.util.*;

import webpackage.DBConnection;

public class QuizManager {
	private int currentQuizId;
	private static Statement stmnt;
	
	public QuizManager(DBConnection con) {
		stmnt = con.getStatement();	
		
		currentQuizId = getCurrentQuizId();
	}
	
	//get currentQuizId by getting the max of the current quiz IDs in the database
	private int getCurrentQuizId(){
		int currentQuizID = 0;
		String query = "SELECT MAX(quizID) FROM Quiz;";
		ResultSet rs = null;
		try{
			rs = stmnt.executeQuery(query);
			if (rs.next()) {
				  currentQuizID = rs.getInt(1) + 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return currentQuizID;
	}
	
	public void createQuiz(String authorID, boolean isRandomizable, boolean isFlashcard, 
			boolean immediateFeedback, boolean allowsPractice, int previousID, 
			String quizDescription, String category, ArrayList<Integer> questionIDs, 
			ArrayList<String> tags, String quizName) {
		
		addQuizToDatabase(currentQuizId, authorID, isRandomizable, isFlashcard, 
				immediateFeedback, allowsPractice, previousID, quizDescription, 
				category, quizName);
		
		addTagsToDatabase(currentQuizId, tags);
		
		addQuestionsToDatabase(currentQuizId, questionIDs);
		
		currentQuizId++;
	}

	public String getQuizName(int quizId){
		Quiz quiz = getQuiz(quizId);
		return quiz.getName();	
	}
	
	public boolean getQuizAllowsPractice(int quizID){
		boolean allowsPractice=false;
		String query = "SELECT * FROM Quiz WHERE quizID = " + quizID + ";";
		ResultSet rs = null;
		try {
			rs = stmnt.executeQuery(query);
			rs.first();
			allowsPractice=rs.getBoolean("allowsPractice");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allowsPractice;
	}
	
	public  Quiz getQuiz(int quizId) {
		String query = "SELECT * FROM Quiz WHERE quizID = " + quizId + ";";
		
		Quiz quiz = null;
		ResultSet rs = null;
		try{
			rs = stmnt.executeQuery(query);
			rs.first();
			
			String authorId = rs.getString("authorID");
			Boolean isRandomized = rs.getBoolean("isRandomized");
			Integer prevId = rs.getInt("prevID");
			Boolean isFlashcard = rs.getBoolean("isFlashcard");
			Boolean allowsPractice = rs.getBoolean("allowsPractice");
			Boolean immediateFeedback = rs.getBoolean("immediateFeedback");
			String description = rs.getString("description");
			String category = rs.getString("category");
			String quizName = rs.getString("quizName");
			Timestamp timeCreated = rs.getTimestamp("timeCreated");
			ArrayList<String> tags = getTags(quizId);
			ArrayList<Integer> questionIds = getQuestionIds(quizId);
			
			quiz = new Quiz(quizId, authorId, isRandomized, isFlashcard, immediateFeedback, 
					allowsPractice, prevId, description, category, questionIds, tags, quizName, timeCreated);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return quiz;
	}
	
	public void deleteQuiz(int quizID) {
		Quiz quiz = getQuiz(quizID);
		String commandQuiz = "DELETE FROM Quiz WHERE quizID = \""+ quizID + "\";";
		

		String commandTag = "DELETE FROM Tag WHERE quizID = \""+ quizID + "\";";
		String commandQuizQuestion = "DELETE FROM QuizQuestion WHERE quizID = \""+ quizID + "\";";
		String commandChallenge = "DELETE FROM Challenge WHERE quizID = \""+ quizID + "\";";
		String commandRating = "DELETE FROM Rating WHERE quizID = \""+ quizID + "\";";
		String commandAttempts = "DELETE FROM Attempts WHERE quizID = \""+ quizID + "\";";
		String commandReview = "DELETE FROM Review WHERE quizID = \""+ quizID + "\";";
		String commandReported = "DELETE FROM Reported WHERE quizID = \""+ quizID + "\";";
		
		
		try {
			stmnt.executeUpdate(commandQuiz);
			stmnt.executeUpdate(commandTag);
			stmnt.executeUpdate(commandQuizQuestion);
			stmnt.executeUpdate(commandChallenge);
			stmnt.executeUpdate(commandRating);
			stmnt.executeUpdate(commandAttempts);
			stmnt.executeUpdate(commandReview);
			stmnt.executeUpdate(commandReported);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (quiz.getPreviousId() != -1) {
			deleteQuiz(quiz.getPreviousId());
		}
	}
	
	public int getNumQuestions(int quizID){
		String query = "SELECT COUNT(qID) FROM QuizQuestion WHERE quizID = " + quizID + ";";
		ResultSet rs = null;
		int count = 0;
		try {
			rs = stmnt.executeQuery(query);
			rs.first();
			count = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	private  ArrayList<String> getTags(int quizID){
		ArrayList<String> tags = new ArrayList<String>();
		
		String query = "SELECT * FROM Tag WHERE quizID = " + quizID + ";";
		ResultSet rs = null;
		try {
			rs = stmnt.executeQuery(query);
			rs.beforeFirst();
			while (rs.next()) {
				String tag = rs.getString("tag");
				tags.add(tag);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tags;
	}
	
	private  ArrayList<Integer> getQuestionIds(int quizId) {
		ArrayList<Integer> questionIds = new ArrayList<Integer>();

		String query = "SELECT * FROM QuizQuestion WHERE quizID = " + quizId + ";";
		ResultSet rs = null;
		try{
			rs = stmnt.executeQuery(query);
			rs.beforeFirst();
			while (rs.next()) {
				int questionID = rs.getInt("qID");
				questionIds.add(questionID);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return questionIds;
	}
	
	private void addTagsToDatabase(int quizId, ArrayList<String> tags) {
		for(int i = 0; i < tags.size(); i++){
			String query = "INSERT INTO Tag (quizID, tag) VALUES (";
			query += quizId + ",";
			query += "\"" + tags.get(i) + "\");";
			
			//System.out.println(query); //DEBUGGING
			
			try {
				stmnt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void addQuestionsToDatabase(int quizID, ArrayList<Integer> questionIDs) {
		for(int i = 0; i < questionIDs.size(); i++){
			String query = "INSERT INTO QuizQuestion (quizID, qID) VALUES (";
			query += quizID + ",";
			query += questionIDs.get(i) + ");";
			
			//System.out.println(query); //DEBUGGING
			
			try {
				stmnt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void addQuizToDatabase(int quizID, String authorID, boolean isRandomizable, 
			boolean isFlashcard, boolean immediateFeedback, boolean allowsPractice, 
			int previousID, String quizDescription, String category, String quizName){
		// Insert the quiz into Quiz table
		String query = "INSERT INTO Quiz (quizID, authorID, isRandomized, prevID, isFlashcard, " +
				"allowsPractice, immediateFeedback, description, category, quizName) VALUES (";
		query += currentQuizId + ",";
		query += "\"" + authorID + "\",";
		query += isRandomizable + ",";
		query += previousID + ",";
		query += isFlashcard + ",";
		query += allowsPractice + ",";
		query += immediateFeedback + ",";
		query += "\"" + quizDescription + "\",";
		query += "\"" + category + "\",";
		query += "\"" + quizName + "\");";
		
		//System.out.println(query); //DEBUGGING
		
		try {
			stmnt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Quiz[] getAllQuizzes() {
		String query = "SELECT * FROM Quiz;";
		ResultSet rs = null;
		int resultSetSize=0;
		ArrayList<Quiz> tempQuizzes = new ArrayList<Quiz>();
		try {
			rs = stmnt.executeQuery(query);
			resultSetSize = DBConnection.getResultSetSize(rs);
			
			ArrayList<Integer> QuizIds = new ArrayList<Integer>();
			rs.beforeFirst();
			for(int i = 0 ; i < resultSetSize; i++) {
				rs.next();
				QuizIds.add(rs.getInt("quizID"));
			}
			
			for(int i = 0 ; i < resultSetSize; i++){
				tempQuizzes.add(getQuiz(QuizIds.get(i)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
		for (int i = 0; i < tempQuizzes.size(); i++) {
			if (!hasNewerVersion(tempQuizzes.get(i).getQuizId())) {
				quizzes.add(tempQuizzes.get(i));
			}
		}
		return quizzes.toArray(new Quiz[quizzes.size()]);
	}
	
	
	public Quiz[] getAllQuizzesByAuthor(String user) {
		String quote = "\"";
		String query = "SELECT * FROM Quiz WHERE authorID = "+quote+user+quote+";";
		ResultSet rs = null;
		int resultSetSize=0;
		ArrayList<Quiz> tempQuizzes = new ArrayList<Quiz>();
		try {
			rs = stmnt.executeQuery(query);
			resultSetSize = DBConnection.getResultSetSize(rs);
			
			ArrayList<Integer> QuizIds = new ArrayList<Integer>();
			rs.beforeFirst();
			for(int i = 0 ; i < resultSetSize; i++) {
				rs.next();
				QuizIds.add(rs.getInt("quizID"));
			}
			
			for(int i = 0 ; i < resultSetSize; i++){
				tempQuizzes.add(getQuiz(QuizIds.get(i)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
		for (int i = 0; i < tempQuizzes.size(); i++) {
			if (!hasNewerVersion(tempQuizzes.get(i).getQuizId())) {
				quizzes.add(tempQuizzes.get(i));
			}
		}
		return quizzes.toArray(new Quiz[quizzes.size()]);
	}
	
	public  boolean hasNewerVersion(int quizId) {
		String query = "SELECT * FROM Quiz WHERE prevID=" + quizId + ";";
		int resultSetSize=0;
		ResultSet rs = null;
		
		try {
			rs = stmnt.executeQuery(query);
			resultSetSize = DBConnection.getResultSetSize(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (resultSetSize != 0);
	}
	
	public Quiz[] getAllQuizByCategory(String category) {
		String query = "SELECT * FROM Quiz WHERE category = " + category + ";";
		int resultSetSize=0;
		ResultSet rs = null;
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
		try{
			rs = stmnt.executeQuery(query);
			resultSetSize = DBConnection.getResultSetSize(rs);
			
			ArrayList<Integer> QuizIds = new ArrayList<Integer>();
			rs.beforeFirst();
			for(int i = 0 ; i < resultSetSize; i++) {
				rs.next();
				int quizID = rs.getInt("quizID");
				QuizIds.add(quizID);
			}
			
			for(int i = 0 ; i < resultSetSize; i++){
				if (!hasNewerVersion(QuizIds.get(i))) {
					quizzes.add(getQuiz(QuizIds.get(i)));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return quizzes.toArray(new Quiz[quizzes.size()]);
	}
	
	public Quiz[] getAllQuizByTag(String tag) {
		String query = "SELECT * FROM Tag WHERE tag = " + tag + ";";
		int resultSetSize=0;
		ResultSet rs = null;
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
		try{
			rs = stmnt.executeQuery(query);
			resultSetSize = DBConnection.getResultSetSize(rs);
			
			ArrayList<Integer> QuizIds = new ArrayList<Integer>();
			rs.beforeFirst();
			for(int i = 0 ; i < resultSetSize; i++) {
				rs.next();
				int quizID = rs.getInt("quizID");
				QuizIds.add(quizID);
			}
			
			for(int i = 0 ; i < resultSetSize; i++){
				if (!hasNewerVersion(QuizIds.get(i))) {
					quizzes.add(getQuiz(QuizIds.get(i)));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return quizzes.toArray(new Quiz[quizzes.size()]);
	}
	
	public Quiz[] getAllQuizzesSortTime() {
		String query = "SELECT * FROM Quiz ORDER BY timeCreated DESC;";
		int resultSetSize=0;
		ResultSet rs = null;
		ArrayList<Integer> QuizIds = new ArrayList<Integer>();
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
		try{
			rs = stmnt.executeQuery(query);
			resultSetSize = DBConnection.getResultSetSize(rs);
			
			rs.first();
			for(int i = 0 ; i < resultSetSize; i++) {
				int quizID = rs.getInt("quizID");
				QuizIds.add(quizID);
				rs.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(int i = 0 ; i < resultSetSize; i++){
			if (!hasNewerVersion(QuizIds.get(i))) {
				quizzes.add(getQuiz(QuizIds.get(i)));
			}
		}
		
		return quizzes.toArray(new Quiz[quizzes.size()]);
	}
}
