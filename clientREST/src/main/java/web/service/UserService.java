package web.service;

import web.model.Role;
import web.model.User;

import java.util.List;

public interface UserService {
    void add(User user);

    User getUserById(long id);

    List<User> getAllUsers();

    List<Role> getAllRoles();

    void update(User user);

    void delete(long id);

    Role getRoleById(long idRole);
}
