<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="user" type="model.User" scope="request"/>

<html>
<head>
    <title>Home</title>
</head>

<body>
<table cellpadding="0" style="border: 0; padding: 0;width: 100%; height: 100%; background-color: #45668e">
    <tr>
        <td style="width: 20%; padding: 10px" valign="top" >
            Мои друзья<br/>
            Мои сообщения<br/>
            Мои фотографии<br/>
        </td>
        <td style="width: 60%">
            <table cellpadding="10" style="border: 0; padding: 0px; width: 100%; height: 100%; background-color: azure">
                <tr style="width: 100%; height: 30px; background-color: azure;">
                    <td colspan="2" align="right">
                        <a style="font-size: small" href="/login/login.jsp">Logout</a>
                    </td>
                </tr>
                <tr style="padding: 10px">
                    <td style="width: 200px; height: 200px; background-color: ghostwhite;" align="middle">
                        USER_IMAGE
                    </td>
                    <td valign="top">
                        <h5>
                            <%=user.getFirstName()%> <%=user.getLastName()%>
                        </h5>
                        <h6>
                            Dob: <%=user.getDob()%>
                        </h6>
                    </td>
                </tr>
                <tr style="height: 100%" valign="top" align="center">
                    <td colspan="2">
                        USER's ALBUMS
                    </td>
                </tr>
            </table>
        </td>
        <td style="width: 20%;">
        </td>
    </tr>
</table>

</body>
</html>
