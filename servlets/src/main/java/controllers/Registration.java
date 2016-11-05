package controllers;

import dao.LoginDao;
import dao.UserDao;
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
import java.util.logging.Level;

@Slf4j
@WebServlet("/registration")
public class Registration extends HttpServlet
{
    private UserDao userDao;
    private LoginDao loginDao;
    private StringEncryptor encryptor;

    private static final String USER = "USER_ID";
    private static final String ALGORITHM = "MD5";

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        encryptor = new StringEncryptor(ALGORITHM);
        ServletContext servletContext = config.getServletContext();
        userDao = (UserDao)servletContext.getAttribute("userDao");
        loginDao = (LoginDao)servletContext.getAttribute("loginDao");
        log.info("REGISTRATION::INITIALIZATION::COMPLETE");
    }

    @Override
    public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        log.info("REGISTRATION::DO_POST::BEGIN");

        HttpSession session = request.getSession(true);
        if (session.getAttribute(USER) != null)
        {
            log.info("REGISTRATION::DO_POST::USER_ALREADY_LOGGED_IN");
            response.sendRedirect("/home");
            log.info("REGISTRATION::DO_POST::REDIRECT::/home");
            return;
        }

        log.info("REGISTRATION::DO_POST::USER_NOT_LOGGED_IN");

        Map<String, String[]> parameterMap = request.getParameterMap();

        String userFirstName = parameterMap.get("userFirstName")[0];
        log.info("REGISTRATION::DO_POST::INPUT::USER_FIRST_NAME::" + userFirstName);

        String userLastName = parameterMap.get("userLastName")[0];
        log.info("REGISTRATION::DO_POST::INPUT::USER_LAST_NAME::" + userLastName);

        String userLogin = parameterMap.get("userLogin")[0];
        log.info("REGISTRATION::DO_POST::INPUT::USER_LOGIN::" + userLogin);

        String userPassword = parameterMap.get("userPassword")[0];
        log.info("REGISTRATION::DO_POST::INPUT::USER_PASSWORD::" + userPassword);

        String userPasswordRepeat = parameterMap.get("userPasswordRepeat")[0];
        log.info("REGISTRATION::DO_POST::INPUT::USER_PASSWORD_REPEAT::" + userPasswordRepeat);

        String userCountry = parameterMap.get("userCountry")[0];
        log.info("REGISTRATION::DO_POST::INPUT::USER_COUNTRY::" + userCountry);

        if(nameIsCorrect(userFirstName,userLastName))
        {
            log.info("REGISTRATION::DO_POST::NAME_IS_CORRECT");
            if (loginIsCorrect(userLogin))
            {
                log.info("REGISTRATION::DO_POST::LOGIN_IS_CORRECT");
                if (passwordIsCorrect(userPassword,userPasswordRepeat))
                {
                    log.info("REGISTRATION::DO_POST::PASSWORD_IS_CORRECT");
                    userDao.createUser(userFirstName,userLastName,userCountry,userLogin,encryptor.encrypt(userPassword));
                    log.info( "REGISTRATION::DO_POST::USER_CREATED");
                    response.sendRedirect("/login");
                    log.info( "REGISTRATION::DO_POST::REDIRECTING::/login");
                }
                else
                {
                    log.info( "REGISTRATION::DO_POST::PASSWORD_IS_NOT_CORRECT");
                    response.sendRedirect("/registration");
                }
            }
            else
            {
                log.info( "REGISTRATION::DO_POST::LOGIN_IS_NOT_CORRECT");
                response.sendRedirect("/registration");
            }
        }
        else
        {
            log.info( "REGISTRATION::DO_POST::NAME_IS_NOT_CORRECT");
            response.sendRedirect("/registration");
        }
        log.info("REGISTRATION::DO_POST::END");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException
    {
        log.info( "REGISTRATION::DO_GET::BEGIN");

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/registration/registration.jsp");
        requestDispatcher.forward(request, response);

        log.info( "REGISTRATION::DO_GET::FORWARDING::/registration/registration.jsp");
        log.info( "REGISTRATION::DO_GET::END");
    }

    private boolean nameIsCorrect(String userFirstName, String userLastName)
    {
        return (userFirstName.length()>=2)&&(userLastName.length()>=2);
    }

    private boolean loginIsCorrect(String login)
    {
        if (login.length() >= 4)
        {
            if (loginDao.isLoginUnique(login))
                return true;
            else
            {
                log.info("REGISTRATION::DO_POST::LOGIN_MUST_BE_UNIQUE");
                return false;
            }
        }
        else
        {
            log.info("REGISTRATION::DO_POST::LOGIN_LENGTH_INCORRECT");
            return false;
        }
    }

    private boolean passwordIsCorrect(String userPassword, String userPasswordRepeat)
    {
        return (userPassword.equals(userPasswordRepeat))&&(userPassword.length()>=4);
    }
}
