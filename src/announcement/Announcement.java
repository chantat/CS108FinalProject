package announcement;

public class Announcement {
	private String adminId;
	private String subject;
	private String text;
	
	public Announcement(String adminId, String subject, String text) {
		this.adminId = adminId;
		this.subject = subject;
		this.text = text;
	}
	
	public String getAnnouncementText() {
		return text;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public String getAdminId() {
		return adminId;
	}
}
