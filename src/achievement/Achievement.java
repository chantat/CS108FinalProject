package achievement;

import java.sql.Timestamp;

public class Achievement {
	private final String name;
	private final String description;
	private final boolean isAchieved;
	private final Timestamp whenAchieved;
	private final int achieveID;
	
	public Achievement(String name, String description, boolean isAchieved, int achieveID, Timestamp when) {
		this.name = name;
		this.description = description;
		this.isAchieved = isAchieved;
		this.whenAchieved = when;
		this.achieveID = achieveID;
	}
	
	public Achievement(String name, String description, int achieveID, boolean isAchieved) {
		this.name = name;
		this.description = description;
		this.isAchieved = isAchieved;
		this.achieveID = achieveID;
		this.whenAchieved = null;
	}
	
	public int getAchieveID(){
		return achieveID;
		
	}
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public boolean getIsAchieved() {
		return this.isAchieved;
	}
	public Timestamp getWhenAchieved(){
		return this.whenAchieved;
	}
}
