/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.RssReader.FeedBean.Feed;
import com.RssReader.Model.DerbyHandler;
import com.RssReader.Model.RssFetcher;
import com.sun.syndication.io.FeedException;
import java.net.MalformedURLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
/**
 *
 * @author hongzhang
 */
public class RssDispatcher extends HttpServlet {
    
    private ResultSet rs = null;
    
    private RequestDispatcher myfeedJsp;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        myfeedJsp = context.getRequestDispatcher("/myfeed.jsp");
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //if no session, do not allow to create a new one
        HttpSession session = request.getSession(false);
        
        /* get user name */
        String userName = (String)session.getAttribute("userName");
        
        /* this ArrayList stores each feed's feedUrl, logoUrl and title */
        ArrayList<Feed> categoryFeeds = new ArrayList<Feed>();
        
        /** 
         * connect to database and get all feeds that subscribed by a user
         * according to userName 
         */
        try{
            DerbyHandler databaseHandler = new DerbyHandler();
                       
            String sql = "SELECT * FROM feed f, hasFeeds h \n"
                       + "where h.userName = '" + userName + "' \n"
                       + "and f.feedUrl = h.feedUrl";
            rs =  databaseHandler.executeQuery(sql);
            
            while (rs.next()) {
                //this user has kept some feeds in database
                Feed feed = new Feed(rs.getString("title"), rs.getString("feedUrl"), rs.getString("logoUrl"));
                categoryFeeds.add(feed);
            
            }
            
            // close database
            databaseHandler.close();
        }        
        catch (SQLException se)
        {
            se.printStackTrace();  
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("classnotfoundexception");
        }
        
        /* set default feed for user */ 
        String defaultFeed = categoryFeeds.isEmpty() ? "http://news.yahoo.com/rss/tech" : categoryFeeds.get(0).getFeedUrl();
        
        /* get feed url */
        String url = request.getParameter("feedUrl");
        //check the feedUrl's data
        url = url == null || url.equals("") ? defaultFeed : url;
        
        RssFetcher fetcher = new RssFetcher();
        Feed feed = null;
        
        // try to fetche a feed
        try {
            feed = fetcher.fetchFeed(url);
            request.setAttribute("userName", userName);
            request.setAttribute("title", feed.getTitle());
            request.setAttribute("entries", feed.getEntries());
            request.setAttribute("link", feed.getLink());
            request.setAttribute("categoryFeeds", categoryFeeds);
            request.setAttribute("feedUrl", url);
            request.setAttribute("logoUrl", feed.getLogoUrl());
            if ("y".equals(request.getParameter("remind"))) 
                request.setAttribute("remind", "wrong feed url, please try again:)");
            myfeedJsp.forward(request, response);
            
        } catch (MalformedURLException | IllegalArgumentException | FeedException ex) {
            try {
                // fail to fetch a feed, use default feed back to client
                response.sendRedirect("RssDispatcher?remind=y");
            } catch (Exception ex1) {               
                Logger.getLogger(RssDispatcher.class.getName()).log(Level.SEVERE, null, ex1);
            }           
            Logger.getLogger(RssDispatcher.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
