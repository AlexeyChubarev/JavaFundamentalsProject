package dao.oracle;

import dao.FriendDao;
import model.Friend;
import lombok.AllArgsConstructor;

import java.sql.*;
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

        try (Connection connection = connectionSupplier.get(); PreparedStatement ps = getFriends(connection, userId);
             ResultSet resultSet = ps.executeQuery())
        {
            while (resultSet.next())
            {
                friends.add(new Friend(resultSet.getLong("ID"), resultSet.getString("FIRST_NAME"), resultSet.getString("LAST_NAME")));
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
        Collection<Friend> incRequests = new HashSet<>();

        try (Connection connection = connectionSupplier.get();
             PreparedStatement ps = getIncomingRequests(connection, userId); ResultSet resultSet = ps.executeQuery())
        {
            while (resultSet.next())
            {
                incRequests.add(new Friend(resultSet.getLong("ID"), resultSet.getString("FIRST_NAME"), resultSet.getString("LAST_NAME")));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return incRequests;
    }

    @Override
    public Collection<Friend> getOutgoingRequests(long userId)
    {
        Collection<Friend> outRequests = new HashSet<>();

        try (Connection connection = connectionSupplier.get();
             PreparedStatement ps = getOutgoingRequests(connection, userId); ResultSet resultSet = ps.executeQuery())
        {
            while (resultSet.next())
            {
                outRequests.add(new Friend(resultSet.getLong("ID"), resultSet.getString("FIRST_NAME"), resultSet.getString("LAST_NAME")));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return outRequests;
    }

    @Override
    public void addFriend(long userId, long friendId)
    {
        try (Connection connection = connectionSupplier.get();
             CallableStatement cs = addFriend(connection, userId, friendId))
        {
            cs.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void removeFriend(long userId, long friendId)
    {
        try (Connection connection = connectionSupplier.get();
             CallableStatement cs = removeFriend(connection, userId, friendId))
        {
            cs.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void rejectRequest(long userId, long friendId)
    {
        try (Connection connection = connectionSupplier.get();
             CallableStatement cs = rejectRequest(connection, userId, friendId))
        {
            cs.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void revokeRequest(long userId, long friendId)
    {
        try (Connection connection = connectionSupplier.get();
             CallableStatement cs = revokeRequest(connection, userId, friendId))
        {
            cs.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private PreparedStatement getFriends(Connection connection, long id) throws SQLException
    {
        String sql = "SELECT ID,FIRST_NAME,LAST_NAME FROM USERS WHERE ID IN (SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND CONFIRMED='Y')";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }

    private PreparedStatement getOutgoingRequests(Connection connection, long id) throws SQLException
    {
        String sql = "SELECT ID,FIRST_NAME,LAST_NAME FROM USERS WHERE ID IN (SELECT FRIEND_ID FROM FRIENDS WHERE USER_ID=? AND CONFIRMED='N')";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }

    private PreparedStatement getIncomingRequests(Connection connection, long id) throws SQLException
    {
        String sql = "SELECT ID,FIRST_NAME,LAST_NAME FROM USERS WHERE ID IN (SELECT USER_ID FROM FRIENDS WHERE FRIEND_ID=? AND CONFIRMED='N')";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }

    private CallableStatement addFriend(Connection connection, long userId, long friendId) throws SQLException
    {
        String sql = "{call ADD_FRIEND(?,?)}";
        CallableStatement callableStatement = connection.prepareCall(sql);
        callableStatement.setLong(1, userId);
        callableStatement.setLong(2, friendId);
        return callableStatement;
    }

    private CallableStatement removeFriend(Connection connection, long userId, long friendId) throws SQLException
    {
        String sql = "{call REMOVE_FRIEND(?,?)}";
        CallableStatement callableStatement = connection.prepareCall(sql);
        callableStatement.setLong(1, userId);
        callableStatement.setLong(2, friendId);
        return callableStatement;
    }

    private CallableStatement rejectRequest(Connection connection, long userId, long friendId) throws SQLException
    {
        String sql = "{call REJECT_REQUEST(?,?)}";
        CallableStatement callableStatement = connection.prepareCall(sql);
        callableStatement.setLong(1, userId);
        callableStatement.setLong(2, friendId);
        return callableStatement;
    }

    private CallableStatement revokeRequest(Connection connection, long userId, long friendId) throws SQLException
    {
        String sql = "{call REVOKE_REQUEST(?,?)}";
        CallableStatement callableStatement = connection.prepareCall(sql);
        callableStatement.setLong(1, userId);
        callableStatement.setLong(2, friendId);
        return callableStatement;
    }

}
