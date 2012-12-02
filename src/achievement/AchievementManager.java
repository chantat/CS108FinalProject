package achievement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import userPackage.*;
import webpackage.DBConnection;

public class AchievementManager {
	private final int numAchievement = 6;
	private final String achievementName[] = 
		{"Amateur Author", 
		 "Prolific Author", 
		 "Prodigous Author", 
		 "Quiz Machine", 
		 "I am the Greatest", 
		 "Practice Makes Perfect"};
	private final String achievementDescription[] = 
		{"The user created a quiz.", 
		 "The user created five quizzes.",
		 "The user created ten quizzes.",
		 "The user took ten quizzes.",
		 "The user had the highest score on a quiz.",
		 "The user took a quiz in practice mode."};
	
	private Statement stmnt;
	private AccountUtil acctutil;
	
	public AchievementManager(DBConnection connection, AccountUtil acctutil) {
		stmnt = connection.getStatement();
		this.acctutil = acctutil;
	}
	
	public int getNumAchievement() {
		return numAchievement;
	}
	
	public void checkAllAchievement(String username) {
		for (int i = 0; i < numAchievement; i++) {
			checkAchievement(username, i);
		}
	}
	
	public boolean checkAchievement(String username, int achievementId) {
		String command = "SELECT * FROM Achievements ";
		command += "WHERE userId = \"" + username + "\" ";
		command += "AND achievementId = " + achievementId + ";";
		
		ResultSet rs = null;
		try {
			rs = stmnt.executeQuery(command);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// The achievement is already achieved, just return
		if (DBConnection.getResultSetSize(rs) == 1) {
			
			return true;
		}
		
		boolean newlyAchieved = false;
		if (achievementId == 0) {
			newlyAchieved = (acctutil.getNumQuizCreated(username) >= 1);
		} else if (achievementId == 1) {
			newlyAchieved = (acctutil.getNumQuizCreated(username) >= 5);
		} else if (achievementId == 2) {
			newlyAchieved = (acctutil.getNumQuizCreated(username) >= 10);
		} else if (achievementId == 3) {
			newlyAchieved = (acctutil.getNumQuizTaken(username) >= 10);
		} else if (achievementId == 4) {
			newlyAchieved = acctutil.isHadHighScore(username);
		} else if (achievementId == 5) {
			newlyAchieved = acctutil.isUsedPracticeMode(username);
		}
		
		if (newlyAchieved) {
			command = "INSERT INTO Achievements(userID, achievementID) VALUES (";
			command += "\"" + username + "\",";
			command += achievementId + ");";
			
			try {
				stmnt.executeUpdate(command);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
		return newlyAchieved;
	}
	
	public Achievement[] getAllAchievement(String username) {
		Achievement[] ret = new Achievement[numAchievement];
		for (int i = 0; i < numAchievement; i++) {
			ret[i] = new Achievement(achievementName[i], achievementDescription[i], checkAchievement(username, i));
		}
		
		return ret;
	}
}