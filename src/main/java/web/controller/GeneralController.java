package web.controller;

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

    private final UserService userService;

    public GeneralController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public View test() {
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

    @GetMapping(value = "users")
    public ModelAndView getUsers() {
        List<User> users = userService.getAllUsers();
        ModelAndView modelAndView = new ModelAndView("users");
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @GetMapping(value = "users/update/{id}")
    public ModelAndView getUserUpdate(@PathVariable("id") long userId) {
        User user = userService.getUserById(userId);
        ModelAndView modelAndView = new ModelAndView("update");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping(value = "users/add")
    public View addUser(@RequestParam(value = "login") String login,
                        @RequestParam(value = "password") String password,
                        @RequestParam(value = "role") String role,
                        @RequestParam(value = "email") String email) {
        if (isValidate(login, password, email)) {
            Set<Role> roles = new HashSet<>();
            role = role.toLowerCase();
            if(role.contains("admin")){
                roles.add(new Role("ROLE_ADMIN"));
            } else if(role.contains("user")){
                roles.add(new Role("ROLE_USER"));
            } else roles.add(new Role(role));
            User user = new User(login, password, email,roles);
            userService.add(user);
        }
        return new RedirectView("/users");
    }

    @PostMapping(value = "users/update/{id}")
    public View updateUser(@PathVariable("id") long userId,
                           @RequestParam(value = "login") String login,
                           @RequestParam(value = "password") String password,
                           @RequestParam(value = "role") String role,
                           @RequestParam(value = "email") String email) {

        Set<Role> roles = new HashSet<>();
        if(role.contains("admin")){
            roles.add(new Role("ROLE_ADMIN"));
        } else if(role.contains("user")){
            roles.add(new Role("ROLE_USER"));
        } else roles.add(new Role(role));
        userService.update(new User(userId, login, password, email, roles));

        return new RedirectView("/users");
    }

    @PostMapping(value = "users/delete/{id}")
    public View deleteUser(@PathVariable("id") long userId) {
        userService.delete(userId);
        return new RedirectView("/users");
    }

    boolean isValidate(String s1, String s2, String s3) {
        return !s1.isEmpty() && !s2.isEmpty() && !s3.isEmpty();
    }
}