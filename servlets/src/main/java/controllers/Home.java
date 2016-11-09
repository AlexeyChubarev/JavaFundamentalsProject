package controllers;

import model.User;
import dao.UserDao;
import lombok.extern.slf4j.Slf4j;

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
@WebServlet("/home/*")
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

        // TODO: 05.11.2016 Разобраться с блоком ниже
        if(session.getAttribute(USER)==null)
        {
            log.error("HOME::DO_GET::HOW_IT_POSSIBLE");
            response.sendRedirect("/login/authorizationError.html");
            return;
        }

        Optional<User> user;

        if(request.getPathInfo()!=null)
        {
            String targetUser = request.getPathInfo().substring(1);
            log.info("HOME::DO_GET::TARGET_USER_ID::" + targetUser);
            try
            {
                long targetUserId = Long.parseLong(targetUser);
                user = userDao.getById(targetUserId);
            }
            catch (NumberFormatException e)
            {
                // TODO: 08.11.2016 Вынести в отдельный метод
                response.sendRedirect("/main/pageNotFoundError.html");
                log.info("HOME::DO_GET::REDIRECT::/main/pageNotFoundError.html");
                return;
            }
        }
        else
        {
            response.sendRedirect("/home/"+String.valueOf((long)session.getAttribute(USER)));
            return;
        }


        if (user.isPresent())
        {
            log.info("HOME::DO_GET::USER_FOUND");
            request.setAttribute("user", user.get());
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/main/index.jsp");
            requestDispatcher.forward(request, response);
            log.info("HOME::DO_GET::FORWARD::/main/index.jsp");
        }
        else
        {
            log.info("HOME::DO_GET::USER_NOT_FOUND");
            response.sendRedirect("/main/pageNotFoundError.html");
            log.info("HOME::DO_GET::REDIRECT::/main/pageNotFoundError.html");
        }
        log.info("HOME::DO_GET::END");
    }
}