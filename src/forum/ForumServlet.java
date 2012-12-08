package forum;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import quiz.ReviewManager;

/**
 * Servlet implementation class ForumServlet
 */
@WebServlet("/ForumServlet")
public class ForumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForumServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int quizId = Integer.parseInt(request.getParameter("ID"));
		request.setAttribute("quizId", quizId);
		RequestDispatcher dispatch = request.getRequestDispatcher("forum.jsp");
		dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = request.getServletContext();
		HttpSession session = request.getSession();
		ForumManager fm = (ForumManager)context.getAttribute("forumManager");
		Map<String, String[]> requestMap = request.getParameterMap();
		String postText="";
		postText=requestMap.get("postText")[0];
		//System.out.println("QUIZ ID: " + request.getParameter("quizId"));
		int currentQuiz = Integer.parseInt(request.getParameter("quizId"));
		//System.out.println("FORUM SERVLET: " + postText + " " + currentQuiz);
		String username = (String)session.getAttribute("username");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		if(!postText.isEmpty()){
			fm.createForumPost(username, currentQuiz, postText, timestamp);
		}
		request.setAttribute("quizId", currentQuiz);
		request.getRequestDispatcher("forum.jsp").forward(request, response);
	}

}
