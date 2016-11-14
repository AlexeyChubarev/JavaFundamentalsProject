package dao.oracle;

import dao.UserDao;
import model.User;

import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.Optional;
import java.util.function.Supplier;

@AllArgsConstructor
public class OracleUserDao implements UserDao
{
    private Supplier<Connection> connectionSupplier;

    @Override
    public Optional<User> getById(long targetId, long userId)
    {
        Optional<User> user = Optional.empty();

        try (Connection connection = connectionSupplier.get();
             PreparedStatement ps = getUserInfo(connection, targetId, userId);
             ResultSet resultSet = ps.executeQuery())
        {
            while (resultSet.next())
            {
                user = Optional.of(new User(
                        resultSet.getLong("ID"),
                        resultSet.getString("FIRST_NAME"),
                        resultSet.getString("LAST_NAME"),
                        resultSet.getString("COUNTRY"),
                        resultSet.getString("STATUS"),
                        resultSet.getInt("COUNT")));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void createUser(String firstName, String lastName, String country, String login, String password)
    {
        try (Connection connection = connectionSupplier.get();
             CallableStatement cs = createUser(connection, firstName, lastName, country, login, password))
        {
            cs.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private CallableStatement createUser(Connection connection, String firstName, String lastName, String country, String login, String password) throws SQLException
    {
        String sql = "{call CREATE_USER (?, ?, ?, ?, ?)}";
        CallableStatement callableStatement = connection.prepareCall(sql);
        callableStatement.setString(1, firstName);
        callableStatement.setString(2, lastName);
        callableStatement.setString(3, country);
        callableStatement.setString(4, login);
        callableStatement.setString(5, password);
        return callableStatement;
    }

    private PreparedStatement getUserInfo(Connection connection, long targetId, long userId) throws SQLException
    {
        String sql = "SELECT ID,FIRST_NAME,LAST_NAME,COUNTRY,GET_STATUS(?,?) AS STATUS, GET_FRIENDS_COUNT(?) AS COUNT FROM USERS WHERE ID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, userId);
        preparedStatement.setLong(2, targetId);
        preparedStatement.setLong(3, targetId);
        preparedStatement.setLong(4, targetId);
        return preparedStatement;
    }
}
