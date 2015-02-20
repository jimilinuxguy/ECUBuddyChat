<%-- 
    Document   : index
    Created on : Feb 19, 2015, 11:02:34 AM
    Author     : jimisanchez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" session="true" %>
<% String username = null; %>
<% if ( session.getAttribute("username") != null) { username = session.getAttribute("username").toString(); }  %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ECU Buddy Chat</title>
        <jsp:include page="header.html" />
    </head>
    <body>
        <h1>Welcome <% if (username !=null ) { %>back <%=username %><% } else { %>to ECU Buddy Chat<% } %>!</h1>
        
        <form method="post" action="UserLogin" class="form-horizontal">
        <fieldset>

        <!-- Form Name -->
        <legend>Login</legend>

        <!-- Text input-->
        <div class="form-group">
          <label class="col-md-4 control-label" for="email">Username</label>  
          <div class="col-md-4">
          <input id="username" name="email" type="text" placeholder="email@students.ecu.edu" class="form-control input-md" required="">
          <span class="help-block">Your pirate email address</span>  
          </div>
        </div>

        <!-- Password input-->
        <div class="form-group">
          <label class="col-md-4 control-label" for="password">Password</label>
          <div class="col-md-4">
            <input id="password" name="password" type="password" placeholder="your strong secure password" class="form-control input-md" required="">
            <span class="help-block">Minimum 8 characters</span>
          </div>
        </div>

        <!-- Button (Double) -->
        <div class="form-group">
          <label class="col-md-4 control-label" for="loginButton">Login</label>
          <div class="col-md-8">
            <button type="submit" id="loginButton" name="loginButton" class="btn btn-success">Login</button>
            <button type="reset" id="resetButton" name="resetButton" class="btn btn-danger">Reset</button>
          </div>
          <br/>
          <br/>
          <br/>
           <label class="col-md-4 control-label">&nbsp;</label>
          <div class="col-md8">
            <a href="register.jsp">Register</a>
            <a href="UserLogout">Logout</a>
            <a href="#">Forgot Password</a>
          </div>
        </div>

        </fieldset>
        </form>

    </body>
</html>
