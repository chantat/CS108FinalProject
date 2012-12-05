package achievement;

import java.sql.Timestamp;

public class Achievement {
	private final String name;
	private final String description;
	private final boolean isAchieved;
	private final Timestamp whenAchieved;
	
	public Achievement(String name, String description, boolean isAchieved, Timestamp when) {
		this.name = name;
		this.description = description;
		this.isAchieved = isAchieved;
		this.whenAchieved = when;
	}
	
	public Achievement(String name, String description, boolean isAchieved) {
		this.name = name;
		this.description = description;
		this.isAchieved = isAchieved;
		this.whenAchieved = null;
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
