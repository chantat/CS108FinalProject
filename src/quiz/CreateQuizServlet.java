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

import userPackage.AccountManager;

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
		
		String authorId = (String)session.getAttribute("username");
		String quizName = request.getParameter("quizName");
		String description = request.getParameter("description");
		String category = request.getParameter("category");
		
		String questionText = request.getParameter("questionText");
		String answer = request.getParameter("answer");
		
		// TODO create other question types and more number of questions
		ArrayList<Integer> questionIds = new ArrayList<Integer>();
		questionIds.add(questionManager.createQuestion(1, questionText));
		
		ArrayList<String> answers = new ArrayList<String>();
		answers.add(answer);
		answerManager.createAnswer(questionIds.get(0), answers, 1);
		
		// TODO make tags work
		ArrayList<String> tags = new ArrayList<String>();
		
		quizManager.createQuiz(authorId, false, false, false, false, -1, description, category, questionIds, tags, quizName);
		
		request.getRequestDispatcher("userHomePage.jsp").forward(request, response);
	}

}
