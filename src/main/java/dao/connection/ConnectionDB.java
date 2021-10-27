package dao.connection;

import dao.connection.DbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionDB implements DbConnection {

    private String hostName = "localhost";
    private String hostPort = "5432";
    private String database = "avecoder";
    private String user = "postgres";
    private String password = "1";

    private Connection connection;

    public ConnectionDB() throws SQLException, ClassNotFoundException {
        StringBuilder url = new StringBuilder();

        url.append("jdbc:postgresql://");
        url.append(hostName);
        url.append(":");
        url.append(hostPort);
        url.append("/");
        url.append(database);

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);

        Class.forName("org.postgresql.Driver");
        this.connection = DriverManager.getConnection(url.toString(), props);
    }

    @Override
    public Connection getConnection() {
        return this.connection;
    }

    @Override
    public void close() throws SQLException {
        this.connection.close();
    }
}
