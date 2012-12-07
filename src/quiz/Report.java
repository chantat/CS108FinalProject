package quiz;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import announcement.Announcement;

import webpackage.DBConnection;
public class Report {
	int quizID;
	int occurrence;
	Timestamp date;
	
	public Report(int quizID, int occurrence, Timestamp date){
		this.quizID = quizID;
		this.occurrence = occurrence;
		this.date = date;
		
	}
	
	public int getQuizID(){
		return quizID;
		
	}
	public int getOccurrence(){
		return occurrence;
		
	}
	public Timestamp getDate(){
		return date;
		
	}
	
	
}
