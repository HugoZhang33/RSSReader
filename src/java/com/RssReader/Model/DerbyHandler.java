/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RssReader.Model;


import com.RssReader.FeedBean.Feed;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * handler of execution statement of Derby database
 * 
 * @author hongzhang
 */
public class DerbyHandler {
    
    private Connection conn;
    private Statement st;
    private ResultSet rs;
    
    public DerbyHandler() throws ClassNotFoundException, SQLException {        
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        String connectionURL = "jdbc:derby://localhost:1527/hoz26";
        conn = DriverManager.getConnection(connectionURL, "IS2560", "IS2560");
        st = conn.createStatement();                              
    }
    
    public ResultSet executeQuery(String _sql) throws SQLException {
        rs =  st.executeQuery(_sql);
        return rs;
    } 
    
    public void executeUpdate(String _sql) throws SQLException {
        st.executeUpdate(_sql);
    } 
    
    public void close() throws SQLException {       
        rs.close();
        st.close();
        conn.close();
    }
    
}
