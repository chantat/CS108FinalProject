package question;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import webpackage.DBConnection;

public class QuestionManager {
	private static final String[] typeName = 
		{"Question Response",
		 "Fill in the Blank",
		 "Multiple Choice",
		 "Picture Response",
		 "Multi-Answer",
		 "Multiple Choice with Multiple Answers",
		 "Matching"
	};
	
	private int currentQuestionId;
	
	private DBConnection con;
	private Statement stmnt;
	
	final public static int QUESTION_RESPONSE = 1;
	final public static int FILL_IN_THE_BLANK = 2;
	final public static int MULTIPLE_CHOICE = 3;
	final public static int PICTURE_RESPONSE = 4;
	final public static int MULTI_ANSWER = 5;
	final public static int MULTI_CHOICE_MULTI_ANSWER = 6;
	final public static int MATCHING = 7;
	
	public QuestionManager(DBConnection con){
		this.con = con;
		stmnt = con.getStatement();
		
		currentQuestionId = getCurrentQuestionId();
	}
	
	private int getCurrentQuestionId(){
		int currentQuestionID = 0;
		String query = "SELECT * FROM Question;";
		ResultSet rs = null;
		try{
			rs = stmnt.executeQuery(query);
			currentQuestionID = DBConnection.getResultSetSize(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return currentQuestionID;
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
			
			qType = rs.getInt("qType");
			numAnswers = rs.getInt("numAnswers");
			isOrdered = rs.getBoolean("isOrdered");
			qText = rs.getString("qText");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// qText now in Question table
//		query = "SELECT * FROM QR WHERE qID = " + qID + ";";
//		try{
//			rs = stmnt.executeQuery(query);
//			rs.first();
//			
//			qText=(String)rs.getObject(2);
//			System.out.println(qText);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
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
			query = "SELECT * FROM PR WHERE qID = " + qID + ";";
			String qPicUrl="";
			try{
				rs = stmnt.executeQuery(query);
				rs.first();
				qPicUrl = rs.getString("qPicUrl");
				System.out.println(qPicUrl);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			question=new PictureResponseQuestion(qID, qText, qPicUrl);
			break;
		case MULTI_ANSWER:
			question = new MultiAnswerQuestion(qID, qText, numAnswers, isOrdered);
			break;
		case MULTI_CHOICE_MULTI_ANSWER:
			question = new MultiChoiceMultiAnswerQuestion(qID, qText);
			break;
		case MATCHING:
			question = new MatchingQuestion(qID, qText, numAnswers);
		}
		return question;
	}
	
	public int createQuestion(Question question) {
		question.setID(currentQuestionId);
		addQuestionToDatabase(question);
//		switch (question.getType()) {
//			case QUESTION_RESPONSE:
//				addQuestionTextToQRTable(question);
//				break;
//			case FILL_IN_THE_BLANK:
//				addQuestionTextToFIBTable(question);
//				break;
//		}
		return currentQuestionId++;
	}
	
	/*public int createQuestion(int type, String questionText) {
		Question question = null;
		if (type == 1) {
			question = new QuestionResponse(currentQuestionId, questionText);
		}
		addQuestionToDatabase(question);
		addQuestionTextToDatabase(question);
		return currentQuestionId++;
	}*/
	
	public void addQuestionToDatabase(Question question){
		int qType=question.getType();
		int qID=question.getID();
		String qText = question.getQText();
		int numAnswers=question.getNumAnswers();
		boolean isOrdered = false;
		if (qType == MULTI_ANSWER) {
			MultiAnswerQuestion sameQuestion=(MultiAnswerQuestion) question;
			isOrdered=sameQuestion.isOrdered();
		}
		
		String query="INSERT INTO Question (qID, qType, qText, numAnswers, isOrdered) VALUES (" + qID + ", " + qType + ", \"" + qText + "\", "+ numAnswers + ", " + isOrdered + ");";
		System.out.println(query); // for verification purposes
		try {
			Statement stmt = con.getStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (qType == PICTURE_RESPONSE) {
			String qPicUrl = ((PictureResponseQuestion) question).getURL();
			query="INSERT INTO PR (qID, qPicUrl) VALUES (" + qID + ", \"" + qPicUrl + "\");";
			System.out.println(query); // for verification purposes
			try {
				Statement stmt = con.getStatement();
				stmt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	public void addQuestionTextToQRTable(Question question){
		String qText = question.getQText();
		int qID = question.getID();
		String query = "INSERT INTO QR (qID, qText) VALUES (" + qID + ", \"" + qText + "\");";
		System.out.println(query); // for verification purposes
		try {
			Statement stmt = con.getStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addQuestionTextToFIBTable(Question question){
		String qText = question.getQText();
		int qID = question.getID();
		String query = "INSERT INTO FIB (qID, qText) VALUES (" + qID + ", \"" + qText + "\");";
		System.out.println(query); // for verification purposes
		try {
			Statement stmt = con.getStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addQuestionTextToPRTable(Question question){
		String qText=question.getQText();
		int qID=question.getID();
		String qPicUrl = ((PictureResponseQuestion) question).getURL();
		String query="INSERT INTO PR (qID, qText, qPicUrl) VALUES (" + qID + ", \"" + qText + ",\"" + qPicUrl + "\");";
		System.out.println(query); // for verification purposes
		try {
			Statement stmt = con.getStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	*/
	
	public static String getTypeName(int type) {
		return typeName[type - 1];
	}
	
	public static int getNumType() {
		return typeName.length;
	}
	
}
