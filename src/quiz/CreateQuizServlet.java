package quiz;

import java.io.IOException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import answer.*;
import question.*;
import userPackage.*;

/**
 * Servlet implementation class CreateQuizServlet
 */
@WebServlet("/CreateQuizServlet")
public class CreateQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateQuizServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = request.getServletContext();
		HttpSession session = request.getSession();
		
		QuizManager quizManager = (QuizManager)context.getAttribute("quizManager");
		QuestionManager questionManager = (QuestionManager)context.getAttribute("questionManager");
		AnswerManager answerManager = (AnswerManager)context.getAttribute("answerManager");
		
		ArrayList<Question> pendingQuestions = (ArrayList<Question>)session.getAttribute("pendingQuestions");
		ArrayList<ArrayList<Answer>> pendingAnswers = (ArrayList<ArrayList<Answer>>)session.getAttribute("pendingAnswers");
		ArrayList<String> pendingTags = (ArrayList<String>)session.getAttribute("pendingTags");

		ArrayList<Integer> questionIds = new ArrayList<Integer>();
		for (int i = 0; i < pendingQuestions.size(); i++) {
			int questionId = questionManager.createQuestion(pendingQuestions.get(i));
			for(int j = 0; j < pendingAnswers.get(i).size(); j++){
				answerManager.createAnswer(pendingAnswers.get(i).get(j), questionId);
			}
			questionIds.add(questionId);
		}
		
		String authorId = (String)session.getAttribute("username");
		String quizName = request.getParameter("quizName");
		String description = request.getParameter("description");
		String category = request.getParameter("category");
		
		boolean isRandomized = (request.getParameter("isRandomized") != null);
		boolean isFlashcard = (request.getParameter("isFlashcard") != null);
		boolean allowsPractice = (request.getParameter("allowsPractice") != null);
		boolean immediateFeedback = (request.getParameter("immediateFeedback") != null);
		int previousQuizId = (Integer)session.getAttribute("previousQuizId");
		
		ArrayList<String> tags = new ArrayList<String>();
		Map<String, String[]> parameters = request.getParameterMap();
		for(String parameter : parameters.keySet()) {
		    if(parameter.toLowerCase().contains("_tag_0")) {
		        String tagText = request.getParameter(parameter);
		        tags.add(tagText);
		    }
		}
		
		quizManager.createQuiz(authorId, isRandomized, isFlashcard, immediateFeedback, allowsPractice, 
				previousQuizId, description, category, questionIds, tags, quizName);
		
		// Clear stuff
		pendingQuestions.clear();
		pendingAnswers.clear();
		pendingTags.clear();
		
		session.setAttribute("pendingQuizName", "");
		session.setAttribute("pendingQuizDescription", "");
		session.setAttribute("pendingCategory", "");
		session.setAttribute("pendingIsRandomized", false);
		session.setAttribute("pendingIsFlashcard", false);
		session.setAttribute("pendingAllowsPractice", false);
		session.setAttribute("pendingImmediateFeedback", false);
		session.setAttribute("previousQuizId", -1);
		
		request.getRequestDispatcher("userHomePage.jsp").forward(request, response);
	}

}
