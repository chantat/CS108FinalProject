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
			insertAnswerIntoDatabase(questionId, answerKey, answerList.get(i), answer.getAnswerOrder(), answer.getScore());
		}
	}
	
	private void insertAnswerIntoDatabase(int qID, String answerKey, String equivalentAnswer, int answerOrder, double score){
		String query = "INSERT INTO Answer (qID, answerKey, answerText, answerOrder, score) VALUES (";
		query += qID + ",";
		query += "\"" + answerKey + "\",";
		query += "\"" + equivalentAnswer+ "\",";
		query += answerOrder + ",";
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
		HashMap<String, Integer> answerOrders = new HashMap<String, Integer>();
		
		//get answers
		try {
			rs = stmnt.executeQuery("SELECT * FROM Answer WHERE qID = '" + qID + "'");
			System.out.println("SELECT * FROM Answer WHERE qID = '" + qID + "'");
			rs.beforeFirst();
			while (rs.next()) {
				String answerKey = rs.getString(2);
				String equivalentAnswer = rs.getString(3);
				int answerOrder = rs.getInt(4);
				double score = rs.getDouble(5);
				
				ArrayList<String> answerList = answerLists.get(answerKey);
				if (answerList == null) {
					answerList = new ArrayList<String>();
					answerLists.put(answerKey, answerList);
				}
				answerList.add(equivalentAnswer);
				
				if (!scores.containsKey(answerKey)) {
					scores.put(answerKey, score);
				}
				
				if (!answerOrders.containsKey(answerKey)) {
					answerOrders.put(answerKey, answerOrder);
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
			int answerOrder = answerOrders.get(key);
			double score = scores.get(key);
			System.out.println(key + " " + val + " " + answerOrder + " " + score); //TODO remove
			answers.add(new Answer(qID, val, qType, answerOrder, score));
		}
		
		Collections.sort(answers);
		return answers;
	}
}
