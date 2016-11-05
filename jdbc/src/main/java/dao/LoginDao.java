package dao;

import model.Login;

import java.util.Optional;

public interface LoginDao
{
    Optional<Login> getUserId(String login, String password);

    boolean isLoginUnique(String login);
}
