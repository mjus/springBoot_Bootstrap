package web.util;

import web.model.Role;
import web.model.User;
import web.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;

@Component
public class SetDB {
    @Autowired
    private UserDao userDao;

    private Role roleUser = new Role("ROLE_USER");
    private Role roleAdmin = new Role("ROLE_ADMIN");

    private User user = new User("a", "a", "a@a.com", new HashSet<>(Collections.singletonList(roleUser)));
    private User admin = new User("s", "s", "s@s.com", new HashSet<>(Collections.singletonList(roleAdmin)));

    public SetDB() {
    }

    @PostConstruct
    public void setDB() {
        if (userDao.getUserByLogin(admin.getLogin()) == null) {
            userDao.save(admin);
        }
        if (userDao.getUserByLogin(user.getLogin()) == null) {
            userDao.save(user);
        }
    }
}