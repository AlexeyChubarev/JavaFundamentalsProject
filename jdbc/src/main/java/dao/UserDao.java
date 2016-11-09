package dao;

import model.User;

import java.util.Optional;

public interface UserDao
{
    Optional<User> getById(long id);

    void createUser(String firstName,String lastName,String country,String login,String password);
}
