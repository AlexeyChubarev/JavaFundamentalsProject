package dao.oracle;

import dao.CommonDao;
import lombok.AllArgsConstructor;
import model.Image;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.function.Supplier;

@AllArgsConstructor
public class OracleCommonDao implements CommonDao
{
    private Supplier<Connection> connectionSupplier;

    @Override
    public Optional<Image> getImageById(long id)
    {
        Optional<Image> image = Optional.empty();

        try (Connection connection = connectionSupplier.get();
             PreparedStatement ps = getUserPhoto(connection, id);
             ResultSet resultSet = ps.executeQuery())
        {
            while (resultSet.next())
            {
                if (resultSet.getBlob("PHOTO")!=null)
                    image = Optional.of(new Image(resultSet.getBlob("PHOTO")));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return image;
    }

    private PreparedStatement getUserPhoto(Connection connection, long id) throws SQLException
    {
        // TODO: 08.11.2016 Изменить в БД PHOTO на IMAGE
        String sql = "SELECT PHOTO FROM USERS WHERE ID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }
}
