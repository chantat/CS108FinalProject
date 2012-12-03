package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import webpackage.DBConnection;

public class AnswerManager {

	private DBConnection con;
	
	public AnswerManager(DBConnection con){
		this.con=con;
	}
	
	private Answer getAnswer(int qID){
		ResultSet rs = null;
		double score=0.0;
		int numAnswers=0;
		int qType=0;
		HashMap<String, String> answers=new HashMap<String, String>();
		HashMap<String, Double> scores=new HashMap<String, Double>();
		
		//get answers
		try{
			Statement stmt=con.getStatement();
			rs=stmt.executeQuery("SELECT * FROM Answer WHERE qID = '" + qID + "'");
			rs.beforeFirst();
			while(rs.next()){
				String answerKey = (String) rs.getObject(2);
				String equivalentAnswer = (String) rs.getObject(3);
				score = (Double) rs.getObject(4);
				answers.put(answerKey, equivalentAnswer);
				scores.put(answerKey, score);
				if(qType==7){ //matching question: add entries in both directions
					answers.put(equivalentAnswer, answerKey);
					scores.put(equivalentAnswer, score);
				}
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		//get numAnswers and qType
		try{
			Statement stmt=con.getStatement();
			rs=stmt.executeQuery("SELECT * FROM Question WHERE qID = '" + qID + "'");
			rs.beforeFirst();
			qType=(Integer) rs.getObject(2);
			numAnswers=(Integer) rs.getObject(3);
		}catch (SQLException e){
			e.printStackTrace();
		}
		return new Answer(qType, numAnswers, answers, scores);
	}
	
	public void createAnswer(int questionId, ArrayList<String> answers, double score) {
		String answerKey = answers.get(0);
		for (int i = 0; i < answers.size(); i++) {
			String answerValue = answers.get(i);
			insertAnswerIntoDatabase(questionId, answerKey, answerValue, score);
		}
	}
	
	private void insertAnswerIntoDatabase(int qID, String answerKey, String equivalentAnswer, double score){
		String query="INSERT INTO Answer (qID, answerKey, answerText, score) VALUES (" + qID + ", \"" + answerKey + "\", " + equivalentAnswer+ "\", " + score + "\");";
		System.out.println(query); // for verification purposes
		try {
			Statement stmt = con.getStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
