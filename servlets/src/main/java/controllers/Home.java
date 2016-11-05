package controllers;

import lombok.extern.slf4j.Slf4j;
import model.User;
import dao.UserDao;

import javax.servlet.annotation.WebServlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@WebServlet("/home")
public class Home extends HttpServlet
{
    private UserDao userDao;
    private static final String USER = "USER_ID";

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        ServletContext servletContext = config.getServletContext();
        userDao = (UserDao)servletContext.getAttribute("userDao");
        log.info("HOME::INITIALIZATION::COMPLETE");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        log.info("HOME::DO_GET::BEGIN");

        HttpSession session = request.getSession(true);

        Optional<User> user = userDao.getById((long)session.getAttribute(USER));

        if (user.isPresent())
        {
            log.info("HOME::DO_GET::USER_FOUND");
            request.setAttribute("user", user.get());
        }
        else
        {
            /* В теории этот блок никогда не отработает */
            log.warn("HOME::DO_GET::USER_NOT_FOUND");
            request.getRequestDispatcher("/login/authorizationError.html").forward(request, response);
            log.warn("HOME::DO_GET::FORWARD::/login/authorizationError.html");
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/home/index.jsp");
        requestDispatcher.forward(request, response);
        log.info("HOME::DO_GET::FORWARD::/home/index.jsp");
        log.info("HOME::DO_GET::END");
    }
}