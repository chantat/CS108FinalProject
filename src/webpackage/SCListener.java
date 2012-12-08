package webpackage;

import javax.servlet.*;
import javax.servlet.annotation.*;

import forum.ForumManager;

import question.QuestionManager;
import quiz.AttemptManager;
import quiz.QuizManager;
import quiz.RatingManager;
import quiz.ReviewManager;
import achievement.AchievementManager;
import announcement.AnnouncementManager;
import announcement.CommentManager;
import answer.AnswerManager;
import quiz.*;


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
        QuestionManager qm = new QuestionManager(con);
        AnswerManager am = new AnswerManager(con);
        AttemptManager attemptManager = new AttemptManager(con);
        RatingManager ratingManager = new RatingManager(con);
        ReviewManager reviewManager = new ReviewManager(con);
        ReportManager reportManager = new ReportManager(con);
        ForumManager forumManager = new ForumManager(con);
        CommentManager commentManager = new CommentManager(con);
        
        ServletContext sc = sce.getServletContext();
        sc.setAttribute("manager",acctmgr);
        sc.setAttribute("friendManager",frmgr);
        sc.setAttribute("announcementManager", anmtmgr);
        sc.setAttribute("achievementManager", achmmgr);
        sc.setAttribute("quizManager", quizManager);
        sc.setAttribute("mailSystem", ms);
        sc.setAttribute("questionManager", qm);
        sc.setAttribute("answerManager", am);
        sc.setAttribute("attemptManager", attemptManager);
        sc.setAttribute("ratingManager", ratingManager);
        sc.setAttribute("reviewManager", reviewManager);
        sc.setAttribute("reportManager",reportManager);
        sc.setAttribute("forumManager", forumManager);
        sc.setAttribute("commentManager", commentManager);
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub
    }
	
}
