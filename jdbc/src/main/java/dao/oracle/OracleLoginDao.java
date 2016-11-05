package dao.oracle;

import dao.LoginDao;
import lombok.AllArgsConstructor;
import model.Login;

import java.sql.*;
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
             PreparedStatement ps = getUserId(connection, login, password);
             ResultSet resultSet = ps.executeQuery())
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

    @Override
    public boolean isLoginUnique(String login)
    {
        try (Connection connection = connectionSupplier.get();
             PreparedStatement ps = isLoginUique(connection, login);
             ResultSet resultSet = ps.executeQuery())
        {
            int count = 0;
            while (resultSet.next())
            {
                count = (resultSet.getInt(1));
            }
            if (count == 0)
                return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    private PreparedStatement getUserId(Connection con, String login, String password) throws SQLException
    {
        String sql = "SELECT USER_ID FROM LOGIN WHERE USER_LOGIN=? AND USER_PASSWORD=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, login);
        ps.setString(2, password);
        return ps;
    }

    private PreparedStatement isLoginUique(Connection con, String login) throws SQLException
    {
        String sql = "SELECT COUNT (*) FROM LOGIN WHERE USER_LOGIN = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, login);
        return ps;
    }
}
