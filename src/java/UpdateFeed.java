/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.RssReader.Model.DerbyHandler;

/**
 *
 * @author hongzhang
 */
public class UpdateFeed extends HttpServlet {
    
    private ResultSet rs;
    
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
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        //get feed title
        String feedUrl = request.getParameter("feedUrl");
        String logoUrl = request.getParameter("logoUrl");
        String title = request.getParameter("title");
        
        //get user name
        HttpSession session = request.getSession(false);
        String userName = (String)session.getAttribute("userName");
        
        try{
            DerbyHandler databaseHandler = new DerbyHandler();
            
            /* check whether database has this feed or not */
            String sql = "SELECT * FROM feed f \n"
                       + "where f.feedUrl = '" + feedUrl + "'";
            rs = databaseHandler.executeQuery(sql);
            
            if (!rs.next()) {
                // table feed dose not have this feed, so insert it into database
                String insertNewFeed = "INSERT INTO IS2560.FEED (TITLE, FEEDURL, LOGOURL) \n" +
                                       "VALUES ('" + title + "', '" + feedUrl +"', '" + logoUrl + "')";
                
                databaseHandler.executeUpdate(insertNewFeed);
                
                String subscription = "INSERT INTO IS2560.HASFEEDS (USERNAME, FEEDURL) \n" +
                                           "VALUES ('" + userName + "', '" + feedUrl +"')";
                databaseHandler.executeUpdate(subscription);
                
                //send "1" as successful insertation to client
                out.print("1");
            } 
            else {
                //database already has this feed, so check whether this user has subscribed this feed
                String subscriptionQuery = "SELECT * FROM hasfeeds \n"
                                         + "where feedUrl = '" + feedUrl + "' \n" 
                                         + "and   userName = '" + userName + "'";
                rs = databaseHandler.executeQuery(subscriptionQuery);
                
                if (!rs.next()) {
                    // this user did not subscribe this feed, so subscribe it
                    String subscription = "INSERT INTO IS2560.HASFEEDS (USERNAME, FEEDURL) \n" +
                                           "VALUES ('" + userName + "', '" + feedUrl +"')";
                    databaseHandler.executeUpdate(subscription);
                    
                    //send "1" as successful insertation to client
                    out.print("1");
                }
                else {
                    // this user has subscribed this feed, so delete it
                    String deleteSubscription = "DELETE FROM IS2560.HASFEEDS \n" + 
                                                "WHERE USERNAME = '" + userName + "' AND FEEDURL = '" + feedUrl + "'";
                    databaseHandler.executeUpdate(deleteSubscription);
                    
                    //send "2" as successful deleltion to client
                    out.print("2");
                }               
            }
            
            //close database
            databaseHandler.close();
            
        }        
        catch (SQLException se)
        {
            se.printStackTrace(); 
            //send "0" as error message to client
            out.print("0");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            //send "0" as error message to client
            out.print("0");
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
