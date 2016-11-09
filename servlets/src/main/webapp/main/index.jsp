<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="user" type="model.User" scope="request"/>

<%
    String user_id = String.valueOf((long)session.getAttribute("USER_ID"));
    String userHome = "/home/" + user_id;
%>


<html>
<head>
    <title>Home</title>
</head>
<body link="azure" vlink="azure">
<table cellpadding="0" style="border: 0; padding: 0;width: 100%; height: 100%; background-color: #45668e">
    <tr>
        <td style="width: 20%; padding-top: 55px; padding-left: 10px" valign="top">
            <table>
                <tr>
                    <td>
                        <a style="font-size: medium; text-decoration: none" href=<%=userHome%>> My page</a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <a style="font-size: medium; text-decoration: none" href="/friends">My friends</a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <a style="font-size: medium; text-decoration: none" href="/home">My messages</a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <a style="font-size: medium; text-decoration: none" href="/home">My photos</a>
                    </td>
                </tr>
            </table>
        </td>
        <td style="width: 60%">
            <table style="width: 100%; height: 100%; background-color: azure">
                <tr style="height: 20px">
                    <td colspan="2" style="padding-right: 10px; padding-top: 15px; background-color: azure"
                        align="right">
                        <form method="post" action="/logout">
                            <input type="submit" value="Logout"/>
                        </form>
                    </td>
                </tr>
                <tr>
                    <td style="width: 210px; height: 210px" align="center">
                        <a href="/image?id=<%=user.getId()%>">
                            <img src="/image?id=<%=user.getId()%>" width="200px" height="200px"/>
                        </a>
                    </td>
                    <td style="padding-left:10px" valign="top">
                        <h5>
                            <%=user.getFirstName()%> <%=user.getLastName()%>
                        </h5>
                        <h6>
                            Country: <%=user.getCountry()%>
                        </h6>
                    </td>
                </tr>
                <tr style="height: 30px" valign="middle" align="center">
                    <td colspan="2">
                        ALBUMS:
                    </td>
                </tr>
                <tr style="height: 100%">
                    <td colspan="2"></td>
                </tr>
            </table>
        </td>
        <td style="width: 20%"></td>
    </tr>
</table>

</body>
</html>
