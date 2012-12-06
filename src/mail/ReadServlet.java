package mail;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * Servlet implementation class ReadServlet
 */
@WebServlet("/ReadServlet")
public class ReadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReadServlet() {
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
		ServletContext sc = request.getServletContext();
		HttpSession session = request.getSession(); 
		MailSystem ms = (MailSystem) sc.getAttribute("mailSystem");
		String fromID = request.getParameter("fromID");
		String timeStr = request.getParameter("timeStamp");
		String toID = (String)session.getAttribute("username");
		Message msg = ms.findMessage(toID, fromID, timeStr);
		System.out.println("MESSAGE TYPE IS: " + msg.getType());
		if (msg.getType().equals("Challenge")) {
			System.out.println("MESSAGE IS A CHALLENGE");
//			int quizID = ms.findChallengeQuizID(fromID, timeStr);
//			double score = ms.findChallengeQuizID(fromID, timeStr);
//			ChallengeMessage chlg = new ChallengeMessage()
			ChallengeMessage chlg = ms.findChallenge(toID, fromID, timeStr);
			ms.markAsRead(chlg);
			request.setAttribute("message", chlg);
		} else {
			System.out.println("MESSAGE IS A MESSAGE");
			System.out.println(msg.getToID());
			ms.markAsRead(msg);
			request.setAttribute("message", msg);
		}
		request.getRequestDispatcher("readMessage.jsp").forward(request, response);
	}

}
