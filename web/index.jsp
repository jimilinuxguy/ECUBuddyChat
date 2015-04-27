<%-- 
    Document   : index
    Created on : Feb 19, 2015, 11:02:34 AM
    Author     : jimisanchez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" session="true" %>
<% String username = null; %>
<% if ( session.getAttribute("username") != null) { username = session.getAttribute("username").toString(); }  %>
<% String validationWarnings = ""; %>
<% if ( session.getAttribute("errors") != null) { validationWarnings = session.getAttribute("errors").toString(); } %>
<% if ( session.getAttribute("passwordChange") != null) { validationWarnings = session.getAttribute("passwordChange").toString(); } %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ECU Buddy Chat</title>
        <jsp:include page="header.html" />
    </head>
    <body>
    <div class="container" id="maincontainer">
        <div class="row">
            <div class="col-sm-12" id="pageTitle">
                <h1>
            Welcome <% if (username !=null ) { %>Back <br /><%=username %><% } else { %>to ECU Buddy Chat<% } %>! <% if (username !=null ) { %><a href="profile.jsp"><br /> Manage Profile</a><% } %></div>
         <% if (!validationWarnings.isEmpty()) { %><div id="indexWarningsDiv" class="alert alert-danger" role="alert"><%= validationWarnings %><% } %>
             </h1></div>
             <div class="graystroke col-sm-12"></div>
         </div>
        <% session.setAttribute("errors",null); session.setAttribute("passwordChange", null); %>

        <!-- Only do if the user is not logged in -->
        <% if (username ==null ) {%>
          <jsp:include page="loginForm.html" />
        <!-- End of username check -->
        <% } else { %>
            <!-- User is logged in, include logged in functionality -->
            <jsp:include page="searchForm.html" />
         <% } %>
        <!-- End of logged in functionality -->
    <jsp:include page="footer.html" />
    </body>
</html>
