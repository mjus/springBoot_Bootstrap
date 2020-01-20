package web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import web.model.Role;
import web.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class UserServiceImp implements UserService, UserDetailsService {

    private final RestTemplate restTemplate;
    private final String serverUrl;

    public UserServiceImp(RestTemplate restTemplate, @Value("${application.server.url}") String serverUrl) {
        this.restTemplate = restTemplate;
        this.serverUrl = serverUrl;
    }

    @Override
    public void add(User user) {
//        Set<Role> roles = new HashSet<>();
//        for (Role name : user.getRoles()) {
//            roles.add(roleDao.getRoleById(name.getId()));
//        }
//        user.setRoles(roles);
//        userDao.save(user);
        HttpEntity<User> requestEntity = new HttpEntity<>(user);
        restTemplate.exchange(serverUrl + "/api", HttpMethod.POST, requestEntity, User.class);
    }

    @Override
    public User getUserById(long id) {
        return restTemplate.getForObject(serverUrl + "/api/users/" + id, User.class);
    }

    @Override
    public Role getRoleById(long id) {
        return restTemplate.getForObject(serverUrl + "/api/role/" + id, Role.class);
    }

    @Override
    public List<User> getAllUsers() {
        return restTemplate.exchange(serverUrl + "/api/users", HttpMethod.GET, null
                , new ParameterizedTypeReference<List<User>>() {
        }).getBody();
    }

    @Override
    public List<Role> getAllRoles() {
        return restTemplate.exchange(serverUrl + "/api/roles", HttpMethod.GET, null
                , new ParameterizedTypeReference<List<Role>>() {
        }).getBody();
    }

    @Override
    public void update(User user) {
//        Set<Role> roles = new HashSet<>();
//        for (Role name : user.getRoles()) {
//            roles.add(roleDao.getRoleById(name.getId()));
//        }
//        user.setRoles(roles);
//        userDao.save(user);
        HttpEntity<User> requestEntity = new HttpEntity<>(user);
        restTemplate.exchange(serverUrl + "/api", HttpMethod.PUT, requestEntity, User.class);
    }

    @Override
    public void delete(long id) {
        restTemplate.delete(serverUrl + "/api/" + id, User.class);
    }

    @Override
    public UserDetails loadUserByUsername(String login) {
//        User user = userDao.getUserByLogin(login);
//        if (user == null) {
//            throw new UsernameNotFoundException(login);
//        }
        User user = restTemplate.postForObject("http://localhost:8080/" + login, getEntity("jsonObject"), User.class);
        if (user == null) {
            throw new UsernameNotFoundException(login);
        }
        return user;
    }

    public static HttpEntity<String> getEntity(String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String token = Base64.getEncoder().encodeToString("s:s".getBytes());
        headers.set("Authorization", "Basic " + token);

        return new HttpEntity<>(body,headers);
    }
}