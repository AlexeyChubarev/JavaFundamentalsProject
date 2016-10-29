package dao;

import model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserDao
{
    Collection<User> getAll();

    default Optional<User> getByLogin(String login)
    {
        return getAll().stream()
                .filter(user -> user.getLogin().equals(login)).findAny();
    }

    default Optional<User> getById(long id)
    {
        return getAll().stream().filter(user -> user.getId() == id).findAny();
    }

    default boolean isPersonRegistered(String login, String nothash)
    {
        return getAll().stream().filter(user -> user.getLogin().equals(login)).anyMatch(user -> user.getPassword().equals(nothash));
    }
}
