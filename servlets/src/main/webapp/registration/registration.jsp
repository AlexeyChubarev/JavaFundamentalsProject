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
        Registration
    </title>
</head>
<body>

<table cellpadding="0" style="border: 0; padding: 0;width: 100%; height: 100%; background-color: #45668e">
    <tr>
        <td style="width: 20%"></td>
        <td style="width: 60%; background-color: azure" align="center" valign="top ">
            <table style="background-color: azure">
                <form method="POST" action="/registration">
                    <tr>
                        <td>
                            First name
                        </td>
                        <td>
                            <input maxlength="64" type="text" name="userFirstName"/> <br/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Last name:
                        </td>
                        <td>
                            <input maxlength="64" type="text" name="userLastName"/> <br/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Email:
                        </td>
                        <td>
                            <input maxlength="256" type="text" name="userLogin"/> <br/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Password:
                        </td>
                        <td>
                            <input maxlength="64" type="password" name="userPassword"/> <br/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Repeat password:
                        </td>
                        <td>
                            <input maxlength="64" type="password" name="userPasswordRepeat"/> <br/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Country:
                        </td>
                        <td>
                            <select name="userCountry">
                                <option>Belarus</option>
                                <option>France</option>
                                <option>Poland</option>
                                <option>Ukraine</option>
                                <option>United Kingdom</option>
                                <option>Russia</option>
                                <option>Other</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <a style="font-size: small" href="/login">Already registered?</a>
                        </td>
                        <td align="right">
                            <input type="submit" value="Sign up"/>
                        </td>
                    </tr>
                </form>
            </table>
        </td>
        <td style="width: 20%;"></td>
    </tr>
</table>

</body>
</html>
