<%-- 
    Document   : profile
    Created on : Feb 19, 2015, 3:15:42 PM
    Author     : jimisanchez
--%>

<%@page import="BuddyDB.DataConnectionClass"%>
<%@page import="java.sql.ResultSet,java.sql.DriverManager,com.mysql.jdbc.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<% String userId = null; %>
<% if ( session.getAttribute("user_id") != null) { userId = session.getAttribute("user_id").toString(); } %>
<% Class.forName("com.mysql.jdbc.Driver"); %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit My Profile</title>
    </head>
    <body>
        <h1>Edit Profile</h1>
                <% 
                    DataConnectionClass dcc = new DataConnectionClass();
                    Connection connection = dcc.getConnection();

                    Statement statement = (Statement) connection.createStatement();

                    ResultSet resultset = 
                        statement.executeQuery("SELECT * from users WHERE id = " + userId) ; 

                    if(!resultset.next()) {
                        // Add Redirect to Login
                        
                    } else {
        %>
        <form action="UserProfileUpdate" method="post">
            Username: <input type="text" readonly="readonly" disabled value="<%= resultset.getString("email") %>" /><br/>
            First Name: <input type="text" name="firstname" value="<%= resultset.getString("firstname") %>" /><br/>
            Last Name: <input type="text" name="lastname" value="<%= resultset.getString("lastname") %>" /><br/>
            Phone: <input type="text" name="phone" value="<%= resultset.getString("phone") %>" /><br/>
            <input type="submit" value="Update" />
        </form>
        <%  } %>
    </body>
</html>
