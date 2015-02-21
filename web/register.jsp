<%-- 
    Document   : register
    Created on : Feb 19, 2015, 11:09:07 AM
    Author     : jimisanchez
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<% String validationWarnings = ""; %>
<% if ( session.getAttribute("errors") != null) { validationWarnings = session.getAttribute("errors").toString(); } %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Registration</title>
        <jsp:include page="header.html" />
    </head>
    <body>
        <h1>User Registration</h1>
        <% if (!validationWarnings.isEmpty()) { %><div class="alert alert-danger" role="alert"><%= validationWarnings %><% } %></div>
        <% session.setAttribute("errors",null); %>
        <form id="registrationForm" action="UserRegistration" method="post" class="form-horizontal">
            <fieldset>

            <!-- Form Name -->
            <legend>Registration</legend>

            <!-- Text input-->
            <div class="form-group">
              <label class="col-md-4 control-label" for="firstname">First Name</label>  
              <div class="col-md-4">
              <input id="firstname" name="firstname" type="text" placeholder="John" class="form-control input-md" required="">
              <span class="help-block">Your first name</span>  
              </div>
            </div>

            <!-- Text input-->
            <div class="form-group">
              <label class="col-md-4 control-label" for="lastname">Last Name</label>  
              <div class="col-md-4">
              <input id="lastname" name="lastname" type="text" placeholder="Doe" class="form-control input-md" required="">
              <span class="help-block">Your last name</span>  
              </div>
            </div>

            <!-- Text input-->
            <div class="form-group">
              <label class="col-md-4 control-label" for="email">Email</label>  
              <div class="col-md-5">
              <input id="email" name="email" type="text" placeholder="email@students.ecu.edu" class="form-control input-md" required="">
              <span class="help-block">Your pirate email address</span>  
              </div>
            </div>

            <!-- Password input-->
            <div class="form-group">
              <label class="col-md-4 control-label" for="password">Password</label>
              <div class="col-md-5">
                <input id="password" name="password" type="password" placeholder="Your secure password" class="form-control input-md" required="">
                <span class="help-block">Minimum 8 characters</span>
              </div>
            </div>

            <!-- Button (Double) -->
            <div class="form-group">
              <label class="col-md-4 control-label" for="register">Actions</label>
              <div class="col-md-8">
                <button id="register" name="register" class="btn btn-success">Register</button>
                <button id="cancel" type="reset" name="cancel" class="btn btn-danger">Reset</button>
              </div>
            </div>

            </fieldset>
        </form>
        <jsp:include page="footer.html" />
    </body>
</html>
