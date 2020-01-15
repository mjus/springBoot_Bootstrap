package web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;
import web.model.Role;
import web.model.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import web.model.UserTo;
import web.service.UserService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @GetMapping(value = "/roles")
    public ResponseEntity<List<Role>> getRoles() {
        List<Role> roles = userService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> userAdd(@Valid @RequestBody UserTo userTo) {
        if (isValidate(userTo.getLogin(), userTo.getPassword(), userTo.getPassword())) {
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(new Role(userTo.getRoles()));
            User user = new User(userTo.getLogin(), userTo.getPassword(), userTo.getEmail(), roleSet);
            userService.add(user);
            return ResponseEntity.ok(HttpStatus.CREATED);
        }
        return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public void edit(@RequestBody User user) {
        userService.update(user);
    }

    @GetMapping (value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(@PathVariable("id") int id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Integer id){
        userService.delete(id);
    }

    boolean isValidate(String s1, String s2, String s3) {
        return !s1.isEmpty() && !s2.isEmpty() && !s3.isEmpty();
    }
}