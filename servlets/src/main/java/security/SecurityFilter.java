package security;

import lombok.extern.java.Log;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;

@Log
@WebFilter({"/home/*"})
public class SecurityFilter implements HttpFilter
{
    private static final String USER = "USER_ID";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        log.log(Level.INFO, "SECURITY_FILTER::INITIALISATION::BEGIN");
        ServletContext servletContext = filterConfig.getServletContext();
        log.log(Level.INFO, "SECURITY_FILTER::INITIALISATION::END");
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        log.log(Level.INFO, "SECURITY_FILTER::DO_FILTER::BEGIN");

        HttpSession session = request.getSession(true);

        if (session.getAttribute(USER) != null)
        {
            log.log(Level.INFO, "SECURITY_FILTER::DO_FILTER::USER_ALREADY_LOGGED_IN");
            chain.doFilter(request, response);
            log.log(Level.INFO, "SECURITY_FILTER::DO_FILTER::CHAIN_DO_FILTER");
        }
        else
        {
            log.log(Level.INFO, "SECURITY_FILTER::DO_FILTER::USER_NOT_LOGGED_IN");
            // TODO: Убрать потерю превоначального ареса перехода
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login/login.jsp");
            requestDispatcher.forward(request, response);
            log.log(Level.INFO, "SECURITY_FILTER::DO_FILTER::FORWARDING::/login/login.jsp");
        }
    }
}
