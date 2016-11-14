package dao;

import model.Login;

import java.util.Optional;

public interface LoginDao
{
    Optional<Login> getUserId(String login, String hash);

    boolean isLoginUnique(String login);
}
