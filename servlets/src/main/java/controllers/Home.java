package controllers;

import dao.UserDao;
import model.User;

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

@WebServlet("/home/")
public class Home extends HttpServlet
{
    private UserDao userDao;

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        ServletContext servletContext = config.getServletContext();
        userDao = (UserDao) servletContext.getAttribute("userDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        HttpSession session = req.getSession(true);

        // In session 100% will be valid USER_ID
        @SuppressWarnings("OptionalGetWithoutIsPresent")
        User user = userDao.getById((long)session.getAttribute("USER_ID")).get();

        req.setAttribute("user", user);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/home/index.jsp");
        requestDispatcher.forward(req, resp);
    }
}