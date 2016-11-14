package dao;

import model.Image;

import java.io.InputStream;
import java.util.Optional;

public interface CommonDao
{
    Optional<Image> getUserImage(long id);

    void setUserImage(InputStream image, long id);
}
