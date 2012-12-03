package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import webpackage.DBConnection;

public class QuestionManager {

	private int currentQuestionId;
	
	private DBConnection con;
	private Statement stmnt;
	
	public QuestionManager(DBConnection con){
		this.con=con;
		stmnt = con.getStatement();
		
		// TODO: Get the correct questionId from DB
		currentQuestionId = 0;
	}
	
	public Question getQuestion(int qID){
		int qType=0;
		int numAnswers=0;
		boolean isOrdered=false;
		String qText="";
		String query = "SELECT * FROM Question WHERE qID = " + qID + ";";
		Question question = null;
		ResultSet rs = null;
		try{
			rs = stmnt.executeQuery(query);
			rs.first();
			
			qType=(Integer)rs.getObject(2);
			numAnswers=(Integer)rs.getObject(3);
			isOrdered=(Boolean)rs.getObject(4);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		query = "SELECT * FROM QR WHERE qID = " + qID + ";";
		try{
			rs = stmnt.executeQuery(query);
			rs.first();
			
			qText=(String)rs.getObject(2);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		switch(qType){
		case 1:
			question=new QuestionResponse(qID, qText);
			break;
		case 2:
			question=new FillInTheBlank(qID, qText);
			break;
		case 3:
			question=new MultipleChoiceQuestion(qID, qText);
			break;
		case 4:
			question=new PictureResponseQuestion(qID, qText);
			break;
		case 5:
			question = new MultiAnswerQuestion(qID, qText, numAnswers, isOrdered);
			break;
		case 6:
			question = new MultiChoiceMultiAnswerQuestion(qID, qText, numAnswers);
			break;
		case 7:
			question = new MatchingQuestion(qID, qText, numAnswers);
		}
		return question;
	}
	
	public int createQuestion(int type, String questionText) {
		Question question = null;
		if (type == 1) {
			question = new QuestionResponse(currentQuestionId, questionText);
		}
		addQuestionToDatabase(question);
		return currentQuestionId++;
	}
	
	public void addQuestionToDatabase(Question question){
		int qType=question.getType();
		int qID=question.getID();
		int numAnswers=question.getNumAnswers();
		boolean isOrdered = false;
		if (qType == 5) {
			MultiAnswerQuestion sameQuestion=(MultiAnswerQuestion) question;
			isOrdered=sameQuestion.isOrdered();
		}
		
		String query="INSERT INTO Question (qID, qType, numAnswers, isOrdered) VALUES (" + qID + ", \"" + qType + "\", " + numAnswers + "\", " + isOrdered + "\");";
		System.out.println(query); // for verification purposes
		try {
			Statement stmt = con.getStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addQuestionTextToDatabase(Question question){
		String qText=question.getQText();
		int qID=question.getID();
		String query="INSERT INTO QR (qID, qText) VALUES (" + qID + ", \"" + qText + "\");";
		System.out.println(query); // for verification purposes
		try {
			Statement stmt = con.getStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
