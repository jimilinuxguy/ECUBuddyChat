<%-- 
    Document   : register
    Created on : Feb 19, 2015, 11:09:07 AM
    Author     : jimisanchez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Registration</title>
    </head>
    <body>
        <h1>User Registration</h1>
        <form action="UserRegistration" method="post">
            E-Mail: <input type="text" name="email" /> <br/>
            Password: <input type="password" name="password" /><br/>
            <input type="submit" value="Register" />
        </form>
    </body>
</html>