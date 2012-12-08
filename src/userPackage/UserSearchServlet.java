package userPackage;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UserSearchServlet
 */
@WebServlet("/UserSearchServlet")
public class UserSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserSearchServlet() {
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
		AccountManager acct = (AccountManager) context.getAttribute("manager");
		FriendManager fmgr = (FriendManager) context.getAttribute("friendManager");
		String victimName = request.getParameter("victim");
		if(!acct.containsAccount(victimName)){  //if nonexistent
			//forward user to the User Does Not Exist
			request.setAttribute("err", "doesNotExist");
			RequestDispatcher dispatch = request.getRequestDispatcher("userHomePage.jsp"); 
			dispatch.forward(request, response);
		} else if (!fmgr.areFriends(victimName, (String)session.getAttribute("username")) && !acct.isPagePublic(victimName)) {
			request.setAttribute("err", "userPrivate");
			RequestDispatcher dispatch = request.getRequestDispatcher("userHomePage.jsp"); 
			dispatch.forward(request, response);
		}
		else{
			//forward user to the dynamically generated user info page of their desired user
			RequestDispatcher dispatch = request.getRequestDispatcher("userInfoPage.jsp"); 
			dispatch.forward(request, response);
		}
	}

}
