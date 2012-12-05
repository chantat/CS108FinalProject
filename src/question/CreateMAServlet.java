package question;

import java.io.IOException;
import java.util.*;

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
@WebServlet("/CreateMAServlet")
public class CreateMAServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateMAServlet() {
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
		Map<String, String[]> parameters = request.getParameterMap();
		for(String parameter : parameters.keySet()) {
		    if(parameter.toLowerCase().contains("_answer_")) {
		        String answerText = (String)request.getParameter(parameter);
		        String[] tokens = parameter.split("_");
		        int answerIndex = Integer.parseInt(tokens[0]);
		        
		        ArrayList<String> answerTextList = answerMap.get(answerIndex);
		        if (answerTextList == null) {
		        	answerTextList = new ArrayList<String>();
		        	answerMap.put(answerIndex, answerTextList);
		        }
		        answerTextList.add(answerText);
		    }
		}
		
		ArrayList<Answer> currentPendingAnswer = new ArrayList<Answer>();
		Set<Integer> keys = answerMap.keySet();
		Integer keysArray[] = (Integer[])keys.toArray();
		Arrays.sort(keysArray);
		
		boolean isOrdered = parameters.containsKey("isOrdered");
		int answerCounter = 0;
		for (int i = 0; i < keysArray.length; i++) {
			ArrayList<String> answerTextList = answerMap.get(keysArray[i]);
			int answerOrder = -1;
			if (isOrdered) {
				answerOrder = answerCounter++;
			}
			currentPendingAnswer.add(new MultipleAnswerAnswer(-1, answerTextList, answerOrder));
		}
		
		
		// Create Question item
		int numAnswers = Integer.parseInt(request.getParameter("numAnswers"));
		String questionText = (String)request.getParameter("questionText");
		Question currentPendingQuestion = new MultiAnswerQuestion(-1, questionText, numAnswers, isOrdered);
		
		
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
