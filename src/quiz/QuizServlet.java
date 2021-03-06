package quiz;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import userPackage.AccountManager;

/**
 * Servlet implementation class QuizServlet
 */
@WebServlet("/QuizServlet")
public class QuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizServlet() {
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
		QuizManager qm = (QuizManager) sc.getAttribute("quizManager");
		HttpSession session = request.getSession();
		String quizId = request.getParameter("quizId");
		request.setAttribute("currentQuiz", quizId);
		session.setAttribute("currentScore", 0);
		request.setAttribute("totalPossibleScore", 0);
		request.setAttribute("currentQuestion", 0);
		session.setAttribute("currentQuestionOrder", null);
		request.setAttribute("numQuestions", qm.getNumQuestions(Integer.parseInt(quizId)));		
		boolean allowsPractice = qm.getQuizAllowsPractice(Integer.parseInt(quizId));
		
		
		if(request.getParameterMap().containsKey("practiceMode")){//just asked the user
			if(request.getParameter("practiceMode").equals("true")){
				session.setAttribute("practiceMode", "true");
				ArrayList<Integer> numTimesCorrect=new ArrayList<Integer>();
				ArrayList<Integer> practiceQuestionIds =new ArrayList<Integer>();
				//ArrayList<ArrayList<String>> practiceQuestionResponses =new ArrayList<ArrayList<String>>();
				session.setAttribute("practiceQuestionsCounter", numTimesCorrect);
				session.setAttribute("practiceQuestionIds", practiceQuestionIds);
				String user = (String)session.getAttribute("username");
				AccountManager acctMGR = (AccountManager)sc.getAttribute("manager");
				acctMGR.setPracticed(user);
			}else if(request.getParameter("practiceMode").equals("ongoing")){
				session.setAttribute("practiceMode", "true");
			}else{
				session.setAttribute("practiceMode", "false");
			}
			Timestamp startTime = new Timestamp(System.currentTimeMillis());
			session.setAttribute("startTime", startTime);
			//System.out.println(startTime);
			request.getRequestDispatcher("displayQuiz.jsp").forward(request, response);
			return;
		}else{
			session.setAttribute("practiceMode", "false");
			if(allowsPractice){
				request.getRequestDispatcher("practiceMode.jsp").forward(request, response);
			}else{
				Timestamp startTime = new Timestamp(System.currentTimeMillis());
				session.setAttribute("startTime", startTime);
				//System.out.println(startTime);
				request.getRequestDispatcher("displayQuiz.jsp").forward(request, response);
			}
		}
		
		
		
		
		
		/*if(!request.getParameterMap().containsKey("practiceMode")){
			if(allowsPractice){
				request.getRequestDispatcher("practiceMode.jsp").forward(request, response);
			}else{
				request.setAttribute("practiceMode", "false");
				Timestamp startTime = new Timestamp(System.currentTimeMillis());
				session.setAttribute("startTime", startTime);
				System.out.println(startTime);
				request.getRequestDispatcher("displayQuiz.jsp").forward(request, response);
			}
		}else{
			String practiceMode=request.getParameter("practiceMode");
			request.setAttribute("practiceMode", practiceMode);
			//playing in practice mode
			if(practiceMode.equals("true")){
				//first time
				if(session.getAttribute("practiceQuestionsCounter")==null){
					ArrayList<Integer> numTimesCorrect=new ArrayList<Integer>();
					ArrayList<Integer> practiceQuestionIds =new ArrayList<Integer>();
					ArrayList<ArrayList<String>> practiceQuestionResponses =new ArrayList<ArrayList<String>>();
					session.setAttribute("practiceQuestionsCounter", numTimesCorrect);
					session.setAttribute("practiceQuestionIds", practiceQuestionIds);
				}
			}
			String user = (String)session.getAttribute("username");
			AccountManager acctMGR = (AccountManager)sc.getAttribute("manager");
			acctMGR.setPracticed(user);
			request.setAttribute("practiceMode", request.getParameter("practiceMode"));
			Timestamp startTime = new Timestamp(System.currentTimeMillis());
			session.setAttribute("startTime", startTime);
			System.out.println(startTime);
			request.getRequestDispatcher("displayQuiz.jsp").forward(request, response);
		}*/
	}
}
