package quiz;

import java.io.IOException;

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
		String entry2 = request.getParameter("entry2");
		double currentScore=(Double)request.getAttribute("currentScore");
		double totalPossibleScore=(Double)request.getAttribute("totalPossibleScore");
		request.setAttribute("currentScore", answer.scoreGuess(entry, entry2) + currentScore);
		request.setAttribute("totalPossibleScore", answer.getPossibleScore()+totalPossibleScore);
		request.getRequestDispatcher("scoreQuiz.jsp").forward(request, response);
	}

}
