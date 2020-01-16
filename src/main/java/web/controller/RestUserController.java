package web.controller;

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
    public List<User> getUsers() {
        List<User> users = userService.getAllUsers();
        return users;
    }

    @GetMapping(value = "/user/{id}")
    public User getUserTo(@PathVariable("id") int id) {
        return userService.getUserById(id);
    }

    @GetMapping(value = "/roles")
    public List<Role> getRoles() {
        List<Role> roles = userService.getAllRoles();
        return roles;
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