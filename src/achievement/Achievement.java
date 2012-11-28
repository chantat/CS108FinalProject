package achievement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import webpackage.DBConnection;

public class Achievement {
	private final int numAchievement = 6;
	private final String achievementName[] = 
		{"Amateur Author", "Prolific Author", "Prodigous Author", 
			"Quiz Machine", "I am the Greatest", "Practice Makes Perfect"};
	private final String achievementDescription[] = 
		{"The user created a quiz.", 
		 "The user created five quizzes.",
		 "The user created ten quizzes.",
		 "The user took ten quizzes.",
		 "The user had the highest score on a quiz.",
		 "The user took a quiz in practice mode."
		};
	
	private ResultSet rs;
	private Statement stmnt;
	
	public Achievement(DBConnection connection) {
		stmnt = connection.getStatement();
		rs = null;
	}
	
	public int getNumAchievement() {
		return numAchievement;
	}
	
	public String getAchievementName(int achievementId) {
		return achievementName[achievementId];
	}
	
	public String getAchievementDescription(int achievementId) {
		return achievementDescription[achievementId];
	}
	
	public boolean checkAchievement(String username, int achievementId) {
		if (achievementId == 0) {
			return (getNumQuizCreated(username) >= 1);
		} else if (achievementId == 1) {
			return (getNumQuizCreated(username) >= 5);
		} else if (achievementId == 2) {
			return (getNumQuizCreated(username) >= 10);
		} else if (achievementId == 3) {
			return (getNumQuizTaken(username) >= 10);
		} else if (achievementId == 4) {
			return isHadHighScore(username);
		} else if (achievementId == 5) {
			return isUsedPracticeMode(username);
		} else {
			return false;
		}
	}
	
	private int getNumQuizCreated(String username) {
		String command = "SELECT * FROM Quiz WHERE authorID = \"" + username + "\" AND prevID = -1;";
		try {
			rs = stmnt.executeQuery(command);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return DBConnection.getResultSetSize(rs);
	}
	
	private int getNumQuizTaken(String username) {
		String command = "SELECT * FROM Attempts WHERE userID = \"" + username + "\";";
		try {
			rs = stmnt.executeQuery(command);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return DBConnection.getResultSetSize(rs);
	}
	
	private boolean isUsedPracticeMode(String username) {
		String command = "SELECT * FROM QuizUser WHERE username = \"" + username + "\";";
		
		Integer usedPracticeMode = 0;
		try {
			rs = stmnt.executeQuery(command);
			rs.next();
			usedPracticeMode = (Integer)rs.getObject("usedPracticeMode");
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return (usedPracticeMode == 1);
	}
	
	private boolean isHadHighScore(String username) {
		String command = "SELECT * FROM QuizUser WHERE username = \"" + username + "\";";
		
		Integer hadHighScore = 0;
		try {
			rs = stmnt.executeQuery(command);
			rs.next();
			hadHighScore = (Integer)rs.getObject("hadHighScore");
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return (hadHighScore == 1);
	}
}
