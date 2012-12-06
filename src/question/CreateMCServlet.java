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

import answer.Answer;
import answer.MatchingAnswer;
import answer.MultipleChoiceAnswer;

/**
 * Servlet implementation class CreateMCServlet
 */
@WebServlet("/CreateMCServlet")
public class CreateMCServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateMCServlet() {
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
		String correctAnswer = "";
		for(String parameter : parameters.keySet()) {
		    if(parameter.toLowerCase().contains("_answer_")) {
		        String answerText = (String)request.getParameter(parameter);
		        String[] tokens = parameter.split("_");
		        
		        int answerIndex = Integer.parseInt(tokens[0]);		        
		        
		        String correctAnswerParameter = (String)request.getParameter("correct");
		        if (correctAnswerParameter.equals(parameter)) {
		        	correctAnswer = answerText;
		        }
		        
		        if (!answerMap.containsKey(answerIndex)) {
		        	answerMap.put(answerIndex, answerText);
		        }
		    }
		}
		
		ArrayList<Answer> currentPendingAnswer = new ArrayList<Answer>();
		Set<Integer> keys = answerMap.keySet();
		Integer keysArray[] = keys.toArray(new Integer[keys.size()]);
		Arrays.sort(keysArray);
		
		for (int i = 0; i < keysArray.length; i++) {
			String answerText = answerMap.get(keysArray[i]);
			
			double score = 0;
			if (correctAnswer.equals(answerText)) {
				score = 1;
			}
			currentPendingAnswer.add(new MultipleChoiceAnswer(-1, answerText, score));
		}
		
		
		// Create Question item
		String questionText = (String)request.getParameter("questionText");
		Question currentPendingQuestion = new MultipleChoiceQuestion(-1, questionText);
		
		
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
