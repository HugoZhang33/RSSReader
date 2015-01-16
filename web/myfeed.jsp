<%-- 
    Document   : myfeed
    Created on : Dec 5, 2014, 11:42:57 PM
    Author     : hongzhang
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.sun.syndication.feed.synd.SyndFeed" %> 
<%@ page import="com.sun.syndication.feed.synd.SyndEntry" %> 
<%@ page import="java.util.Iterator" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="logincheck.jsp" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>RssReader</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- Loading Bootstrap -->
        <link href="./dist/css/vendor/bootstrap.min.css" rel="stylesheet">

        <!-- Loading Flat UI -->
        <link href="./dist/css/flat-ui.min.css" rel="stylesheet">
        <link rel="shortcut icon" href="">
        
        <!-- Loading myfeed CSS -->
        <link href="./media/css/myfeed.css" rel="stylesheet">
        
        <!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
        <!--[if lt IE 9]>
          <script src="js/vendor/html5shiv.js"></script>
          <script src="js/vendor/respond.min.js"></script>
        <![endif]-->       
  </head>
  <body class="body">
      <div class="secondary">
          <form class="navbar-form feed-form" action="RssDispatcher" method="POST" role="search">
            <div class="form-group">
                <div class="input-group" id="feed-form-width">
                <input class="form-control" id="navbarInput-01" name="feedUrl" type="search" placeholder="Search  Feed">
                <span class="input-group-btn" >
                  <button type="submit" class="btn" id="search-btn"><span class="fui-search"></span></button>
                </span>
              </div>
              <p id="urlRemind">${remind}</p>
            </div>
          </form>
          <ul>
              <li>
                  <span class="fui-user"></span>
                  <a id="userName">${userName}</a>
              </li>
              <li>
                  <span class="fui-heart"></span>
                  <a id="favorite" title="${feedUrl}">Favorite</a>
                  <span style="display:none">${logoUrl}</span>
              </li>              
              <li>
                  <span class="fui-exit"></span>
                  <a href="./Logout">Log Out</a>
              </li>
          </ul>
      </div>
      <div class="container">
        <div class="feed-nav">
            <a class="" id="feedsButton" >FEEDS</a>
            <a style="color:#B4B4C3" id="categoriesButton">CATEGORIES</a>            
            <span class="icon fui-list"></span>
        </div>
        
    <!--iterate all feeds to create feed category-->
    <div id="feedCategory" class="row demo-tiles" style="display:none">
    <c:choose>
        <c:when test="${not empty categoryFeeds}">
            <!--<div id="feedCategory" class="row demo-tiles" style="display:none">-->
                <c:forEach var="feed" items="${categoryFeeds}">
                    <div class="col-xs-3">
                        <div class="tile">
                          <img src="${feed.getLogoUrl()}" alt="" class="feedLogo ">
                          <!--<h3 class="">${feed.getTitle()}</h3>-->
                          <!--<p style="display:none">${feed.getFeedUrl()}</p>-->
                          <form action="RssDispatcher" method="POST">
                              <button type="submit" name="feedUrl" value="${feed.getFeedUrl()}" class="btn btn-primary btn-large btn-block" >Get Feed</button>
                          </form>
                        </div>
                    </div>  
                </c:forEach>       
        </c:when>
    </c:choose>     
    </div>      
          
          
      <!--iterate the feed to create all items-->
      <div id="feedContent" class="content">
      <c:choose>
            <c:when test="${not empty entries}">
                <p>${title}</p>
                <!--<div id="feedContent" class="content">-->
                    <c:forEach var="entry" items="${entries}">
                        <div class="feed">
                            <header class="">
                                <img class="img-circle feed-img" alt=""  src="">
                                <h2 class="feed-title"><a class="feed-title" href="${entry.link}"><c:out value="${entry.title}" /></a></h2>
                                <p class="feed-meta">
                                  Posted on <a class="feed-date" href="#"><c:out value="${entry.publishedDate}" /></a> | By <a class="feed-date" href="${link}">${title}</a>
                                </p>
                            </header>
                            <div class="feed-description">                               
                                 ${entry.description.value}                               
                            </div>
                            <div class="feed-icon">                  
                                <a class="icon fui-facebook" href="#"></a>
                                <a class="icon fui-twitter"  href="#"></a>
                                <!--<a class="icon fui-heart"    href=""></a>-->
                            </div>
                        </div>                       
                    </c:forEach>
            </c:when>
      </c:choose>
      </div>
    </div>  
      
      
      <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
      <script src="./dist/js/vendor/jquery.min.js"></script>
      <!-- Include all compiled plugins (below), or include individual files as needed -->
      <script src="./dist/js/flat-ui.min.js"></script>
      <script src="./media/js/myfeedPage.js"></script>
    </body>
</html>
