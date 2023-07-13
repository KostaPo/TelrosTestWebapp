package ru.kostapo.telrostestwebapp.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kostapo.telrostestwebapp.dto.UserDTO;
import ru.kostapo.telrostestwebapp.entity.Role;
import ru.kostapo.telrostestwebapp.service.UserService;

import java.util.List;

@Log4j
@Controller
@RequestMapping
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String findAll(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDTO userDTO = userService.findByUsername(authentication.getName());
        if (userDTO.getRole().equals(Role.ADMIN)) {
            List<UserDTO> users = userService.getAllUsers();
            model.addAttribute("users", users);
            log.debug("find all for ADMIN role");
            return "users";
        } else {
            model.addAttribute("user", userDTO);
            log.debug("user info for USER role");
            return "info";
        }
    }

    @GetMapping("/users/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAddUserForm(UserDTO userDTO) {
        return "create";
    }

    @PostMapping("/users/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String addUser(UserDTO userDTO) {
        userService.save(userDTO);
        log.debug("ADMIN add new user with username:"+userDTO.getUsername());
        return "redirect:/users";
    }

    @GetMapping("/users/delete/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(@PathVariable("username") String username) {
        userService.delete(username);
        log.debug("ADMIN delete user by username:"+username);
        return "redirect:/users";
    }

    @GetMapping("/users/update/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public String getUpdateForm(@PathVariable("username") String username, Model model){
        UserDTO userDTO = userService.findByUsername(username);
        model.addAttribute("user", userDTO);
        return "update";
    }

    @PostMapping("/users/update")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateUser(UserDTO userDTO){
        userService.update(userDTO);
        log.debug("ADMIN update user info for username:"+userDTO.getUsername());
        return "redirect:/users";
    }
}
