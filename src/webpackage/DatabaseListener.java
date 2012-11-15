package webpackage;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;


/**
 * Application Lifecycle Listener implementation class DatabaseListener
 *
 */
@WebListener
public class DatabaseListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public DatabaseListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
    	LoginConnectionSetup loginCon = new LoginConnectionSetup();
        AccountManager acctmgr = new AccountManager(loginCon);
        ServletContext servContx = arg0.getServletContext();
        servContx.setAttribute("manager",acctmgr);
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
