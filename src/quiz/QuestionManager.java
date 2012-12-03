package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import webpackage.DBConnection;

public class QuestionManager {

	private DBConnection con;
	private Statement stmnt;
	
	final public int QUESTION_RESPONSE = 1;
	final public int FILL_IN_THE_BLANK = 2;
	final public int MULTIPLE_CHOICE = 3;
	final public int PICTURE_RESPONSE = 4;
	final public int MULTI_ANSWER = 5;
	final public int MULTI_CHOICE_MULTI_ANSWER = 6;
	final public int MATCHING = 7;
	
	public QuestionManager(DBConnection con){
		this.con=con;
		stmnt = con.getStatement();	
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
		case QUESTION_RESPONSE:
			question=new QuestionResponse(qID, qText);
			break;
		case FILL_IN_THE_BLANK:
			question=new FillInTheBlank(qID, qText);
			break;
		case MULTIPLE_CHOICE:
			question=new MultipleChoiceQuestion(qID, qText);
			break;
		case PICTURE_RESPONSE:
			question=new PictureResponseQuestion(qID, qText);
			break;
		case MULTI_ANSWER:
			question = new MultiAnswerQuestion(qID, qText, numAnswers, isOrdered);
			break;
		case MULTI_CHOICE_MULTI_ANSWER:
			question = new MultiChoiceMultiAnswerQuestion(qID, qText, numAnswers);
			break;
		case MATCHING:
			question = new MatchingQuestion(qID, qText, numAnswers);
		}
		return question;
	}
	
	public void addQuestionToDatabase(Question question){
		int qType=question.getType();
		int qID=question.getID();
		int numAnswers=question.getNumAnswers();
		boolean isOrdered=false;
		if(qType==5){
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
