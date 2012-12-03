package quiz;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ScoreServlet
 */
@WebServlet("/ScoreServlet")
public class ScoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScoreServlet() {
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
		// TODO Auto-generated method stub
		ServletContext sc = request.getServletContext();
		AnswerManager am = (AnswerManager) sc.getAttribute("answerManager");
		
		Answer answer = am.getAnswer(Integer.parseInt(request.getParameter("qID")));
		String entry = request.getParameter("entry");
		//String entry2 = request.getParameter("entry2"); TODO placeholder for later
		//TODO for loop through num parameters
		double currentScore=(Double)request.getAttribute("currentScore");
		double totalPossibleScore=(Double)request.getAttribute("totalPossibleScore");
		int currentQuestion = (Integer)request.getAttribute("currentQuestion");
		int numQuestions=(Integer)request.getAttribute("numQuestions");
		request.setAttribute("currentScore", answer.scoreGuess(entry, "") + currentScore);
		request.setAttribute("totalPossibleScore", answer.getPossibleScore()+totalPossibleScore);
		
		if(numQuestions==currentQuestion){ //finished quiz
			request.getRequestDispatcher("scoreQuiz.jsp").forward(request, response);
			AttemptManager attemptMngr = (AttemptManager) sc.getAttribute("quizManager");
			HttpSession session = request.getSession();
			String username=(String) session.getAttribute("username");
			String quizID=request.getParameter("quizID");
			attemptMngr.createAttempt(username, Integer.parseInt(quizID), currentScore, new Timestamp(new java.util.Date().getTime()));
		}else{ //go to next question
			request.setAttribute("currentQuestion", currentQuestion+1);
			request.getRequestDispatcher("displayQuiz.jsp").forward(request, response);
		}
	}

}
