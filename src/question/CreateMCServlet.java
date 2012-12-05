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
		HashMap<Integer, ArrayList<String> > answerMap = new HashMap<Integer, ArrayList<String> >();
		HashMap<Integer, Double > scoreMap = new HashMap<Integer, Double >();
		Map<String, String[]> parameters = request.getParameterMap();
		for(String parameter : parameters.keySet()) {
		    if(parameter.toLowerCase().contains("_answer_")) {
		        String answerText = (String)request.getParameter(parameter);
		        String[] tokens = parameter.split("_");
		        int answerIndex = Integer.parseInt(tokens[0]);		        
		        boolean isOrdered = false;
		        if(parameters.get(answerIndex + "_isCorrect_0") != null) isOrdered=true;
		        
		        ArrayList<String> answerTextList = answerMap.get(answerIndex);
		        Double scoreList = scoreMap.get(answerIndex);
		        if (answerTextList == null) {
		        	answerTextList = new ArrayList<String>();
		        	answerMap.put(answerIndex, answerTextList);
		        	if(isOrdered) scoreMap.put(answerIndex, 1.0);
		        	else scoreMap.put(answerIndex, 0.0);
		        }
		        answerTextList.add(answerText);
		    }
		}
		
		ArrayList<Answer> currentPendingAnswer = new ArrayList<Answer>();
		Set<Integer> keys = answerMap.keySet();
		Integer keysArray[] = keys.toArray(new Integer[keys.size()]);
		Arrays.sort(keysArray);
		
		int answerCounter = 0;
		for (int i = 0; i < keysArray.length; i++) {
			ArrayList<String> answerTextList = answerMap.get(keysArray[i]);
			answerCounter++;
			currentPendingAnswer.add(new MultipleChoiceAnswer(-1, answerTextList, answerCounter, scoreMap.get(keysArray[i])));
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
