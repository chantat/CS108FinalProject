package userPackage;

import java.io.*;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;




/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		AccountManager acct = (AccountManager) context.getAttribute("manager");
		
//TEST
    	acct.promoteAdmin("allen");
		String name = request.getParameter("user");
		String pass = request.getParameter("pwd");
		
		
//TEST
		acct.dumpTable();
		
		
		if(acct.containsAccount(name) && acct.passwordMatch(name, pass)){
			HttpSession session = request.getSession();
			session.setAttribute("username", name);     //store the username for this session so all pages and servlets can access.
			session.setAttribute("mode", "normal");     //set to non-guest mode
			if(acct.isDeact(name)){
				//forward to the welcome back reactivation page  
				acct.reactivate(name);
				RequestDispatcher dispatch = request.getRequestDispatcher("reactivate.jsp"); 
				dispatch.forward(request, response);	
				
			}
			else{
				//forward to the user home page  (dynamically generated since it's so short)		
				RequestDispatcher dispatch = request.getRequestDispatcher("userHomePage.jsp"); 
				dispatch.forward(request, response);	
			}
		}
		else{
			//forward user to the try again page
			RequestDispatcher dispatch = request.getRequestDispatcher("tryAgain.jsp"); 
			dispatch.forward(request, response);
			
		}
	}

}
