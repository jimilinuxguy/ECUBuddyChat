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
    </head>
    <body>
        <h1>Welcome to ECU Buddy Chat <% if (username !=null ) { %> <%=username %><% } %> !</h1>
        
        <form action="UserLogin" method="post">
            Username: <input type="text" name="email" /><br/>
            Password: <input type="password" name="password" /><br/>
            <input type="Submit" value="Login"/>
        </form>
        <a href="register.jsp">Register</a>
        <a href="UserLogout">Logout</a>
        <a href="#">Forgot Password</a>
    </body>
</html>
