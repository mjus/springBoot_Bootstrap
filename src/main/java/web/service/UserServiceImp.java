package web.service;

import web.model.Role;
import web.model.User;
import web.repository.RoleDao;
import web.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImp implements UserService, UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Transactional
    @Override
    public void add(User user) {
        Set<Role> roles = new HashSet<>();
        for (Role name : user.getRoles()) {
//            long id = 1;
            roles.add(roleDao.getRoleById(name.getId()));
        }
        user.setRoles(roles);
        userDao.save(user);
    }

    @Transactional
    @Override
    public User getUserById(long id) {
        return userDao.getById(id);
    }

    @Transactional
    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDao.findAll();
    }

    @Transactional
    @Override
    public void update(User user) {
        userDao.save(user);
    }

    @Transactional
    @Override
    public void delete(long id) {
        userDao.deleteById(id);
    }

    @Transactional
    @Override
    public Role getRoleById(Long id) {
        return roleDao.getRoleById(id);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String login) {
        User user = userDao.getUserByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException(login);
        }
        return user;
    }
}