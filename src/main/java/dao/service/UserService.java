package dao.service;

import dao.models.User;
import dao.models.dto.NewUser;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    String create(NewUser user) throws SQLException;

    User update(User user) throws SQLException;

    List<User> getAll() throws SQLException;

    User findById(String id) throws SQLException;

    void deleteById(String id) throws SQLException;
}
