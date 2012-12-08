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
import mail.*;

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
		ArrayList<Integer> questionIds = (ArrayList<Integer>)session.getAttribute("currentQuestionOrder");
		String practiceMode=(String)session.getAttribute("practiceMode");
		ArrayList<Integer> numTimesCorrect=null;
		if(practiceMode.equals("true")){
			numTimesCorrect=(ArrayList<Integer>)session.getAttribute("practiceQuestionsCounter");
			questionIds=(ArrayList<Integer>) session.getAttribute("practiceQuestionIds");
		}

		double totalScore = 0;
		double totalPossibleScore = 0;
		
		if (quiz.getIsFlashcard()) {
			int currQuest = Integer.parseInt(request.getParameter("currentQuestion"));
			if (currQuest == 0) {
				session.setAttribute("currScore", 0.0);
				session.setAttribute("currPossibleScore", 0.0);
				session.setAttribute("currTimeTaken", (long)0);	
				
			}
			double currScore = (Double)session.getAttribute("currScore");
			double currPossibleScore = (Double)session.getAttribute("currPossibleScore");
			
			int qId = questionIds.get(currQuest);
			System.out.println("CURRRENT QUESTION: " + qId);
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
			
			double toIncrement=Answer.scoreUserInput(answers, userInputs);
			currScore += toIncrement;
			currPossibleScore += question.getNumAnswers();
			
			if (quiz.getImmediateFeedback()) {
				if (Answer.scoreUserInput(answers, userInputs) != question.getNumAnswers()) {
					session.setAttribute("prevAnswer", "incorrect");
					session.setAttribute("prevCorrectAnswers", answers);
					session.setAttribute("prevUserAnswers", userInputs);
				} else {
					session.setAttribute("prevAnswer", "correct");
				}
			}
			
			//PRACTICE MODE FOR FLASHCARDS
			if(practiceMode.equals("true")){
				if(toIncrement >0){
					numTimesCorrect.set(currQuest, numTimesCorrect.get(currQuest)+1);
					System.out.println("FROM SCORE SERVLET FLASHCARD: INCREMENTING " + currQuest + " " + numTimesCorrect.get(currQuest));
				}
			}
			
			Timestamp start = (Timestamp)session.getAttribute("startTime");
			if (currQuest == 0) {
				session.setAttribute("lastTime", start.getTime());
			}
			long lastTime = (Long)session.getAttribute("lastTime");
			long inc = System.currentTimeMillis() - lastTime;
			long curTime = (Long)session.getAttribute("currTimeTaken");
			session.setAttribute("currTimeTaken", curTime+inc);
			session.setAttribute("lastTime", System.currentTimeMillis());
			
			session.setAttribute("currScore", currScore);
			session.setAttribute("currPossibleScore", currPossibleScore);
			
			if ((!(currQuest == quiz.getNumQuestions() - 1) && practiceMode.equals("false")) || (practiceMode.equals("true") && currQuest != numTimesCorrect.size()-1)) {
				//System.out.println("MOVING FROM: " + currQuest + " " + numTimesCorrect.size() + " " + quiz.getNumQuestions());
				request.setAttribute("currentQuestion", currQuest+1);
				request.setAttribute("practiceMode", practiceMode);
				request.setAttribute("totalScore", currScore);
				request.setAttribute("totalPossibleScore", currPossibleScore);
				request.setAttribute("currentQuiz", request.getParameter("currentQuiz"));
				request.setAttribute("currentTimeTaken", curTime+inc);
				request.getRequestDispatcher("displayQuiz.jsp").forward(request, response);
				return;
			}
			totalScore = currScore;
			totalPossibleScore = currPossibleScore;	
		
		}
		
		if (!quiz.getIsFlashcard()) {
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
				double scoreToIncrement=Answer.scoreUserInput(answers, userInputs);
				
				if(practiceMode.equals("true")){
					if(scoreToIncrement >0){
						numTimesCorrect.set(i, numTimesCorrect.get(i)+1);
						System.out.println("FROM SCORE SERVLET NOT FLASHCARD: INCREMENTING " + i + " " + numTimesCorrect.get(i));
					}
				}
				
				totalScore += scoreToIncrement;
				totalPossibleScore += question.getNumAnswers();
			}
		}
		
		Double percent = 100.0 * totalScore / totalPossibleScore;
		
		if(practiceMode.equals("true")){
			System.out.println(achMGR.checkAchievement(username, 5));
			session.setAttribute("practiceQuestionsCounter", numTimesCorrect);
			session.setAttribute("practiceQuestionIds", questionIds);
		}else{
			AttemptManager attemptMngr = (AttemptManager)context.getAttribute("attemptManager");
			Timestamp startTime = (Timestamp)session.getAttribute("startTime");
			Timestamp endTime = new Timestamp(System.currentTimeMillis());
			long timeTaken = endTime.getTime() - startTime.getTime();
			
			if (quiz.getIsFlashcard()) {
				timeTaken = (Long)session.getAttribute("currTimeTaken");
			}
			
			request.setAttribute("timeTaken", (int)(timeTaken/1000));
			attemptMngr.createAttempt(username, quizId, percent, (int)(timeTaken/1000), endTime);

			
//TEST
	System.out.println("high score on quiz id "+quizId+" is "+ attemptMngr.getQuizHighScore(quizId)+ "and your score: "+ totalScore);		
			
			if(totalScore>= attemptMngr.getQuizHighScore(quizId)){
				
//TEST
	System.out.println("NEW HIGH SCORE");
				acctMGR.setHighScorer(username);
			}
		}
		request.setAttribute("practiceMode", practiceMode);
		request.setAttribute("totalScore", totalScore);
		request.setAttribute("totalPossibleScore", totalPossibleScore);
		request.setAttribute("currentQuiz", quizId);
		/* If user took this quiz as a challenge, send a message to challenger. */
		if (request.getParameterMap().containsKey("challenger")) {
			String challenger = request.getParameter("challenger");
			Double challengerScore = Double.parseDouble(request.getParameter("challengerScore"));
			if (!challenger.equals("") && challengerScore != -1) {
				MailSystem ms = (MailSystem) context.getAttribute("mailSystem");
				String subject = username + " has accepted your challenge!";
				String message = username + " took the quiz " + quiz.getName();
				if (totalScore > challengerScore)
					message += " and beat you ";
				else if (totalScore < challengerScore)
					message += ", but couldn't beat you ";
				else
					message += " and tied you ";
				String percentString = String.format("%.2f", percent);
				message += "with a score of " + percentString + "%!";
				Message msg = new Message(challenger, username, subject, message, "Message");
				ms.send(msg);
			}
		}
		session.setAttribute("practiceQuestionsCounter", numTimesCorrect);
		session.setAttribute("practiceQuestionIds", questionIds);
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
		}else{ //go to next question
			request.setAttribute("currentQuestion", currentQuestion+1);
			request.getRequestDispatcher("displayQuiz.jsp").forward(request, response);
		}*/
	}

}
