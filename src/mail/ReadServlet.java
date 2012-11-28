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
		MailSystem ms = (MailSystem) sc.getAttribute("mailSystem");
		String fromID = request.getParameter("fromID");
		String timeStr = request.getParameter("timeStamp");
		Message msg = ms.findMessage(fromID, timeStr);
		//System.out.println(msg);
		ms.markAsRead(msg);
		request.setAttribute("message", msg);
		request.getRequestDispatcher("readMessage.jsp").forward(request, response);
	}

}
