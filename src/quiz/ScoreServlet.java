package quiz;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import question.Question;
import question.QuestionManager;
import userPackage.*;
import achievement.AchievementManager;
import answer.*;

/**
 * Servlet implementation class ScoreServlet
 */
@WebServlet("/ScoreServlet")
public class ScoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScoreServlet() {
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
		AccountManager acctMGR = (AccountManager)context.getAttribute("manager");
		AnswerManager am = (AnswerManager) context.getAttribute("answerManager");
		QuestionManager questionManager = (QuestionManager) context.getAttribute("questionManager");
		AchievementManager achMGR = (AchievementManager) context.getAttribute("achievementManager");
		String username = (String)session.getAttribute("username");
		QuizManager quizManager = (QuizManager)context.getAttribute("quizManager");
		
		Map<String, String[]> requestMap = request.getParameterMap();
		
		int quizId = Integer.parseInt(request.getParameter("currentQuiz"));
		Quiz quiz = quizManager.getQuiz(quizId);
		ArrayList<Integer> questionIds = quiz.getQuestionIds();
		String practiceMode=request.getParameter("allowsPractice");

		double totalScore = 0;
		double totalPossibleScore = 0;
		for (int i = 0; i < questionIds.size(); i++) {
			int qId = questionIds.get(i);
			int index = 0;

			ArrayList<String> userInputs = new ArrayList<String>();

			while (true) {
				String parameterName = qId + "answer" + index;
				if (!requestMap.containsKey(parameterName)) {
					break;
				}

				String userInput[] = request.getParameterValues(parameterName);
				for (int j = 0; j < userInput.length; j++) {
					userInputs.add(userInput[j]);
				}

				index++;
			}

			Question question = questionManager.getQuestion(qId);
			ArrayList<Answer> answers = am.getAnswers(qId);

			totalScore += Answer.scoreUserInput(answers, userInputs);
			totalPossibleScore += question.getNumAnswers();
		}
		if(practiceMode.equals("true")){
			System.out.println(achMGR.checkAchievement(username, 5));
		}else{
			AttemptManager attemptMngr = (AttemptManager)context.getAttribute("attemptManager");
			Timestamp startTime = (Timestamp)session.getAttribute("startTime");
			Timestamp endTime = new Timestamp(System.currentTimeMillis());
			long timeTaken = startTime.getTime() - startTime.getTime();
			attemptMngr.createAttempt(username, quizId, totalScore, (int)timeTaken, endTime);

			if(totalScore> attemptMngr.getQuizHighScore(quizId)){
				acctMGR.setHighScorer(username);
			}
		}
		request.setAttribute("practiceMode", practiceMode);
		request.setAttribute("totalScore", totalScore);
		request.setAttribute("totalPossibleScore", totalPossibleScore);
		request.setAttribute("currentQuiz", quizId);
		request.getRequestDispatcher("scoreQuiz.jsp").forward(request, response);
			// OLD CODE
		/*int numQuestions=0;
		int currentQuestion=0;
		double currentScore=0.0;
		int count = Integer.parseInt(request.getParameter("qID"));
		Map<String, String[]> requestMap=request.getParameterMap();
		String parameterToMatch="entry" + count;
		String entry="";
		String entry2="";
		
		while (true) {
			if(requestMap.containsKey(parameterToMatch)){
				entry = request.getParameter(parameterToMatch);
				entry2 = request.getParameter("entry2" + count);
				currentScore=(Double)request.getAttribute("currentScore");
				ArrayList<Answer> answers = am.getAnswers(Integer.parseInt(request.getParameter(parameterToMatch + "qID")));
				double totalPossibleScore = (Double)request.getAttribute("totalPossibleScore");
				currentQuestion = (Integer)request.getAttribute("currentQuestion");
				numQuestions=(Integer)request.getAttribute("numQuestions");
				//request.setAttribute("currentScore", Answer.scoreUserInput(answers, );
				request.setAttribute("totalPossibleScore", Answer.getPossibleScore(answers) + totalPossibleScore);
			}
			else break;
			count++;
			parameterToMatch="entry" + count;
			currentQuestion++;
		}
		
		
		if(numQuestions==currentQuestion){ //finished quiz
			request.getRequestDispatcher("scoreQuiz.jsp").forward(request, response);
			AttemptManager attemptMngr = (AttemptManager) sc.getAttribute("quizManager");
			HttpSession session = request.getSession();
			String username=(String) session.getAttribute("username");
			String quizID=request.getParameter("quizID");
			attemptMngr.createAttempt(username, Integer.parseInt(quizID), currentScore, new Timestamp(new java.util.Date().getTime()));
			//TODO timestamp
		}else{ //go to next question
			request.setAttribute("currentQuestion", currentQuestion+1);
			request.getRequestDispatcher("displayQuiz.jsp").forward(request, response);
		}*/
	}

}
