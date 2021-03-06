package mail;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import userPackage.*;

/**
 * Servlet implementation class MailServlet
 */
@WebServlet("/MailServlet")
public class MailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MailServlet() {
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
		FriendManager fm = (FriendManager) sc.getAttribute("friendManager");
		String toIDStr = request.getParameter("toID");
		toIDStr = toIDStr.replaceAll("\\s", "");
		String toIDList[] = toIDStr.split(",");
		String subject = request.getParameter("subject");
		String message = request.getParameter("message");
		String user = (String) session.getAttribute("username");
		for (int i = 0; i < toIDList.length; i++) {
			String toID = toIDList[i];
			if (fm.areFriends(user, toID)) {
				Message msg = new Message(toID, user, subject, message, "Message");
				ms.send(msg);
			}
		}
		request.getRequestDispatcher("userHomePage.jsp").forward(request, response);
	}

}
