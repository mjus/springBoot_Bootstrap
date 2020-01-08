package web.rest;

import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/myservice")
public class RestUserController {

    private UserService userService;

    @GetMapping
    public List<User> list(){
        return userService.getAllUsers();
    }

    @PostMapping
    public void insert(@RequestBody User user) {
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