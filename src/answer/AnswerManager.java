package answer;

import java.sql.*;
import java.util.*;

import webpackage.DBConnection;

public class AnswerManager {
	private Statement stmnt;
	
	public AnswerManager(DBConnection con) {
		stmnt = con.getStatement();
	}
	
	public void createAnswer(Answer answer, int questionId) {
		ArrayList<String> answerList = answer.getAnswerList();
		
		String answerKey = answerList.get(0);
		for (int i = 0; i < answerList.size(); i++) {
			insertAnswerIntoDatabase(questionId, answerKey, answerList.get(i), answer.getScore());
		}
	}
	
	private void insertAnswerIntoDatabase(int qID, String answerKey, String equivalentAnswer, double score){
		String query = "INSERT INTO Answer (qID, answerKey, answerText, score) VALUES (";
		query += qID + ",";
		query += "\"" + answerKey + "\",";
		query += "\"" + equivalentAnswer+ "\",";
		query += score + ");";
		
		System.out.println(query); // for verification purposes
		
		try {
			stmnt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Answer> getAnswers(int qID) {
		ResultSet rs = null;
		HashMap<String, ArrayList<String> > answerLists = new HashMap<String, ArrayList<String> >();
		HashMap<String, Double> scores = new HashMap<String, Double>();
		
		//get answers
		try {
			rs = stmnt.executeQuery("SELECT * FROM Answer WHERE qID = '" + qID + "'");
			rs.beforeFirst();
			while (rs.next()) {
				String answerKey = rs.getString(2);
				String equivalentAnswer = rs.getString(3);
				double score = rs.getDouble(4);
				
				ArrayList<String> answerList = answerLists.get(answerKey);
				if (answerList == null) {
					answerList = new ArrayList<String>();
					answerLists.put(answerKey, answerList);
				}
				answerList.add(equivalentAnswer);
				
				if (!scores.containsKey(answerKey)) {
					scores.put(answerKey, score);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		int qType = 0;
		//get questionType
		try {
			rs = stmnt.executeQuery("SELECT * FROM Question WHERE qID = '" + qID + "'");
			rs.first();
			qType = rs.getInt(2);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ArrayList<Answer> answers = new ArrayList<Answer>();
		Iterator<String> iter = answerLists.keySet().iterator();
		while(iter.hasNext()) {
			String key = iter.next();
			ArrayList<String> val = answerLists.get(key);
			double score = scores.get(key);
			
			answers.add(new Answer(qID, val, qType, score));
		}
		return answers;
	}
}
