<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session.getAttribute("USER_ID") != null)
    {
        response.sendRedirect("/home");
    }
%>


<html>
<head>
    <title>Login</title>
</head>
<body>
<center>
    <form method="POST" action="/home">
        <input name="j_username" title="Login"/> <br/>
        <input type="password" name="j_password" autocomplete="off" title="Password"/> <br/>
        <input type="submit" value="submit"/>
        <a style="font-size: small" href="/registration/registration.html">Registration</a>
    </form>
</center>

</body>
</html>