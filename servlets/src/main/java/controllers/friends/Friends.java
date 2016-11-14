package controllers.friends;

import dao.FriendDao;
import lombok.extern.slf4j.Slf4j;
import model.Friend;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

@Slf4j
@WebServlet("/friends")
public class Friends extends HttpServlet
{
    private FriendDao friendDao;
    private static final String USER = "USER_ID";

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        ServletContext servletContext = config.getServletContext();
        friendDao = (FriendDao)servletContext.getAttribute("friendDao");
        log.info("FRIENDS::INITIALIZATION::COMPLETE");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        log.info("FRIENDS::DO_GET::BEGIN");
        HttpSession session = request.getSession(true);

        getAllCollections((long)session.getAttribute(USER), request);

        //load friends collection
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/friends/index.jsp");
        requestDispatcher.forward(request, response);
        log.info("FRIENDS::DO_GET::FORWARD::/friends/index.jsp");

        log.info("FRIENDS::DO_GET::END");
    }

    private void getAllCollections(long id, HttpServletRequest request)
    {
        Collection<Friend> friends = friendDao.getFriends(id);
        log.info("FRIENDS::DO_GET::FRIENDS_SIZE::" + friends.size());
        request.setAttribute("friends", friends);

        Collection<Friend> incRequests = friendDao.getIncomingRequests(id);
        log.info("FRIENDS::DO_GET::FRIENDS_SIZE::" + incRequests.size());
        request.setAttribute("incRequests", incRequests);

        Collection<Friend> outRequests = friendDao.getOutgoingRequests(id);
        log.info("FRIENDS::DO_GET::FRIENDS_SIZE::" + outRequests.size());
        request.setAttribute("outRequests", outRequests);
    }
}
