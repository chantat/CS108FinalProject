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
		
		// Create ArrayList<Answer> item
		ArrayList<Answer> currentPendingAnswer = new ArrayList<Answer>();
		ArrayList<String> answerTexts = new ArrayList<String>();
		Map<String, String[]> parameters = request.getParameterMap();
		for(String parameter : parameters.keySet()) {
		    if(parameter.toLowerCase().contains("_answer")) {
		        String answerText = (String)request.getParameter(parameter);
		        answerTexts.add(answerText);
		    }
		}
		Answer answer = new PictureResponseAnswer(-1, answerTexts);
		currentPendingAnswer.add(answer);
		
		// Create Question item
		String picURL = (String)request.getParameter("picURL");
		String questionText = (String)request.getParameter("questionText");
		Question currentPendingQuestion = new PictureResponseQuestion(-1, questionText, picURL);
		
		// Add the items into pendingQuestions and pendingAnswers
		int questionIndex = (Integer)session.getAttribute("editPendingQuestionIndex");
		if (questionIndex == -1) {
			pendingQuestions.add(currentPendingQuestion);
			pendingAnswers.add(currentPendingAnswer);
		} else {
			pendingQuestions.set(questionIndex, currentPendingQuestion);
			pendingAnswers.set(questionIndex, currentPendingAnswer);
		}
		
		request.getRequestDispatcher("createQuiz.jsp").forward(request, response);
	}

}
