/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.RssReader.Model.DerbyHandler;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author hongzhang
 */
public class Register extends HttpServlet {
    
    private ResultSet rs = null;

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
        
        /* get user name and password */
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        
        /* connect to database and get password back according to userName */
        try{
            DerbyHandler databaseHandler = new DerbyHandler();
            
            String sql = "SELECT * FROM account where userName = '" + userName + "'";
            rs =  databaseHandler.executeQuery(sql);
            
            if(!rs.next()) {
                // userName does not be used, user can use this name
                String insertAccount = "INSERT INTO IS2560.ACCOUNT (USERNAME, PASSWORD) \n" +
                                       "VALUES ('" + userName + "', '" + password +"')";
                //create a new record into database table ACCOUNT
                databaseHandler.executeUpdate(insertAccount);
                
                /* create a session and store userName and password */
                HttpSession session = request.getSession(true);
                session.setAttribute("userName", userName);
                session.setAttribute("password", password);
                //send "1" as success flag back to client
                out.print("1");
            } 
            else {
                //send "0" as an error flag back to client 
                out.print("0");
            }
            
            // close database
            databaseHandler.close();
        }        
        catch (SQLException se)
        {
            se.printStackTrace();  
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
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
