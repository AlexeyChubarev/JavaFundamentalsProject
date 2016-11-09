package dao.oracle;

import dao.FriendDao;
import lombok.AllArgsConstructor;
import model.Friend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Supplier;

@AllArgsConstructor
public class OracleFriendDao implements FriendDao
{

    private Supplier<Connection> connectionSupplier;

    @Override
    public Collection<Friend> getFriends(long userId)
    {
        Collection<Friend> friends = new HashSet<>();

        try (Connection connection = connectionSupplier.get();
             PreparedStatement ps = getAllFriends(connection, userId);
             ResultSet resultSet = ps.executeQuery())
        {
            while (resultSet.next())
            {
                friends.add(
                        new Friend(
                                resultSet.getLong("ID"),
                                resultSet.getString("FIRST_NAME"),
                                resultSet.getString("LAST_NAME")
                        ));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return friends;
    }

    @Override
    public Collection<Friend> getIncomingRequests(long userId)
    {
        return null;
    }

    @Override
    public Collection<Friend> getOutgoingRequests(long userId)
    {
        return null;
    }

    private PreparedStatement getAllFriends(Connection connection, long id) throws SQLException
    {
        String sql = "SELECT ID,FIRST_NAME,LAST_NAME FROM USERS WHERE ID IN (SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND CONFIRMED='Y')";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }
}
