package quiz;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import announcement.Announcement;

import webpackage.DBConnection;
public class Report {
	int quizID;
	int occurence;
	Timestamp date;
	
	public Report(int quizID, int occurence, Timestamp date){
		this.quizID = quizID;
		this.occurence = occurence;
		this.date = date;
		
	}
	
	public int getQuizID(){
		return quizID;
		
	}
	public int getOccurence(){
		return occurence;
		
	}
	public Timestamp getDate(){
		return date;
		
	}
	
	
}
