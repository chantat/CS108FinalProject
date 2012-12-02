package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import webpackage.DBConnection;

public class QuizManager {
	private int currentQuizId;
	private Statement stmnt;
	
	public QuizManager(DBConnection con) {
		stmnt = con.getStatement();	
		
		// TODO: We have to save the currentQuizId somewhere so that when we start a new instance of the server it doesn't use old quizids
		currentQuizId = 0;
	}
	
	public void createQuiz(String authorID, boolean isRandomizable, boolean isFlashcard, 
			boolean immediateFeedback, boolean allowsPractice, int previousID, 
			String quizDescription, String category, ArrayList<Integer> questionIDs, 
			ArrayList<String> tags) {
		
		addQuizToDatabase(currentQuizId, authorID, isRandomizable, isFlashcard, 
				immediateFeedback, allowsPractice, previousID, quizDescription, 
				category);
		
		addTagsToDatabase(currentQuizId, tags);
		
		addQuestionsToDatabase(currentQuizId, questionIDs);
		
		currentQuizId++;
	}

	public Quiz getQuiz(int quizId) {
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
			ArrayList<String> tags = getTags(quizId);
			ArrayList<Integer> questionIds = getQuestionIds(quizId);
			
			quiz = new Quiz(quizId, authorId, isRandomized, isFlashcard, immediateFeedback, 
					allowsPractice, prevId, description, category, questionIds, tags);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return quiz;
	}
	
	private ArrayList<String> getTags(int quizID){
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
	
	private ArrayList<Integer> getQuestionIds(int quizId) {
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
			
			System.out.println(query); //DEBUGGING
			
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
			
			System.out.println(query); //DEBUGGING
			
			try {
				stmnt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void addQuizToDatabase(int quizID, String authorID, boolean isRandomizable, 
			boolean isFlashcard, boolean immediateFeedback, boolean allowsPractice, 
			int previousID, String quizDescription, String category){
		// Insert the quiz into Quiz table
		String query = "INSERT INTO Quiz (quizID, authorID, isRandomized, prevID, isFlashcard, " +
				"allowsPractice, immediateFeedback, description, category) VALUES (";
		query += currentQuizId + ",";
		query += "\"" + authorID + "\",";
		query += isRandomizable + ",";
		query += previousID + ",";
		query += isFlashcard + ",";
		query += allowsPractice + ",";
		query += immediateFeedback + ",";
		query += "\"" + quizDescription + "\",";
		query += "\"" + category + "\");";
		
		System.out.println(query); //DEBUGGING
		
		try {
			stmnt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
