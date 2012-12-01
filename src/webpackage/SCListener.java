package webpackage;

import javax.servlet.*;
import javax.servlet.annotation.*;

import achievement.AchievementManager;
import announcement.AnnouncementManager;


import userPackage.*;
import mail.*;


/**
 * Application Lifecycle Listener implementation class SCListener
 *
 */
@WebListener
public class SCListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public SCListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce) {
    	DBConnection con = new DBConnection();
        AccountManager acctmgr = new AccountManager(con);
        QuizManager quizManager = new QuizManager(con);
        ServletContext sc = sce.getServletContext();
        sc.setAttribute("manager",acctmgr);
        sc.setAttribute("quiz manager", quizManager);
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub
    }
	
}
