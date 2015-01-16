<%-- 
    Document   : logincheck
    Created on : Dec 7, 2014, 12:28:56 PM
    Author     : hongzhang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%  
    if(session.getAttribute("userName") == null) {  
        //if request does not have a session, redirect it to login page
%>  
        <script type="text/javascript" language="javascript">  
            alert("you do not login, please lgoin...");  
            window.document.location.href="index.jsp";  
        </script>   
<%  
    }  
%>  
