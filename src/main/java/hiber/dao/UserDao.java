package hiber.dao;

import hiber.model.User;

import java.util.List;

public interface UserDao {
    void add(User user);
    void delete(User user);
    void update(long id, User user);
    List<User> listUsers();
    User getUserById(long id);
}
