package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Quiz {
	private final int quizId;
	private final String authorId;
	private final boolean isRandomized;
	private final boolean isFlashcard;
	private final boolean immediateFeedback;
	private final boolean allowsPractice;
	private final int previousId;
	private final String description;
	private final String category; 
	private final ArrayList<Integer> questionIds; 
	private final ArrayList<String> tags;
	private final String quizName;
	
	
	public Quiz(int quizId, String authorId, boolean isRandomized, boolean isFlashcard, 
			boolean immediateFeedback, boolean allowsPractice, int previousId, 
			String description, String category, ArrayList<Integer> questionIds, 
			ArrayList<String> tags, String quizName){
		this.quizId = quizId;
		this.authorId = authorId;
		this.isRandomized = isRandomized;
		this.isFlashcard = isFlashcard;
		this.immediateFeedback = immediateFeedback;
		this.allowsPractice = allowsPractice;
		this.previousId = previousId;
		this.description = description;
		this.category = category;
		this.questionIds = questionIds; 
		this.tags = tags;
		this.quizName = quizName;
	}
	
	public int getQuizId() {
		return quizId;
	}
	
	public String getAuthorId() {
		return authorId;
	}
	
	public boolean getIsRandomized() {
		return isRandomized;
	}
	
	public boolean getIsFlashcard() {
		return isFlashcard;
	}
	
	public boolean getImmediateFeedback() {
		return immediateFeedback;
	}
	
	public boolean getAllowsPractice() {
		return allowsPractice;
	}
	
	public int getPreviousId() {
		return previousId;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getCategory() {
		return category; 
	}
	
	public ArrayList<Integer> getQuestionIds() {
		return questionIds;
	}
	
	public ArrayList<String> getTags() {
		return tags;
	}
	
	public String getName(){
		return quizName;
	}
	
	public int getNumQuestions(){
		return questionIds.size();
	}
	
	/* Old code: might be useful
	private static int getNewerQuizID(DBConnection con, int quizID){
		if(quizID <= 0 || quizID > currentQuizId) return 0;
		ResultSet rs = null;
		try{
			Statement stmt=con.getStatement();
			rs=stmt.executeQuery("SELECT * FROM Quiz WHERE prevID = '" + quizID + "'");
			rs.beforeFirst();
			if(!rs.next()) return 0;
			if(rs.getObject(4).equals(null)) return 0;
			Integer older = (Integer) rs.getObject(1);
			return older;
		}catch (SQLException e){
			e.printStackTrace();
		}
		return 0;
	}*/
}
