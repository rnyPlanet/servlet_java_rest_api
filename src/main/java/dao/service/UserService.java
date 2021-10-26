package dao.service;

import dao.models.User;
import dao.models.dto.NewUser;

import java.util.List;

public interface UserService {
    String create(NewUser user);

    List<User> getAll();

    User findById(String id);

    void deleteById(String id);
}
