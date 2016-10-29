package listeners;

import common.ConnectionPool;
import dao.LoginDao;
import dao.UserDao;
import dao.oracle.OracleLoginDao;
import dao.oracle.OracleUserDao;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.util.function.Supplier;

@WebListener
public class Initer implements ServletContextListener
{
    @Override
    public void contextInitialized(ServletContextEvent sce)
    {
        ServletContext context = sce.getServletContext();
        String pathToDbConfig = context.getRealPath("/") + "WEB-INF/classes/";
        Supplier<Connection> connectionPool = ConnectionPool.create(pathToDbConfig + "db.properties");

        UserDao userDao = new OracleUserDao(connectionPool);
        LoginDao loginDao = new OracleLoginDao(connectionPool);

        context.setAttribute("userDao", userDao);
        context.setAttribute("loginDao", loginDao);
    }
}