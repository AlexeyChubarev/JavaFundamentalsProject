package controllers;

import dao.LoginDao;
import dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import utils.StringEncryptor;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@Slf4j
@WebServlet("/userImageUploader")
@MultipartConfig(maxFileSize = 2097152)    // upload file's size up to 2MB
public class UserImageUploader extends HttpServlet
{
    @Override
    public void init(ServletConfig config) throws ServletException
    {
        ServletContext servletContext = config.getServletContext();
        log.info("USER_IMAGE_UPLOADER::INITIALIZATION::COMPLETE");
    }
}
