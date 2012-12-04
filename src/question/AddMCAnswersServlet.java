package question;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import answer.Answer;
import answer.MultipleChoiceAnswer;
import answer.QuestionResponseAnswer;

/**
 * Servlet implementation class AddMCAnswersServlet
 */
@WebServlet("/AddMCAnswersServlet")
public class AddMCAnswersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddMCAnswersServlet() {
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
		System.out.println("request isFinished " + request.getParameter("isFinished"));
		
		ServletContext context = request.getServletContext();
		HttpSession session = request.getSession();
		
		ArrayList<Question> pendingQuestions = (ArrayList<Question>)session.getAttribute("pendingQuestions");
		ArrayList<ArrayList<Answer>> pendingAnswers = (ArrayList<ArrayList<Answer>>)session.getAttribute("pendingAnswers");
		int questionIndex = (Integer)session.getAttribute("editPendingQuestionIndex");
		
		String answerText = (String)request.getParameter("answer");
		
		// TODO: allow multiple answers
		ArrayList<String> answerTexts = new ArrayList<String>();
		answerTexts.add(answerText);
		
		double score = 0.0;
		if((request.getParameter("isCorrectAnswer"))!=null) score=Double.parseDouble(request.getParameter("score"));
		System.out.println("score:"+score);
		int numAnswers=(Integer)session.getAttribute("numAnswers");
		Answer answer = new MultipleChoiceAnswer(-1, answerTexts, numAnswers, score);
		
		ArrayList<Answer> currAnswer;
		if(questionIndex == -1){
			currAnswer=new ArrayList<Answer>();
			currAnswer.add(answer);
			pendingAnswers.add(currAnswer);
		}else{
			currAnswer=pendingAnswers.get(questionIndex);
			currAnswer.add(answer);
			pendingAnswers.set(questionIndex, currAnswer);
		}

		if(request.getParameter("isFinished")==null) request.getRequestDispatcher("MCanswer.jsp").forward(request, response);
		else request.getRequestDispatcher("createQuiz.jsp").forward(request, response);
		
	}

}
