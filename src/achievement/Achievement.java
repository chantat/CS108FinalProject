package achievement;

public class Achievement {
	private final String name;
	private final String description;
	private final boolean isAchieved;
	
	public Achievement(String name, String description, boolean isAchieved) {
		this.name = name;
		this.description = description;
		this.isAchieved = isAchieved;
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
}
