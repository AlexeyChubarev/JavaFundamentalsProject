package controllers;

import dao.LoginDao;
import utils.StringEncryptor;

import lombok.extern.slf4j.Slf4j;

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
import java.util.Map;
import java.util.Optional;

@Slf4j
@WebServlet("/login")
public class Login extends HttpServlet
{
    private LoginDao loginDao;
    private StringEncryptor encryptor;

    private static final String USER = "USER_ID";
    private static final String ALGORITHM = "MD5";

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        ServletContext servletContext = config.getServletContext();
        encryptor = new StringEncryptor(ALGORITHM);
        loginDao = (LoginDao)servletContext.getAttribute("loginDao");
        log.info("LOGIN::INITIALIZATION::COMPLETE");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        log.info("LOGIN::DO_POST::BEGIN");

        HttpSession session = request.getSession(true);

        if (session.getAttribute(USER) != null)
        {
            log.info("LOGIN::DO_POST::USER_ALREADY_LOGGED_IN");
            response.sendRedirect("/home");
            log.info("LOGIN::DO_POST::REDIRECT::/home");
            return;
        }

        log.info("LOGIN::DO_POST::USER_NOT_LOGGED_IN");

        Map<String, String[]> parameterMap = request.getParameterMap();

        String userLogin = parameterMap.get("user_login")[0];
        log.info("LOGIN::DO_POST::INPUT::USER_LOGIN::" + userLogin);

        String hash = encryptor.encrypt(parameterMap.get("user_password")[0]);
        log.info("LOGIN::DO_POST::INPUT::USER_PASSWORD::" + parameterMap.get("user_password")[0]);

        Optional<model.Login> login = loginDao.getUserId(userLogin, hash);

        if (login.isPresent())
        {
            log.info("LOGIN::DO_POST::USER_FOUND");
            session.setAttribute(USER, login.get().getUserId());
            response.sendRedirect("/home");
            log.info("LOGIN::DO_POST::REDIRECT::/home");
        }
        else
        {
            log.warn("LOGIN::DO_POST::USER_NOT_FOUND");
            request.getRequestDispatcher("/login/authorizationError.html").forward(request, response);
            log.warn("LOGIN::DO_POST::FORWARD::/login/authorizationError.html");
        }

        log.info("LOGIN::DO_POST::END");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException
    {
        log.info("LOGIN::DO_GET::BEGIN");

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login/login.jsp");
        requestDispatcher.forward(request, response);

        log.info("LOGIN::DO_GET::FORWARD::/login/login.jsp");
        log.info("LOGIN::DO_GET::END");
    }
}
