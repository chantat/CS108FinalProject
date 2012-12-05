package question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

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
		ArrayList<ArrayList<Answer>> pendingAnswers = (ArrayList<ArrayList<Answer>>)session.getAttribute("pendingAnswers");
		
		// Create Question item
		String questionText = (String)request.getParameter("questionText");
		Question currentPendingQuestion = new FillInTheBlank(-1, questionText);
		
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
		Answer answer = new FillInTheBlankAnswer(-1, answerTexts);
		currentPendingAnswer.add(answer);
		
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
