package ru.kostapo.telrostestwebapp.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kostapo.telrostestwebapp.dto.UserDTO;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserDTO> getAllUsers();
    UserDTO findById(Long id);
    UserDTO findByUsername(String username);
    void save(UserDTO userDTO);
    void update(UserDTO userDTO);
    void delete(Long id);
}
