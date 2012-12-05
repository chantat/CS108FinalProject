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

/**
 * Servlet implementation class CreateMCMAServlet
 */
@WebServlet("/CreateMCMAServlet")
public class CreateMCMAServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateMCMAServlet() {
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
		int questionIndex = (Integer)session.getAttribute("editPendingQuestionIndex");
		
		String questionText = (String)request.getParameter("questionText");
		
		Question question = new MultiChoiceMultiAnswerQuestion(-1, questionText, Integer.parseInt(request.getParameter("numCorrectAnswers")));
		if (questionIndex == -1) {
			pendingQuestions.add(question);
		} else {
			pendingQuestions.set(questionIndex, question);
		}
		session.setAttribute("numAnswers", 0);
		request.getRequestDispatcher("MCMAanswer.jsp").forward(request, response);
	}

}
