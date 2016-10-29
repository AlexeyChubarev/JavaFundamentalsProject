package dao.oracle;

import dao.LoginDao;
import lombok.AllArgsConstructor;
import model.Login;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.function.Supplier;

@AllArgsConstructor
public class OracleLoginDao implements LoginDao
{
    private Supplier<Connection> connectionSupplier;

    @Override
    public Optional<Login> getUserId(String login, String password)
    {
        Optional<Login> userId = Optional.empty();

        try (Connection connection = connectionSupplier.get();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT USER_ID FROM LOGIN WHERE USER_LOGIN=" + "\'" + login + "\'" + " AND USER_PASSWORD=" + "\'" + password + "\'"))
        {
            while (resultSet.next())
            {
                userId = Optional.of(new Login(resultSet.getLong("USER_ID")));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return userId;
    }
}
