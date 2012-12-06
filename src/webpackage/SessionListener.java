package webpackage;

import java.util.ArrayList;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import question.Question;
import answer.Answer;

/**
 * Application Lifecycle Listener implementation class SessionListener
 *
 */
@WebListener
public class SessionListener implements HttpSessionListener {

    /**
     * Default constructor. 
     */
    public SessionListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent hse) {
        HttpSession hs = hse.getSession();
        
        ArrayList<Question> pendingQuestions = new ArrayList<Question>();
		ArrayList<Answer> pendingAnswers = new ArrayList<Answer>();
		hs.setAttribute("pendingQuestions", pendingQuestions);  //store the questions that the user is creating
		hs.setAttribute("pendingAnswers", pendingAnswers);  //store the answers that the user is creating
		
		ArrayList<String> pendingTags = new ArrayList<String>();
		/*Boolean pendingIsRandomized = false;
		Boolean pendingIsFlashcard = false;
		Boolean pendingAllowsPractice = false;
		Boolean pendingImmediateFeedback = false;*/
		hs.setAttribute("pendingQuizName", "");
		hs.setAttribute("pendingQuizDescription", "");
		hs.setAttribute("pendingCategory", "");
		hs.setAttribute("pendingTags", pendingTags);
		hs.setAttribute("pendingIsRandomized", false);
		hs.setAttribute("pendingIsFlashcard", false);
		hs.setAttribute("pendingAllowsPractice", false);
		hs.setAttribute("pendingImmediateFeedback", false);
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent hse) {
        // TODO Auto-generated method stub
    }
	
}
