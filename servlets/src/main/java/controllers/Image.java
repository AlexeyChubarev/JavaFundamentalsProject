package controllers;

import dao.CommonDao;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

@Slf4j
@WebServlet("/image")
public class Image extends HttpServlet
{
    private CommonDao commonDao;
    private static final String USER = "USER_ID";

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        ServletContext servletContext = config.getServletContext();
        commonDao = (CommonDao)servletContext.getAttribute("commonDao");
        log.info("IMAGE::INITIALIZATION::COMPLETE");
    }

    @Override
    @SneakyThrows
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        log.info("IMAGE::DO_GET::BEGIN");

        long target_id;

        try
        {
            log.info("IMAGE::DO_GET::TARGET_USER_ID::" + request.getParameter("id"));
            target_id = Long.parseLong(request.getParameter("id"));
        }
        catch (NumberFormatException e)
        {
            log.info("IMAGE::DO_GET::PARSE_FAILED");
            HttpSession session = request.getSession(true);
            response.sendRedirect("/home/"+(long)session.getAttribute(USER));
            log.info("IMAGE::DO_GET::REDIRECT::USER_PAGE");
            return;
        }

        Optional<model.Image> image = commonDao.getImageById(target_id);

        if (image.isPresent())
        {
            log.info("IMAGE::DO_GET::PHOTO_EXISTS");
            byte[] imgData = image.get().getImage().getBytes(1,(int)image.get().getImage().length());
            OutputStream o = response.getOutputStream();
            response.setContentType("image/gif");
            o.write(imgData);
            o.flush();
            o.close();
        }
        else
        {
            log.info("IMAGE::DO_GET::PHOTO_DOES_NOT_EXIST");
            String path = request.getServletContext().getRealPath("images/defaultUserImage.jpg");
            log.info("IMAGE::DO_GET::PATH::" + path);
            File file = new File(path);

            response.setContentLength((int)file.length());

            FileInputStream in = new FileInputStream(file);
            OutputStream out = response.getOutputStream();

            copyImage(in,out);

            out.flush();
            out.close();
            in.close();
        }

        log.info("IMAGE::DO_GET::END");
    }

    private void copyImage(FileInputStream in, OutputStream out) throws IOException
    {
        // Copy the contents of the file to the output stream
        byte[] buf = new byte[1024];
        int count;
        while ((count = in.read(buf)) >= 0)
            out.write(buf, 0, count);
    }
}
