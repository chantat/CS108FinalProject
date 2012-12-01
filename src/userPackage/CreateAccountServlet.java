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
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


/**
 * Servlet implementation class CreateAccountServlet
 */
@WebServlet("/CreateAccountServlet")
public class CreateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateAccountServlet() {
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
		AccountManager acct = (AccountManager) context.getAttribute("manager");
		String name = request.getParameter("user");
		String pass = request.getParameter("pwd");
		String perfPriv = request.getParameter("privacy1");   //equals null pointer if boxes were not checked, equals "Public" if checked	
		String pagePriv = request.getParameter("privacy2");
		
		if(acct.containsAccount(name)){

			//forward to Name Already Exists page
			RequestDispatcher dispatch = request.getRequestDispatcher("exists.jsp"); 
			dispatch.forward(request, response);
		}
		else{
			//otherwise just add the info to the account manager
	
			
			
			int priv1=1;
			int priv2=1;
			if(perfPriv!=null) priv1=0;
			if(pagePriv!=null) priv2=0;
			acct.addAccount(name, pass, priv1,priv2);
			sess.setAttribute("username", name);   //set this session's user
			sess.setAttribute("mode", "normal");   //set to non-guest mode
			
	//TEST
			acct.dumpTable();
			
			
			//then bring them to the user welcome page
			RequestDispatcher dispatch = request.getRequestDispatcher("userHomePage.jsp"); 
			dispatch.forward(request, response);
	
			
		}
		
		
	}

}
