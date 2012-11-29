package userPackage;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ChangePrivacyServlet
 */
@WebServlet("/ChangePrivacyServlet")
public class ChangePrivacyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePrivacyServlet() {
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
		HttpSession sess = request.getSession();
		String user = (String)sess.getAttribute("username");
		AccountManager acct = (AccountManager) context.getAttribute("manager");
		String perfPriv = request.getParameter("privacy1");   //equals null pointer if boxes were not checked, equals "Public" if checked	
		String pagePriv = request.getParameter("privacy2");  //aka null if want public
		
		int privPerf=1;    //by default, start as private
		int privPage=1;
		if(perfPriv!=null) privPerf=0;  //make public if checkboxes were checked
		if(pagePriv!=null) privPage=0;
		acct.setPriv(user, privPerf, privPage);
		
		//back to homepage
		RequestDispatcher dispatch = request.getRequestDispatcher("userHomePage.jsp"); 
		dispatch.forward(request, response);
	}

}
