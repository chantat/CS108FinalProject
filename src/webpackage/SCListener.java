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
    	AnnouncementManager anmtmgr = new AnnouncementManager(con);
    	AccountManager acctmgr = new AccountManager(con);
    	AccountUtil acctutil = new AccountUtil(con);
        QuizManager quizManager = new QuizManager(con);
        FriendManager frmgr = new FriendManager(con);
        AchievementManager achmmgr = new AchievementManager(con, acctutil);
        MailSystem ms = new MailSystem(con);
        ServletContext sc = sce.getServletContext();
        sc.setAttribute("manager",acctmgr);
        sc.setAttribute("friendManager",frmgr);
        sc.setAttribute("announcementManager", anmtmgr);
        sc.setAttribute("achievementManager", achmmgr);
        sc.setAttribute("quiz manager", quizManager);
        sc.setAttribute("mailSystem", ms);
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub
    }
	
}
