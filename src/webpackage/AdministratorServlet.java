package webpackage;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import quiz.*;

import announcement.AnnouncementManager;

import userPackage.AccountManager;

/**
 * Servlet implementation class AdministratorServlet
 */
@WebServlet("/AdministratorServlet")
public class AdministratorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdministratorServlet() {
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
		ServletContext sc = getServletContext();
		
		String function = request.getParameter("function");
		if (function.equals("create_announcement")) {
			AnnouncementManager anmtmgr = (AnnouncementManager)sc.getAttribute("announcementManager");
			String adminId = request.getParameter("adminId");
			String text = request.getParameter("text");
			String subject = request.getParameter("subject");
			anmtmgr.createAnnouncement(adminId, subject, text);
		} else if (function.equals("remove_account")) {
			AccountManager acctmgr = (AccountManager)sc.getAttribute("manager");
			String username = request.getParameter("username");
			acctmgr.deleteAccount(username);
		}  else if (function.equals("clear_quiz_history")) {
			
//TEST
//System.out.println("Attempting to clear quiz history");

			AttemptManager attemptMGR = (AttemptManager)sc.getAttribute("attemptManager");
			int quizID = Integer.parseInt(request.getParameter("quizId"));
			attemptMGR.deleteAllQuizAttempts(quizID); 
			
		} else if (function.equals("promote_admin")) {
			AccountManager acctmgr = (AccountManager)sc.getAttribute("manager");
			String username = request.getParameter("username");
			acctmgr.promoteAdmin(username);
		} else if (function.equals("remove_quiz")) {
			QuizManager quizMGR = (QuizManager)sc.getAttribute("quizManager");
			int quizID = Integer.parseInt(request.getParameter("quizId"));
			quizMGR.deleteQuiz(quizID);
		}

		request.getRequestDispatcher("userHomePage.jsp").forward(request, response);
	}

}
