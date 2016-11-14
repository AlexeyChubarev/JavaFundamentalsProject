package dao;

import model.User;

import java.util.Optional;

public interface UserDao
{
    /**
     * @param targetId Load user data by id
     * @param userId Check status for id
     * Get USER data by ID = targetId and check status for USER with ID = userId
     */
    Optional<User> getById(long targetId, long userId);

    void createUser(String firstName,String lastName,String country,String login,String password);
}
