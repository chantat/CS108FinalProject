package quiz;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import question.Question;
import question.QuestionManager;
import answer.Answer;
import answer.AnswerManager;

/**
 * Servlet implementation class EditQuizServlet
 */
@WebServlet("/EditQuizServlet")
public class EditQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditQuizServlet() {
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
		
		int quizId = Integer.parseInt(request.getParameter("quizId"));
		
		QuizManager quizManager = (QuizManager)context.getAttribute("quizManager");
		QuestionManager questionManager = (QuestionManager)context.getAttribute("questionManager");
		AnswerManager answerManager = (AnswerManager)context.getAttribute("answerManager");
		
		ArrayList<Question> pendingQuestions = (ArrayList<Question>)session.getAttribute("pendingQuestions");
		ArrayList<ArrayList<Answer>> pendingAnswers = (ArrayList<ArrayList<Answer>>)session.getAttribute("pendingAnswers");
		
		Boolean pendingIsFlashcard = (Boolean)session.getAttribute("pendingIsFlashcard");
		Boolean pendingAllowsPractice = (Boolean)session.getAttribute("pendingAllowsPractice");
		Boolean pendingImmediateFeedback = (Boolean)session.getAttribute("pendingImmediateFeedback");
		
		Quiz quiz = quizManager.getQuiz(quizId);
		session.setAttribute("pendingQuizName", quiz.getName());
		session.setAttribute("pendingQuizDescription", quiz.getDescription());
		session.setAttribute("pendingCategory", quiz.getCategory());
		session.setAttribute("pendingIsRandomized", quiz.getIsRandomized());
		session.setAttribute("pendingIsFlashcard", quiz.getIsFlashcard());
		session.setAttribute("pendingAllowsPractice", quiz.getAllowsPractice());
		session.setAttribute("pendingImmediateFeedback", quiz.getImmediateFeedback());
		session.setAttribute("pendingTags", quiz.getTags());
		 
		pendingQuestions.clear();
		pendingAnswers.clear();
		ArrayList<Integer> questionIds = quiz.getQuestionIds();
		for (int i = 0; i < questionIds.size(); i++) {
			Question question = questionManager.getQuestion(questionIds.get(i));
			pendingQuestions.add(question);
			
			ArrayList<Answer> answers = answerManager.getAnswers(questionIds.get(i));
			pendingAnswers.add(answers);
		}
		
		request.getRequestDispatcher("createQuiz.jsp").forward(request, response);
	}

}
