package controllers;

import dao.UserDao;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import model.Photo;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

@Slf4j
@WebServlet("/image")
public class Image extends HttpServlet
{
    private UserDao userDao;
    private static final String USER = "USER_ID";

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        ServletContext servletContext = config.getServletContext();
        userDao = (UserDao)servletContext.getAttribute("userDao");
        log.info("IMAGE::INITIALIZATION::COMPLETE");
    }

    @Override
    @SneakyThrows
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        log.info("IMAGE::DO_GET::BEGIN");

        HttpSession session = request.getSession(true);

        long user_id = (long)session.getAttribute(USER);
        Optional<Photo> photo = userDao.getPhotoById(user_id);

        if (photo.isPresent())
        {
            log.info("IMAGE::DO_GET::PHOTO_EXISTS");
            byte[] imgData = photo.get().getImage().getBytes(1,(int)photo.get().getImage().length());
            OutputStream o = response.getOutputStream();
            response.setContentType("image/gif");
            o.write(imgData);
            o.flush();
            o.close();
        }
        else
        {
            // TODO: 05.11.2016 Заменить дефолтовой аватаркой
            log.warn("IMAGE::DO_GET::PHOTO_DOES_NOT_EXIST");
        }

        log.info("IMAGE::DO_GET::END");
    }
}
