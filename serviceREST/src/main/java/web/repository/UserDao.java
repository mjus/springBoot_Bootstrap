package web.repository;

import web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User, Long> {
    User save(User user);

    User getById(long id);

    List<User> findAll();

    void deleteById(long id);

    User getUserByLogin(String login);
}