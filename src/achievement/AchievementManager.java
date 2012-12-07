package achievement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import quiz.Attempt;

import userPackage.*;
import webpackage.DBConnection;

public class AchievementManager {
	private final int numAchievement = 17;
	private final String achievementName[] = 
		{"Amateur Author", 
		 "Prolific Author", 
		 "Prodigous Author", 
		 "Quiz Machine", 
		 "I am the Greatest", 
		 "Practice Makes Perfect",
		 "Extrovert",
		 "LinkedIn Employee",
		 "Facebook CEO",
		 "Triple Double",
		 "Awkward Penguin",
		 "Playground Bully",
		 "High Praise",
		 "Hired Reviewer",
		 "Net Rage",
		 "Scrooge",
		 "The Ex-Friend"
		 
		 
		};
	private final String achievementDescription[] = 
		{"The user created a quiz.", 
		 "The user created five quizzes.",
		 "The user created ten quizzes.",
		 "The user took ten quizzes.",
		 "The user had the highest score on a quiz.",
		 "The user took a quiz in practice mode.",
		 "The user has at least 5 friends.",
		 "The user has at least 10 friends.",
		 "The user has at least 50 friends.",
		 "The user has made at least 10 quizzes, 10 quizzes taken, and 10 friends.",
		 "The user has at least 10 unapproved friend requests",
		 "The user has at least 10 unaccepted challenges",
		 "The user has given at least 1 5-star review",
		 "The user has given at least 10 5-star reviews",
		 "The user has given at least 1 one-star review",
		 "The user has given at least 10 1-star reviews",
		 "The user has unfriended at least 1 person"
	
		};
	
	private Statement stmnt;
	private AccountUtil acctutil;
	private FriendManager frnMGR;
	public AchievementManager(DBConnection connection, AccountUtil acctutil) {
		stmnt = connection.getStatement();
		this.acctutil = acctutil;
		this.frnMGR = new FriendManager(connection);
	}
	
	public int getNumAchievement() {
		return numAchievement;
	}
	
	public String getIconURL(int achieveID){
		String quote = "\"";
		String description = achievementDescription[achieveID];
		String URL = "<IMG SRC="+quote+achieveID+".gif"+quote+" TITLE="+quote+description+quote+" WIDTH=32 HEIGHT=32>";
		return URL;
	}
	
	public void checkAllAchievement(String username) {
		for (int i = 0; i < numAchievement; i++) {
			checkAchievement(username, i);
		}
	}
	
	public boolean checkAchievement(String username, int achievementId) {  //see if user has gotten this achievement yet
		String command = "SELECT * FROM Achievements ";
		command += "WHERE userId = \"" + username + "\" ";
		command += "AND achievementId = " + achievementId + ";";
		int numFriends = frnMGR.getFriends(username).size();
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
		}else if (achievementId == 6) {		
			newlyAchieved = (numFriends>=5);
		}else if (achievementId == 7) {
			newlyAchieved = (numFriends>=10);
		}else if (achievementId == 8) {
			newlyAchieved = (numFriends>=50);
		}
		if(newlyAchieved){
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			insertAchievementIntoDatabase(username, achievementId, timestamp);
		}
		return newlyAchieved;
	}
	
	public Achievement[] getAllAchievement(String username) {
		Achievement[] ret = new Achievement[numAchievement];
		for (int i = 0; i < numAchievement; i++) {
			ret[i] = new Achievement(achievementName[i], achievementDescription[i], i, checkAchievement(username, i));
		}
		
		return ret;
	}
	
	public void insertAchievementIntoDatabase(String username, int achievementId, Timestamp timestamp){
		String command = "INSERT INTO Achievements(userID, achievementID, timeAchieved) VALUES (";
		command += "\"" + username + "\",";
		command += "" + achievementId + ",";
		command += "'" + timestamp + "');";
		System.out.println(command); // for verification purposes
		
		try {
			stmnt.executeUpdate(command);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Achievement> getAllTimedAchievement(String username) {
		ResultSet testRS;
		ArrayList<Achievement> achieve = new ArrayList<Achievement>();
		String quote = "\"";
		String command = "SELECT * FROM Achievements WHERE userID = "+quote+username+quote+";";
		try {
			testRS = stmnt.executeQuery(command);
			while(testRS.next()){
				String user = testRS.getString("userID");
				int achieveID = testRS.getInt("achievementID");
				Timestamp time = testRS.getTimestamp("timeAchieved");
				String description = achievementDescription[achieveID];
				Achievement item = new Achievement(user,description,true,achieveID,time);
				achieve.add(item);  //add the friend's name to the list		
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return achieve;  //returns an empty list if no friends are found :(

	}
	public void swapAchievements(ArrayList<Achievement> achieveList, int prim, int sec){
		Achievement tempPrim = achieveList.get(prim);
		Achievement tempSec = achieveList.get(sec);
		achieveList.remove(prim);
		achieveList.add(prim,tempSec);
		achieveList.remove(sec);
		achieveList.add(sec,tempPrim);
		
	}
	
	public ArrayList<Achievement> sortAchievementByTime(ArrayList<Achievement> achieveList){
		for(int primary=0;primary < achieveList.size()-1;primary++){
			for(int secondary=primary+1;secondary<achieveList.size();secondary++){
				if(achieveList.get(primary).getWhenAchieved().before(achieveList.get(secondary).getWhenAchieved()) ){
					swapAchievements(achieveList,primary,secondary);
				}
				
			}
			
		}
		return achieveList;
	}

}
