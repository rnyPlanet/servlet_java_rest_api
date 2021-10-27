package dao.repository.impl;

import dao.connection.DbConnection;
import dao.models.User;
import dao.models.dto.NewUser;
import dao.repository.UserRepository;

import java.sql.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepositoryImpl implements UserRepository {
    Connection connection;

    public UserRepositoryImpl(DbConnection connection) throws SQLException {
        this.connection = connection.getConnection();
    }

    @Override
    public String create(NewUser newUser) throws SQLException {
        String id = UUID.randomUUID().toString();

        String sql = "INSERT INTO \"user\" (id, login, password) VALUES (?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, id);
        preparedStatement.setString(2, newUser.getLogin());
        preparedStatement.setString(3, newUser.getPassword());

        preparedStatement.executeUpdate();

        preparedStatement.close();

        return id;
    }

    @Override
    public User update(User user) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement(
                "UPDATE \"user\" SET login = ?, password = ? WHERE id = ?;");

        ps.setString(1, user.getLogin());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getId());

        ps.executeUpdate();
        ps.close();

        return findById(user.getId()).orElse(null);
    }

    @Override
    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<>();

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM \"user\";" );
        while ( rs.next() ) {
            String id = rs.getString("id");
            String login = rs.getString("login");
            String password  = rs.getString("password");

            users.add(
                    User.builder()
                        .id(id)
                        .login(login)
                        .password(password)
                        .build()
            );
        }
        rs.close();
        stmt.close();

        return users;
    }

    @Override
    public Optional<User> findById(String uid) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM \"user\" where id = '" + uid + "';" );
        while ( rs.next() ) {
            String id = rs.getString("id");
            String login = rs.getString("login");
            String password  = rs.getString("password");

            return Optional.of(User.builder()
                    .id(id)
                    .login(login)
                    .password(password)
                    .build());
        }
        rs.close();
        stmt.close();

        return Optional.empty();
    }

    @Override
    public void deleteById(String id) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate( "DELETE FROM \"user\" where id = '" + id + "';" );
    }
}
