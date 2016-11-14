package controllers;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebServlet("/settings")
public class Settings extends HttpServlet
{
    /*private static final String USER = "USER_ID";*/

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        ServletContext servletContext = config.getServletContext();
        log.info("SETTINGS::INITIALIZATION::COMPLETE");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        log.info("SETTINGS::DO_GET::BEGIN");

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/settings/index.jsp");
        requestDispatcher.forward(request, response);
        log.info("SETTINGS::DO_GET::FORWARD::/settings/index.jsp");

        log.info("SETTINGS::DO_GET::END");
    }
}
