package question;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import answer.*;


/**
 * Servlet implementation class CreateQRServlet
 */
@WebServlet("/CreateQRServlet")
public class CreateQRServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateQRServlet() {
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
		
		ArrayList<Question> pendingQuestions = (ArrayList<Question>)session.getAttribute("pendingQuestions");
		ArrayList<ArrayList<Answer>> pendingAnswers = (ArrayList<ArrayList<Answer>>)session.getAttribute("pendingAnswers");
		
		int questionIndex = (Integer)session.getAttribute("editPendingQuestionIndex");
		
		String questionText = (String)request.getParameter("questionText");
		String answerText = (String)request.getParameter("answer");
		
		// TODO: allow multiple answers
		ArrayList<String> answerTexts = new ArrayList<String>();
		answerTexts.add(answerText);
		ArrayList<Answer> currentAnswer=new ArrayList<Answer>();
		
		Question question = new QuestionResponse(-1, questionText);
		Answer answer = new QuestionResponseAnswer(-1, answerTexts);
		if (questionIndex == -1) {
			pendingQuestions.add(question);
			currentAnswer.add(answer);
			pendingAnswers.add(currentAnswer);
		} else {
			pendingQuestions.set(questionIndex, question);
			currentAnswer.add(answer);
			pendingAnswers.set(questionIndex, currentAnswer);
		}
		
		request.getRequestDispatcher("createQuiz.jsp").forward(request, response);
	}

}
