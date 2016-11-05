<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session.getAttribute("USER_ID") != null)
    {
        response.sendRedirect("/home");
    }
%>

<html>
<head>
    <title>
        Login
    </title>
</head>
<body>

<table cellpadding="0" style="border: 0; padding: 0;width: 100%; height: 100%; background-color: #45668e">
    <tr>
        <td style="width: 20%"></td>
        <td style="width: 60%; background-color: azure" align="center" valign="top ">
            <table style="background-color: azure">
                <form method="POST" action="/login">
                    <tr>
                        <td>
                            Login:
                        </td>
                        <td>
                            <input name="user_login"/> <br/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Password:
                        </td>
                        <td>
                            <input type="password" name="user_password" autocomplete="off"/> <br/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <a style="font-size: small" href="/registration">Registration</a>
                        </td>
                        <td align="right">
                            <input type ="submit" value="Login"/> <br/>
                        </td>
                    </tr>
                </form>
            </table>
        </td>
        <td style="width: 20%"></td>
    </tr>
</table>

</body>
</html>