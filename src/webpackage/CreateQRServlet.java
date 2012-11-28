package webpackage;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
/**
 * Servlet implementation class CreateQRServlet
 */
@WebServlet("/CreateQRServlet")
public class CreateQRServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateQRServlet() {
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
		ServletContext sc = getServletContext();
		Statement stmt = (Statement)sc.getAttribute("stmt");
		
		String qText = request.getParameter("qText");
		String answerText = request.getParameter("answerText");
		int qID = Integer.parseInt(request.getParameter("qID"));
		
		try {
			stmt.executeUpdate("insert into Question values (" + qID + ", 1);");
			stmt.executeUpdate("insert into QR values (" + qID + ", '" + qText + "');");
			stmt.executeUpdate("insert into Answer values(" + qID + ", '" + answerText + "', 1.0);");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("createQR.jsp").forward(request, response);
	}

}
