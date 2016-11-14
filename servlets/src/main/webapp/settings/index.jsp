<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String user_id = String.valueOf((long)session.getAttribute("USER_ID"));
    String userHome = "/home/" + user_id;
%>


<html>
<head>
    <title>Settings</title>
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
                        <a style="font-size: medium; text-decoration: none" href="/settings"> My settings</a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <a style="font-size: medium; text-decoration: none" href="/friends">My friends</a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <a style="font-size: medium; text-decoration: none" href=<%=userHome%>>My messages</a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <a style="font-size: medium; text-decoration: none" href=<%=userHome%>>My photos</a>
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

                <tr style="background-color: #45668e">
                    <td colspan="2" align="center">
                        <p style="color: azure">
                            Settings
                        </p>
                    </td>
                </tr>

                <tr>
                    <td style="width: 110px; height: 110px" align="center">
                        <a href="/image?id=<%=user_id%>">
                            <img src="/image?id=<%=user_id%>" width="100px" height="100px"/>
                        </a>
                    </td>
                    <td style="padding-top: 5px" valign="top">
                        <form action="/imageUpload" method="post" enctype="multipart/form-data">
                            <input type="file" name="userImage" accept="image/*,image/jpeg"><br/><br/>
                            <input type="submit" value="Upload">
                        </form>
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
