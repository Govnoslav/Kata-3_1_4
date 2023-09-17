package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.services.impl.UserServiceImpl;

import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userServiceImpl;


    @Autowired
    public AdminController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping
    public String getUsers(Model model, Authentication authentication) {

        model.addAttribute("currentUserRoles",userServiceImpl.getAllRolesFromCurrentUser(authentication));
        model.addAttribute("currentUser", userServiceImpl.findUserByUserName(authentication.getName()));
        model.addAttribute("users", userServiceImpl.findAllUsers());
        model.addAttribute("user", new User());

        return "admin/usersBootstrap";
    }

    @PostMapping("/new")
    public String createUser(@Valid User user,
                             BindingResult bindingResult,
                             String userRole,
                             Model model ,
                             Authentication authentication) throws Exception{

        if (bindingResult.hasErrors()) {
            User currentUser = userServiceImpl.findUserByUserName(authentication.getName());
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("users", userServiceImpl.findAllUsers());
            return "admin/usersBootstrapWithError";

        }

        userServiceImpl.createUser(user, userRole);
        return "redirect:/admin";
    }

    @ExceptionHandler(java.sql.SQLIntegrityConstraintViolationException.class)
    public String handleExceptions(Model model) {
        model.addAttribute("exception", new Exception("Email already exist"));
        return "error/error";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userServiceImpl.deleteUserById(id);
        return "redirect:/admin";
    }

    @PostMapping("/{id}")
    public String updateUser(@Valid User user, String userRole) {
        userServiceImpl.updateUser(user, userRole);
        return "redirect:/admin";
    }

}

