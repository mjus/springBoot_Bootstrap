package web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
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
public class UserServiceImp implements UserService {

    private final RestTemplate restTemplate;
    private final String serverUrl;

    public UserServiceImp(RestTemplate restTemplate, @Value("${application.server.url}") String serverUrl) {
        this.restTemplate = restTemplate;
        this.serverUrl = serverUrl;
    }

    @Override
    public void add(User user) {
        HttpEntity<User> requestEntity = new HttpEntity<>(user);
        restTemplate.exchange(serverUrl + "/api/users", HttpMethod.POST, requestEntity, User.class);
    }

    @Override
    public User getUserById(long id) {
        return restTemplate.getForObject(serverUrl + "/api/users/" + id, User.class);
    }

    @Override
    public List<User> getAllUsers() {
        return restTemplate.exchange(serverUrl + "/api/users", HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
        }).getBody();
    }

    @Override
    public List<Role> getAllRoles() {
        return restTemplate.exchange(serverUrl + "/api/roles", HttpMethod.GET, null, new ParameterizedTypeReference<List<Role>>() {
        }).getBody();
    }

    @Override
    public void update(User user) {
        HttpEntity<User> requestEntity = new HttpEntity<>(user);
        ResponseEntity<User> responseEntity =restTemplate.exchange(serverUrl + "/api", HttpMethod.PUT, requestEntity, User.class);
    }

//    @Override
//    public void delete(long id) {
//        User user = new User();
//        restTemplate.getForObject(serverUrl + "/api" + id, HttpMethod.PUT, User.class);
//    }

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