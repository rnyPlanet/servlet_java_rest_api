package dao.repository;

import dao.models.User;
import dao.models.dto.NewUser;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    String create(NewUser user) throws SQLException;

    User update(User user) throws SQLException;

    List<User> getAll() throws SQLException;

    Optional<User> findById(String id) throws SQLException;

    void deleteById(String id) throws SQLException;
}
