package web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import web.model.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import web.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestUserController {

    private final UserService userService;

    public RestUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAll(){
        return userService.getAllUsers();
    }

    @GetMapping ("/{id}")
    public User get(@PathVariable long id){
        return userService.getUserById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create(@RequestBody User user) {
        User created = userService.add(user);
        userService.add(user);
    }

    @PutMapping
    public void edit(@RequestBody User user) {
        userService.add(user);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Integer id){
        userService.delete(id);
    }
}