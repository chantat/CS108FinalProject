package question;

import java.io.IOException;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import answer.*;

/**
 * Servlet implementation class CreatePRServlet
 */
@WebServlet("/CreatePRServlet")
public class CreatePRServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreatePRServlet() {
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
		HttpSession session = request.getSession();
		
		ArrayList<Question> pendingQuestions = (ArrayList<Question>)session.getAttribute("pendingQuestions");
		ArrayList<ArrayList<Answer>> pendingAnswers = (ArrayList<ArrayList<Answer>>)session.getAttribute("pendingAnswers");
		
		int questionIndex = (Integer)session.getAttribute("editPendingQuestionIndex");
		
		String picURL = (String)request.getParameter("picURL");
		String questionText = (String)request.getParameter("questionText");
		String answerText = (String)request.getParameter("answer");
		
		// TODO: allow multiple answers
		ArrayList<String> answerTexts = new ArrayList<String>();
		answerTexts.add(answerText);
		ArrayList<Answer> currentAnswer=new ArrayList<Answer>();
		
		Question question = new PictureResponseQuestion(-1, questionText, picURL);
		Answer answer = new PictureResponseAnswer(-1, answerTexts);
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
