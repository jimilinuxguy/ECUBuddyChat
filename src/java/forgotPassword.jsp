<%-- 
    Document   : forgotPassword.jsp
    Created on : Apr 17, 2015, 5:42:10 AM
    Author     : reganj06
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<% String validationWarnings = ""; %>
<% if ( session.getAttribute("errors") != null) { validationWarnings = session.getAttribute("errors").toString(); } %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forgot Password</title>
        <jsp:include page="header.html"/>
    </head>
    <body>
        <jsp:include page="forgotPasswordForm.html"/>
            <div class="form-group">
                <label class="col-md-4 control-label" id="passwordLabel"> 
                    <% if(session.getAttribute("passkey").toString() != "") session.getAttribute("passkey").toString(); %> 
                </label>
            </div>
    </body>
</html>
