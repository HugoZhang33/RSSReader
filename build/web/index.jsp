<%-- 
    Document   : index.jsp
    Created on : Dec 4, 2014, 3:38:47 PM
    Author     : hongzhang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>RssReader</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Loading Bootstrap -->
    <link href="./dist/css/vendor/bootstrap.min.css" rel="stylesheet">

    <!-- Loading Flat UI -->
    <link href="./dist/css/flat-ui.min.css" rel="stylesheet">
    <link rel="shortcut icon" href="">

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
    <!--[if lt IE 9]>
      <script src="js/vendor/html5shiv.js"></script>
      <script src="js/vendor/respond.min.js"></script>
    <![endif]-->    
  </head>
  <body>
    <nav class="navbar navbar-inverse navbar-embossed navbar-static-top" role="navigation">
        <div class="container">
          <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-collapse-9">
              <span class="sr-only">Toggle navigation</span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">RSS Reader</a>
          </div>
          <div class="navbar-collapse collapse" id="navbar-collapse-9">
              <form class="navbar-form navbar-right" role="form" action="Login" method="POST" >
              <div class="form-group">
                <input type="text" id="userName" name="userName" placeholder="User Name" class="form-control">
              </div>
              <div class="form-group">
                <input type="password" id="password" name="password" placeholder="Password" class="form-control">
              </div>
                <button  type="submit" id="signin" class="btn btn-success">Sign in</button>
                <a  id="signup" class="btn btn-danger">Sign up</a>
            </form>
          </div><!--/.navbar-collapse -->
        </div>
    </nav>

    <div class="container">
        <h1>Hello, world!</h1>
        <p id="megOfRegister" style="display: none">user name has been used, please choose the other name :)</p>
        <p id="megOfLogin" style="display: none">${wrongMeg}</p>
    </div>
    <!-- /.container -->


    <!-- jQuery (necessary for Flat UI's JavaScript plugins) -->
    <script src="./dist/js/vendor/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="./dist/js/flat-ui.min.js"></script>
    <script src="./media/js/homePage.js"></script>
  </body>
</html>

