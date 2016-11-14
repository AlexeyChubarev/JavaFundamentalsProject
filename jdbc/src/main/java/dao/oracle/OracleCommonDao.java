package dao.oracle;

import dao.CommonDao;
import lombok.AllArgsConstructor;
import model.Image;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Optional;
import java.util.function.Supplier;

@AllArgsConstructor
public class OracleCommonDao implements CommonDao
{
    private Supplier<Connection> connectionSupplier;

    @Override
    public Optional<Image> getUserImage(long id)
    {
        Optional<Image> image = Optional.empty();

        try (Connection connection = connectionSupplier.get();
             PreparedStatement ps = getUserImage(connection, id);
             ResultSet resultSet = ps.executeQuery())
        {
            while (resultSet.next())
            {
                if (resultSet.getBlob("IMAGE")!=null)
                    image = Optional.of(new Image(resultSet.getBlob("IMAGE")));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    public void setUserImage(InputStream image, long id)
    {
        try (Connection connection = connectionSupplier.get();
             CallableStatement cs = setUserImage(connection, id, image))
        {
            cs.executeUpdate();
        }
        catch (SQLException | IOException e)
        {
            e.printStackTrace();
        }
    }

    private PreparedStatement getUserImage(Connection connection, long id) throws SQLException
    {
        String sql = "SELECT IMAGE FROM USERS WHERE ID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }

    private CallableStatement setUserImage(Connection connection, long id, InputStream image) throws SQLException, IOException
    {
        String sql = "{call SET_USER_IMAGE(?,?)}";
        CallableStatement callableStatement = connection.prepareCall(sql);
        callableStatement.setLong(1, id);
        callableStatement.setBlob(2, image);
        image.close();
        return callableStatement;
    }
}
