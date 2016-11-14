package security;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@WebFilter({"/home/*", "/friends/*", "/imagesz", "/settings"})
public class SecurityFilter implements HttpFilter
{
    private static final String USER = "USER_ID";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        ServletContext servletContext = filterConfig.getServletContext();
        log.info("SECURITY_FILTER::INITIALIZATION::COMPLETE");
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        log.info("SECURITY_FILTER::DO_FILTER::BEGIN");

        HttpSession session = request.getSession(true);

        if (session.getAttribute(USER) != null)
        {
            log.info("SECURITY_FILTER::DO_FILTER::USER_ALREADY_LOGGED_IN");
            chain.doFilter(request, response);
            log.info("SECURITY_FILTER::DO_FILTER::CHAIN_DO_FILTER");
        }
        else
        {
            log.info("SECURITY_FILTER::DO_FILTER::USER_NOT_LOGGED_IN");
            // TODO: Убрать потерю превоначального ареса перехода
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login/login.jsp");
            requestDispatcher.forward(request, response);
            log.info("SECURITY_FILTER::DO_FILTER::FORWARDING::/login/login.jsp");
        }
    }
}
