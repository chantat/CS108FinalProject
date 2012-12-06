package quiz;

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

/**
 * Servlet implementation class ReviewServlet
 */
@WebServlet("/ReviewServlet")
public class ReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewServlet() {
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
		ReviewManager reviewManager = (ReviewManager)context.getAttribute("reviewManager");
		Map<String, String[]> requestMap = request.getParameterMap();
		String reviewText="";
		reviewText=requestMap.get("reviewText")[0];
		int currentQuiz = Integer.parseInt(request.getParameter("quizId"));
		System.out.println("REVIEW SERVLET: " + reviewText + " " + currentQuiz);
		String username = (String)session.getAttribute("username");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		reviewManager.createReview(username, currentQuiz, reviewText, timestamp);
		request.getRequestDispatcher("thankYouForRating.jsp").forward(request, response);
	}

}
