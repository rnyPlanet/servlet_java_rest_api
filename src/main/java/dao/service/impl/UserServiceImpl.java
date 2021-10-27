package dao.service.impl;

import dao.models.User;
import dao.models.dto.NewUser;
import dao.repository.UserRepository;
import dao.service.UserService;
import errors.user.UserDoesNotExistException;
import lombok.Value;

import java.sql.SQLException;
import java.util.List;

@Value
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String create(NewUser newUser) throws SQLException {
        return userRepository.create(newUser);
    }

    @Override
    public User update(User user) throws SQLException {
        return userRepository.update(user);
    }

    @Override
    public List<User> getAll() throws SQLException {
        return userRepository.getAll();
    }

    @Override
    public User findById(String id) throws SQLException {
        return userRepository.findById(id).orElseThrow(() -> new UserDoesNotExistException(id));
    }

    @Override
    public void deleteById(String id) throws SQLException {
        userRepository.deleteById(id);
    }
}
