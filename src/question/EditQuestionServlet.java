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
 * Servlet implementation class EditQuestionServlet
 */
@WebServlet("/EditQuestionServlet")
public class EditQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditQuestionServlet() {
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
		
		int questionIndex = Integer.parseInt((String)request.getParameter("questionIndex"));
		int questionType = -1;
		if (questionIndex == -1) {
			questionType = Integer.parseInt((String)request.getParameter("questionType"));
		} else {
			questionType = pendingQuestions.get(questionIndex).getType();
		}
		session.setAttribute("editPendingQuestionIndex", questionIndex);
		
		String jspName = null;
		if (questionType == 1) {
			jspName = "createQR.jsp";
		} else if (questionType == 2) {
			jspName = "createFIB.jsp";
		} else if (questionType == 3) {
			jspName = "createMC.jsp";
		} else if (questionType == 4) {
			jspName = "createPR.jsp";
		} else if (questionType == 5) {
			jspName = "createMA.jsp";
		} else if (questionType == 6) {
			jspName = "createMCMA.jsp";
		} else if (questionType == 7) {
			jspName = "createM.jsp";
		}
		
		request.getRequestDispatcher(jspName).forward(request, response);
	}

}
