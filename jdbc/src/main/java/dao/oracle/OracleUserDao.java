package dao.oracle;

import dao.UserDao;
import model.User;

import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Supplier;

@AllArgsConstructor
public class OracleUserDao implements UserDao
{
    private Supplier<Connection> connectionSupplier;

    @Override
    public Collection<User> getAll()
    {

        Collection<User> users = new HashSet<>();

        try (Connection connection = connectionSupplier.get();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT ID,FIRST_NAME,LAST_NAME,DOB,PHOTO,LOGIN,PASSWORD FROM USERS"))
        {
            while (resultSet.next())
            {
                users.add(
                        new User(
                                resultSet.getLong("ID"),
                                resultSet.getString("FIRST_NAME"),
                                resultSet.getString("LAST_NAME"),
                                resultSet.getDate("DOB"),
                                resultSet.getBlob("PHOTO"),
                                resultSet.getString("LOGIN"),
                                resultSet.getString("PASSWORD")));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return users;
    }
}
