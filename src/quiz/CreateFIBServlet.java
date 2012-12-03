package quiz;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CreateFIBServlet
 */
@WebServlet("/CreateFIBServlet")
public class CreateFIBServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateFIBServlet() {
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
		
		ArrayList<Question> pendingQuestions = (ArrayList<Question>)session.getAttribute("pendingQuestions");
		ArrayList<String> pendingAnswers = (ArrayList<String>)session.getAttribute("pendingAnswers");
		int questionIndex = (int)session.getAttribute("editPendingQuestionIndex");
		
		String questionText = (String)request.getParameter("questionText");
		String answer = (String)request.getParameter("answer");
		
		Question question = new FillInTheBlank(-1, questionText);
		if (questionIndex == -1) {
			pendingQuestions.add(question);
			pendingAnswers.add(answer);
		} else {
			pendingQuestions.set(questionIndex, question);
			pendingAnswers.set(questionIndex, answer);
		}
		
		request.getRequestDispatcher("createQuiz.jsp").forward(request, response);
	}

}
