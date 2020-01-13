package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
        List<Role> listRoles = userService.getAllRoles();
        ModelAndView modelAndView = new ModelAndView("users");
        modelAndView.addObject("users", users);
        modelAndView.addObject("listRoles", listRoles);
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
    public View addUser(@ModelAttribute User user, @RequestParam("rolesNewUser") Long[] idRoles) {
        refreshUser(user, idRoles);
        userService.add(user);
        return new RedirectView("/users");
    }

    @PostMapping(value = "users/update/{id}")
    public View updateUser(@ModelAttribute User user, @RequestParam("rolesNewUser") Long[] idRoles) {
        refreshUser(user, idRoles);
        userService.update(user);
        return new RedirectView("/users");
    }

    @PostMapping(value = "users/delete/{id}")
    public View deleteUser(@PathVariable("id") long userId) {
        userService.delete(userId);
        return new RedirectView("/users");
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