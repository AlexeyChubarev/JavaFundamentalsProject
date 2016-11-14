package controllers.images;

import dao.CommonDao;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@WebServlet("/imageUpload")
@MultipartConfig(maxFileSize = 1048576)    // upload file's size up to 1MB
public class ImageUpload extends HttpServlet
{
    private CommonDao commonDao;
    private static final String USER = "USER_ID";

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        ServletContext servletContext = config.getServletContext();
        commonDao = (CommonDao)servletContext.getAttribute("commonDao");
        log.info("IMAGE_UPLOAD::INITIALIZATION::COMPLETE");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        log.info("IMAGE_UPLOAD::DO_POST::BEGIN");

        HttpSession session = request.getSession();
        InputStream inputStream;

        Part part = request.getPart("userImage");
        if(part.getSize()!=0)
        {
            log.info("IMAGE_UPLOAD::DO_POST::IMAGE_NOT_NULL");
            log.info(part.getName());
            log.info(String.valueOf(part.getSize()));
            log.info(part.getContentType());
            inputStream = part.getInputStream();
            commonDao.setUserImage(inputStream,(long)session.getAttribute(USER));
        }
        response.sendRedirect("/settings");
        log.info("IMAGE_UPLOAD::DO_POST::REDIRECT::/settings");

        log.info("IMAGE_UPLOAD::DO_POST::END");
    }
}
