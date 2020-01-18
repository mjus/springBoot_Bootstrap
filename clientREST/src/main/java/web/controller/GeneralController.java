package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import web.model.Role;
import web.model.User;
import web.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@Controller
@RequestMapping("/")
public class GeneralController {

    private UserService userService;

    @Autowired
    public GeneralController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public View general() {
        return new RedirectView("/users");
    }

    @GetMapping(value = "login")
    public String loginPage() {
        return "login";
    }

    @GetMapping(value = "hello")
    public String printWelcome(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        messages.add("User");
        model.addAttribute("messages", messages);
        return "hello";
    }
//
//    @GetMapping(value = "admin")
//    public String admin(){
//        return "users";
//    }

    @GetMapping(value = "admin")
    public ModelAndView admin() {
        ModelAndView modelAndView = new ModelAndView("users");
        return modelAndView;
    }

//    @GetMapping(value = "users")
//    public List<User> getUsers(){
//        return userService.getAllUsers();
//    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Role>> getRoles() {
        List<Role> roles = userService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

//    @GetMapping(value = "users/update/{id}")
//    public ModelAndView getUserUpdate(@PathVariable("id") long userId) {
//        User user = userService.getUserById(userId);
//        ModelAndView modelAndView = new ModelAndView("update");
//        modelAndView.addObject("user", user);
//        return modelAndView;
//    }

    @GetMapping(value = "/users/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserTo(@PathVariable("id") int id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping(value = "users/add")
    public String addUser(@RequestBody User user) {
        userService.add(user);
        return "users";
    }
//
//    @PostMapping(value = "users/add")
//    public View addUser(@ModelAttribute User user, @RequestParam("rolesNewUser") Long[] idRoles) {
//        userService.add(user);
//        return new RedirectView("/users");
//    }

    @PutMapping(value = "users/update/{id}")
    public String updateUser(@RequestBody User user) {
        userService.update(user);
        return "users";
    }

    @DeleteMapping(value = "users/{id}")
    public String deleteUser(@PathVariable("id") long userId) {
        userService.delete(userId);
        return "users";
    }

    private void refreshUser (User user, Long[] idRoles) {
        Set<Role> roles = new HashSet<>();
        for (int i = 0; i < idRoles.length; i++) {
            Role role = userService.getRoleById(idRoles[i]);
            roles.add(role);
        }
        user.setRoles(roles);
    }
}