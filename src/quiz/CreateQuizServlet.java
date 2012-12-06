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
		
		ArrayList<Integer> questionIds = new ArrayList<Integer>();
		for (int i = 0; i < pendingQuestions.size(); i++) {
			int questionId = questionManager.createQuestion(pendingQuestions.get(i));
			for(int j = 0; j < pendingAnswers.get(i).size(); j++){
				answerManager.createAnswer(pendingAnswers.get(i).get(j), questionId);
			}
			questionIds.add(questionId);
		}
		pendingQuestions.clear();
		pendingAnswers.clear();
		
		String authorId = (String)session.getAttribute("username");
		String quizName = request.getParameter("quizName");
		String description = request.getParameter("description");
		String category = request.getParameter("category");
		boolean allowsPractice=false;
		if(request.getParameter("allowsPractice").equals("true")) allowsPractice = true;
		
		// TODO make tags work
		ArrayList<String> tags = new ArrayList<String>();
		
		quizManager.createQuiz(authorId, false, false, false, allowsPractice, -1, description, category, questionIds, tags, quizName);
		
		request.getRequestDispatcher("userHomePage.jsp").forward(request, response);
	}

}
