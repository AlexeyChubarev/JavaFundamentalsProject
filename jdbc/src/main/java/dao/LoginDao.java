package dao;

import model.Login;

import java.util.Optional;

public interface LoginDao
{
    Optional<Login> getUserId(String login, String password);

    default boolean isUserRegistered(String login, String hash)
    {
        return getUserId(login, hash).isPresent();
    }
}
