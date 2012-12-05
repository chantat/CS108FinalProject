package question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import answer.*;

/**
 * Servlet implementation class CreateMServlet
 */
@WebServlet("/CreateMServlet")
public class CreateMServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateMServlet() {
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
		
		// Create ArrayList<Answer> item
		HashMap<Integer, String> answerMap = new HashMap<Integer, String>();
		Map<String, String[]> parameters = request.getParameterMap();
		for(String parameter : parameters.keySet()) {
		    if(parameter.toLowerCase().contains("_left")) {
		        String answerTextLeft = (String)request.getParameter(parameter);
		        String[] tokens = parameter.split("_");
		        String answerTextRight = (String)request.getParameter(tokens[0] + "_right");
		        String answerText = answerTextLeft + "#" + answerTextRight;
		        
		        int answerIndex = Integer.parseInt(tokens[0]);
		        
		        if (!answerMap.containsKey(answerIndex)) {
		        	answerMap.put(answerIndex, answerText);
		        }
		    }
		}
		
		ArrayList<Answer> currentPendingAnswer = new ArrayList<Answer>();
		Set<Integer> keys = answerMap.keySet();
		Integer keysArray[] = keys.toArray(new Integer[keys.size()]);
		Arrays.sort(keysArray);
		
		int answerCounter = 0;
		for (int i = 0; i < keysArray.length; i++) {
			String answerText = answerMap.get(keysArray[i]);
			currentPendingAnswer.add(new MatchingAnswer(-1, answerText, answerCounter++));
		}
		
		
		// Create Question item
		String questionText = (String)request.getParameter("questionText");
		Question currentPendingQuestion = new MatchingQuestion(-1, questionText, currentPendingAnswer.size());
		
		
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
