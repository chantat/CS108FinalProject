package userPackage;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import achievement.AchievementManager;

/**
 * Servlet implementation class RemoveFriendServlet
 */
@WebServlet("/RemoveFriendServlet")
public class RemoveFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveFriendServlet() {
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
		FriendManager frnmgr = (FriendManager) context.getAttribute("friendManager");
		HttpSession hs = request.getSession();
		String name =(String)hs.getAttribute("username");
		String victim = request.getParameter("victim");
		frnmgr.removeFriend(name, victim);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		AchievementManager achMGR = (AchievementManager) context.getAttribute("achievementManager");
		if(!achMGR.isAchieveExist(name, 16)){
			achMGR.insertAchievementIntoDatabase(name, 16, timestamp);
		}
		RequestDispatcher dispatch = request.getRequestDispatcher("userHomePage.jsp"); 
		dispatch.forward(request, response);	
	}

}
