package web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.model.User;

import java.util.List;

public interface UserDao extends JpaRepository<User, Long> {
    User save(User user);

    User getById(long id);

    List<User> findAll();

    void deleteById(long id);

    User getUserByLogin(String login);
}