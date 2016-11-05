package controllers;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebServlet("/logout")
public class Logout extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        log.info("LOGOUT::DO_POST::BEGIN");

        request.getSession().invalidate();
        log.info("LOGOUT::DO_POST::SESSION_INVALIDATE");

        response.sendRedirect("/login");
        log.info("LOGOUT::DO_POST::REDIRECT::/login");

        log.info("LOGOUT::DO_POST::END");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException
    {
        log.info("LOGOUT::DO_GET::BEGIN");

        response.sendRedirect("/home");
        log.info("LOGOUT::DO_GET::REDIRECT::/home");

        log.info("LOGOUT::DO_GET::END");
    }
}