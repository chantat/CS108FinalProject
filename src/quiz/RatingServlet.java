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

import achievement.AchievementManager;

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
		ReviewManager reviewManager = (ReviewManager)context.getAttribute("reviewManager");
		ReportManager reportMGR = (ReportManager)context.getAttribute("reportManager");
		Map<String, String[]> requestMap = request.getParameterMap();
		String username = (String)session.getAttribute("username");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		int ratingReceived=0;
		if(requestMap.get("grade")!= null){
			ratingReceived=Integer.parseInt(requestMap.get("grade")[0]);
		}
		
		int currentQuiz = Integer.parseInt(request.getParameter("quizId"));
		if(request.getParameter("reportFlag")!=null){  //if checkbox was checked
//TEST
System.out.println("OMGGGGG FLAGGED QUIZ");
			
			
			AchievementManager achMGR = (AchievementManager) context.getAttribute("achievementManager");
			if(!achMGR.isAchieveExist(username, 19)){
				achMGR.insertAchievementIntoDatabase(username, 19, timestamp);
			}
			
			if(reportMGR.isReportExist(currentQuiz)){
				reportMGR.incrementOccurrence(currentQuiz);
			}
			else{
				reportMGR.createReport(currentQuiz, 1, timestamp);
			}	
		}
			
		
		
		
		String reviewText="";
		reviewText=requestMap.get("reviewText")[0];
		if(!reviewText.isEmpty()){
			reviewManager.createReview(username, currentQuiz, reviewText, timestamp);
		}
		
		System.out.println("RATING SERVLET: " + currentQuiz + " " + ratingReceived + " " + request.getParameter("quizId") + " " + reviewText);
		
		if(ratingReceived != 0) ratingManager.createRating(username, currentQuiz, ratingReceived, timestamp);
		request.getRequestDispatcher("thankYouForRating.jsp").forward(request, response);
	}

}
