package quiz;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class RatingServlet
 */
@WebServlet("/RatingServlet")
public class RatingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RatingServlet() {
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
		RatingManager ratingManager = (RatingManager)context.getAttribute("ratingManager");
		Map<String, String[]> requestMap = request.getParameterMap();
		int ratingReceived=0;
		for(int i = 0; i < ratingManager.RATING_MAXIMUM; i++){
			int grade = i+1;
			if(requestMap.containsKey("" + grade)){
				ratingReceived=grade;
				System.out.println(grade);
			}
		}
		int currentQuiz = Integer.parseInt(request.getParameter("quizId"));
		System.out.println("RATING SERVLET: " + currentQuiz);
		String username = (String)session.getAttribute("username");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		ratingManager.createRating(username, currentQuiz, ratingReceived, timestamp);
		request.getRequestDispatcher("thankYouForRating.jsp").forward(request, response);
	}

}
