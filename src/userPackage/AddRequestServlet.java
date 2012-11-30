package userPackage;

import mail.*;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mail.Message;

/**
 * Servlet implementation class AddRequestServlet
 */
@WebServlet("/AddRequestServlet")
public class AddRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddRequestServlet() {
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
		FriendManager frnmgr = (FriendManager) context.getAttribute("friendManager");
		HttpSession hs = request.getSession();
		String name =(String)hs.getAttribute("username");
		String victim = request.getParameter("victim");
		frnmgr.requestFriend(name, victim);
		
		//notify the recipient with a mail system message
		String messageTxt = "You have a pending request from "+name+".  Please check your Friends List to accept or reject this request.";
		Message requestMsg = new Message(victim, name, "Friend Request", messageTxt, "Request");
		mail.send(requestMsg);
		
		RequestDispatcher dispatch = request.getRequestDispatcher("userInfoPage.jsp"); 
		dispatch.forward(request, response);
	}

}
