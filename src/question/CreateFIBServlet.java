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
 * Servlet implementation class CreateFIBServlet
 */
@WebServlet("/CreateFIBServlet")
public class CreateFIBServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateFIBServlet() {
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
		ArrayList<Answer> pendingAnswers = (ArrayList<Answer>)session.getAttribute("pendingAnswers");
		int questionIndex = (int)session.getAttribute("editPendingQuestionIndex");
		
		String questionText = (String)request.getParameter("questionText");
		String answerText = (String)request.getParameter("answer");
		
		// TODO: allow multiple answers
		ArrayList<String> answerTextList = new ArrayList<String>();
		answerTextList.add(answerText);
		
		Question question = new FillInTheBlank(-1, questionText);
		Answer answer = new FillInTheBlankAnswer(-1, answerTextList);
		if (questionIndex == -1) {
			pendingQuestions.add(question);
			pendingAnswers.add(answer);
		} else {
			pendingQuestions.set(questionIndex, question);
			pendingAnswers.set(questionIndex, answer);
		}
		
		request.getRequestDispatcher("createQuiz.jsp").forward(request, response);
	}

}
