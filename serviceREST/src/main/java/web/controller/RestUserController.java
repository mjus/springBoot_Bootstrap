package web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.UserService;
import web.service.UserServiceImp;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class RestUserController {

    private final UserServiceImp userService;

    public RestUserController(UserServiceImp userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") int id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
//
//    @GetMapping(value = "/user/{login}")
//    public ResponseEntity<UserDetails> getUserByLogin(@PathVariable("login") String login) {
//        return ResponseEntity.ok(userService.loadUserByUsername(login));
//    }

    @GetMapping(value = "/role/{id}")
    public ResponseEntity<Role> getRole(@PathVariable("id") int id) {
        return ResponseEntity.ok(userService.getRoleById((long) id));
    }

    @GetMapping(value = "/roles")
    public ResponseEntity<List<Role>> getRoles() {
        List<Role> roles = userService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @PostMapping
    public void addUser(@RequestBody User user) {
        userService.add(user);
    }

    @PutMapping
    public void edit(@RequestBody User user) {
        userService.update(user);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Integer id){
        userService.delete(id);
    }
}