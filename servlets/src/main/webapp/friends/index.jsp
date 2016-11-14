<%@ page import="model.Friend" %>
<%@ page import="java.util.HashSet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String id = String.valueOf((long)session.getAttribute("USER_ID"));
    String userHome = "/home/" + id;
%>

<jsp:useBean id="friends" class="java.util.HashSet" scope="request"/>
<jsp:useBean id="incRequests" class="java.util.HashSet" scope="request"/>
<jsp:useBean id="outRequests" class="java.util.HashSet" scope="request"/>

<html>
<head>
    <title>Friends</title>
</head>
<body link="azure" vlink="azure">
<table cellpadding="0" style="border: 0; padding: 0;width: 100%; height: 100%; background-color: #45668e">
    <tr>
        <td style="width: 20%; padding-top: 55px; padding-left: 10px" valign="top">
            <table>
                <tr>
                    <td>
                        <a style="font-size: medium; text-decoration: none" href=<%=userHome%>>My page</a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <a style="font-size: medium; text-decoration: none" href="/settings">My settings</a>
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
                    <td colspan="2" style="padding-right: 10px; padding-top: 15px; background-color: azure" align="right">
                        <form method="post" action="/logout">
                            <input type="submit" value="Logout"/>
                        </form>
                    </td>
                </tr>

                <tr style="background-color: #45668e">
                    <td colspan="2" align="center">
                        <p style="color: azure">
                            Friends
                        </p>
                    </td>
                </tr>

                <% if (friends.size()==0) {%>
                <tr>
                    <td colspan="2">
                        <p style="color: #45668e">
                            No friends yet
                        </p>
                    </td>
                </tr>
                <%}%>
                <% for (Friend item: (HashSet<Friend>) friends) {%>
                <tr>
                    <td style="width: 110px; height: 110px" align="center">
                        <a href="/home/<%=item.getFriendId()%>">
                            <img src="/image?id=<%=item.getFriendId()%>" width="100px" height="100px"/>
                        </a>
                    </td>
                    <td style="padding-left:10px;" valign="top">
                        <h5>
                            <a style="font-size: medium; text-decoration: none; color: #45668e"
                               href="/home/<%=item.getFriendId()%>">
                                <%=item.getFirstName()%> <%=item.getLastName()%>
                            </a><br/><br/>
                            <form method="post" action="/friendsManager?action=REMOVE_FRIEND&id=<%=item.getFriendId()%>">
                                <input type="submit" value="Remove"/>
                            </form><br/>
                        </h5>
                    </td>
                </tr>
                <%}%>

                <tr style="background-color: #45668e">
                    <td colspan="2" align="center">
                        <p style="color: azure">
                            Incoming requests
                        </p>
                    </td>
                </tr>

                <% if (incRequests.size()==0) {%>
                <tr>
                    <td colspan="2">
                        <p style="color: #45668e">
                            No incoming requests
                        </p>
                    </td>
                </tr>
                <%}%>
                <% for (Friend item: (HashSet<Friend>) incRequests) {%>
                <tr>
                    <td style="width: 110px; height: 110px" align="center">
                        <a href="/home/<%=item.getFriendId()%>">
                            <img src="/image?id=<%=item.getFriendId()%>" width="100px" height="100px"/>
                        </a>
                    </td>
                    <td style="padding-left:10px;" valign="top">
                        <h5>
                            <a style="font-size: medium; text-decoration: none; color: #45668e"
                               href="/home/<%=item.getFriendId()%>">
                                <%=item.getFirstName()%> <%=item.getLastName()%>
                            </a><br/>
                            <table>
                                <tr>
                                    <td>
                                        <form method="post" action="/friendsManager?action=ADD_FRIEND&id=<%=item.getFriendId()%>">
                                            <input type="submit" value="Accept"/>
                                        </form>
                                    </td>
                                    <td>
                                        <form method="post" action="/friendsManager?action=REJECT_REQUEST&id=<%=item.getFriendId()%>">
                                            <input type="submit" value="Reject"/>
                                        </form>
                                    </td>
                                </tr>
                            </table>
                        </h5>
                    </td>
                </tr>
                <%}%>

                <tr style="background-color: #45668e">
                    <td colspan="2" align="center">
                        <p style="color: azure">
                            Outgoing requests
                        </p>
                    </td>
                </tr>

                <% if (outRequests.size()==0) {%>
                <tr>
                    <td colspan="2">
                        <p style="color: #45668e">
                            No outgoing requests
                        </p>
                    </td>
                </tr>
                <%}%>
                <% for (Friend item: (HashSet<Friend>) outRequests) {%>
                <tr>
                    <td style="width: 110px; height: 110px" align="center">
                        <a href="/home/<%=item.getFriendId()%>">
                            <img src="/image?id=<%=item.getFriendId()%>" width="100px" height="100px"/>
                        </a>
                    </td>
                    <td style="padding-left:10px;" valign="top">
                        <h5>
                            <a style="font-size: medium; text-decoration: none; color: #45668e"
                               href="/home/<%=item.getFriendId()%>">
                                <%=item.getFirstName()%> <%=item.getLastName()%>
                            </a><br/><br/>
                            <form method="post" action="/friendsManager?action=REVOKE_REQUEST&id=<%=item.getFriendId()%>">
                                <input type="submit" value="Cancel"/>
                            </form><br/>
                        </h5>
                    </td>
                </tr>
                <%}%>

                <tr style="height: 100%">
                    <td colspan="2"> </td>
                </tr>
            </table>
        </td>
        <td style="width: 20%;">
        </td>
    </tr>
</table>
</body>
</html>
