package ru.kostapo.telrostestwebapp.controller;

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
import ru.kostapo.telrostestwebapp.entity.User;
import ru.kostapo.telrostestwebapp.service.UserService;

import java.util.List;

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
            return "users"; // Вернуть представление для таблицы всех пользователей для администратора
        } else {
            model.addAttribute("user", userDTO);
            return "info"; // Вернуть представление для личной карточки авторизованного пользователя
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/users";
    }

    @GetMapping("/users/add")
    public String getAddUserForm(UserDTO userDTO) {
        return "create";
    }

    @PostMapping("/users/add")
    public String addUser(UserDTO userDTO) {
        userService.save(userDTO);
        return "redirect:/users";
    }

    @GetMapping("/users/update/{id}")
    public String getUpdateForm(@PathVariable("id") Long id, Model model){
        UserDTO userDTO = userService.findById(id);
        model.addAttribute("user", userDTO);
        return "update";
    }

    @PostMapping("/users/update")
    public String updateUser(UserDTO userDTO){
        userService.update(userDTO);
        return "redirect:/users";
    }
}
