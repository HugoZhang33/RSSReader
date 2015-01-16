/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

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
public class Login extends HttpServlet {

    private ResultSet rs = null;
    
    private RequestDispatcher RssDispatcher;
    private RequestDispatcher IndexJsp;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        RssDispatcher = context.getRequestDispatcher("/RssDispatcher");
        IndexJsp = context.getRequestDispatcher("/index.jsp");
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
        
        /* get user name and password, clear whitespace of head and tail */
        String userName = request.getParameter("userName").trim();
        String password = request.getParameter("password").trim();
        
        /* create a session and store userName and password */
        HttpSession session = request.getSession(true);
        session.setAttribute("userName", userName);
        session.setAttribute("password", password);
        
        /* connect to database and get password back according to userName */
        try{
            
            DerbyHandler databaseHandler = new DerbyHandler();
            
            String sql = "SELECT password FROM account where userName = '" + userName + "'";
            rs =  databaseHandler.executeQuery(sql);
            
            if (rs.next()) {                
                // database has this userName, then check the password
                if (rs.getString(1).equals(password)) {
                    // password is correct, login to user page
                    RssDispatcher.forward(request, response);                 
                } else {
                    //password is wrong, alert user
                    request.setAttribute("wrongMeg", "user name or password is wrong, please try again :)");
                    IndexJsp.forward(request, response);
                }
            } else {
                // database does not have this userName, alert user
                request.setAttribute("wrongMeg", "user name or password is wrong, please try again :)");
                IndexJsp.forward(request, response);
            }
            
            /* close database */
            databaseHandler.close();
        }        
        catch (SQLException se)
        {
            System.out.println("sqlexception");
            se.printStackTrace();  
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("classnotfoundexception");
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
        System.out.println("get..............................");
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
        System.out.println("post...............................");
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
