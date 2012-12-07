package mail;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import userPackage.FriendManager;
import quiz.*;

/**
 * Servlet implementation class ChallengeServlet
 */
@WebServlet("/ChallengeServlet")
public class ChallengeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChallengeServlet() {
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
		MailSystem mail = (MailSystem) context.getAttribute("mailSystem");
		QuizManager qm = (QuizManager) context.getAttribute("quizManager");
		HttpSession hs = request.getSession();
		String name =(String)hs.getAttribute("username");
		String victim = request.getParameter("victim");
		
		//notify the recipient with a mail system message
//		String quizIDStr = request.getParameter("quizId"); // Quiz ID is recorded as message text in database
//		Message requestMsg = new Message(victim, name, "Challenge!", quizIDStr, "Challenge");
		int quizID = Integer.parseInt(request.getParameter("quizId"));
		String quizStr = qm.getQuizName(quizID);
		double score = Double.parseDouble(request.getParameter("score"));
		double possibleScore = Double.parseDouble(request.getParameter("possibleScore"));
		String message = name + " has challenged you to a the quiz ";
		message += quizStr;
		message += "! Can you beat a score of " + score + "/" + possibleScore +"?";
		ChallengeMessage challengeMsg = new ChallengeMessage(victim, name, message, quizID, score);
		mail.send(challengeMsg);
		
		request.getRequestDispatcher("quizHomepage.jsp").forward(request, response);
	}

}
