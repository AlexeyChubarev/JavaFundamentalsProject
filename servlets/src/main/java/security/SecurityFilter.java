package security;

import dao.LoginDao;
import model.Login;
import utils.StringEncryptor;
import lombok.extern.java.Log;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;

@Log
@WebFilter({"/login/*","/home/*"})
public class SecurityFilter implements HttpFilter
{
    private static final String USER = "USER_ID";

    private LoginDao loginDao;
    private StringEncryptor encryptor = new StringEncryptor("MD5");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        ServletContext servletContext = filterConfig.getServletContext();
        loginDao = (LoginDao)servletContext.getAttribute("loginDao");
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        HttpSession session = request.getSession(true);

        if (session.getAttribute(USER) != null)
        {
            chain.doFilter(request, response);
            return;
        }

        Map<String, String[]> parameterMap = request.getParameterMap();

        if (parameterMap.containsKey("j_username") && parameterMap.containsKey("j_password") && session.getAttribute(USER) == null)
        {
            String userLogin = parameterMap.get("j_username")[0];
            String hash = encryptor.encrypt(parameterMap.get("j_password")[0]);

            // TODO: 29.10.2016 Убрать логирование
            log.log(Level.INFO, "PASSWORD HASH = " + hash);

            Optional<Login> login = loginDao.getUserId(userLogin, hash);

            if (login.isPresent())
            {
                log.log(Level.INFO, "LOGIN SUCCESSFUL");
                session.setAttribute(USER, login.get().getUserId());
                chain.doFilter(request, response);
            }
            else request.getRequestDispatcher("/login/authorizationError.html").forward(request, response);
        }
        else
        {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login/login.jsp");
            // TODO: 22/10/2016
            // Посмотреть что можно сделать,
            // чтобы не терять информацию о странице,
            // на которую пользователь хотел зайти
            // log.log(Level.INFO, session.getAttribute(USER).toString());
            //response.sendRedirect("/home");
            requestDispatcher.forward(request, response);
        }
    }
}
