package controllers.friends;

import dao.FriendDao;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@WebServlet("/friendsManager")
public class FriendsManager extends HttpServlet
{
    private FriendDao friendDao;
    private static final String USER = "USER_ID";

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        ServletContext servletContext = config.getServletContext();
        friendDao = (FriendDao)servletContext.getAttribute("friendDao");
        log.info("MANAGE::INITIALIZATION::COMPLETE");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        log.info("MANAGE::DO_POST::BEGIN");

        HttpSession session = request.getSession(true);

        Actions action;
        long friendId;

        try
        {
            action = Actions.valueOf(request.getParameter("action"));
            friendId = Long.parseLong(request.getParameter("id"));
        }
        catch (IllegalArgumentException e)
        {
            log.error("MANAGE::DO_POST::IllegalArgumentException::" + e);
            response.sendRedirect("/friends");
            return;
        }

        final long userId = (long)session.getAttribute(USER);

        log.info("MANAGE::DO_POST::ACTION::" + action);
        log.info("MANAGE::DO_POST::FRIEND_ID::" + friendId);

        switch (action)
        {
            case ADD_FRIEND:
            case APPLY_REQUEST:
                friendDao.addFriend(userId,friendId);
                break;
            case REJECT_REQUEST:
                friendDao.rejectRequest(userId,friendId);
                break;
            case REVOKE_REQUEST:
                friendDao.revokeRequest(userId,friendId);
                break;
            case REMOVE_FRIEND:
                friendDao.removeFriend(userId,friendId);
                break;
        }
        String referer = request.getHeader("Referer");
        log.info("MANAGE::DO_POST::" + referer);
        response.sendRedirect(referer);
    }
}
