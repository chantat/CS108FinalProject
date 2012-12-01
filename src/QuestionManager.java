import webpackage.DBConnection;


public class QuestionManager {
	
	private static int currentQuestionId=0;
	private static DBConnection con;
	
	public QuestionManager(DBConnection con){
		this.con=con;
	}
	
	public void createQuestion(){
		
	}
}
