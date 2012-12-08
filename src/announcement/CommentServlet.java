package announcement;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import quiz.ReviewManager;

/**
 * Servlet implementation class CommentServlet
 */
@WebServlet("/CommentServlet")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentServlet() {
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
		CommentManager commentManager = (CommentManager)context.getAttribute("commentManager");
		Map<String, String[]> requestMap = request.getParameterMap();
		String commentText="";
		commentText=requestMap.get("commentText")[0];
		int announcementID = Integer.parseInt(request.getParameter("announcementID"));
		//System.out.println("COMMENT SERVLET: " + commentText + " " + announcementID);
		String username = (String)session.getAttribute("username");
		Timestamp timeCommented = new Timestamp(System.currentTimeMillis());
		if(!commentText.isEmpty()) commentManager.insertCommentIntoDatabase(announcementID, commentText, username, timeCommented);
		request.getRequestDispatcher("announcements.jsp").forward(request, response);
	}

}
