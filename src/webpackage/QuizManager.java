package webpackage;

import java.util.ArrayList;

public class QuizManager {

	private static int currentQuizId=0;
	private static DBConnection con;
	
	public QuizManager(DBConnection con){
		this.con=con;
	}
	
	private void createQuiz(String authorID, boolean isRandomizable, boolean isFlashcard, boolean immediateFeedback, boolean allowsPractice, int previousID, String quizDescription, String category, ArrayList questionIDs, ArrayList tags){
		currentQuizId++;
		Quiz quiz=new Quiz(con, currentQuizId, authorID, isRandomizable, isFlashcard, immediateFeedback, allowsPractice, previousID, quizDescription, category, questionIDs, tags);
	}
	
}
