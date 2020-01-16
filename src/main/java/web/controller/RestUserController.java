package web.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import web.model.Role;
import web.model.User;
import org.springframework.web.bind.annotation.*;
import web.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class RestUserController {

    private final UserService userService;

    public RestUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<User> getUserTo(@PathVariable("id") int id) {
        return ResponseEntity.ok(userService.getUserById(id));
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