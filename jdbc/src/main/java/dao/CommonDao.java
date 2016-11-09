package dao;

import model.Image;

import java.util.Optional;

public interface CommonDao
{
    Optional<Image> getImageById(long id);
}
