package dao.connection;

import java.sql.Connection;
import java.sql.SQLException;

public interface DbConnection {
    Connection getConnection();
    void close() throws SQLException;
}
