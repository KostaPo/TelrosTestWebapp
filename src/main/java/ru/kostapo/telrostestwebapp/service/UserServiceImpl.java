package ru.kostapo.telrostestwebapp.service;

import lombok.extern.log4j.Log4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kostapo.telrostestwebapp.dao.UserRepository;
import ru.kostapo.telrostestwebapp.dto.UserDTO;
import ru.kostapo.telrostestwebapp.entity.Role;
import ru.kostapo.telrostestwebapp.entity.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Log4j
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> userList = userRepository.findAllByRole(Role.USER);
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : userList) {
            UserDTO dto = UserDTO.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .role(user.getRole())
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .surname(user.getSurname())
                    .dob(user.getDob().toString())
                    .email(user.getEmail())
                    .phone(user.getPhone())
                    .build();
            userDTOList.add(dto);
        }
        log.debug("get all users list in service");
        return userDTOList;
    }

    @Override
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User with id = " + id + " not exist!"));
        log.debug("find user by id:" + id + " in service");
        return UserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .surname(user.getSurname())
                .dob(user.getDob().toString())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }

    @Override
    public UserDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username = " + username + " not exist!"));
        log.debug("find user by username:" + username + " in service");
        return UserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .surname(user.getSurname())
                .dob(user.getDob().toString())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }

    @Override
    public void save(UserDTO userDTO) {
        User user = User.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(Role.USER)
                .firstname(userDTO.getFirstname())
                .lastname(userDTO.getLastname())
                .surname(userDTO.getSurname())
                .dob(LocalDate.parse(userDTO.getDob()))
                .email(userDTO.getEmail())
                .phone(userDTO.getPhone())
                .build();
        userRepository.save(user);
        log.debug("add new user username:" + userDTO.getUsername() + " in service");
    }

    @Override
    public void update(UserDTO userDTO) {
        User user = userRepository.findByUsername(userDTO.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User with username = " + userDTO.getUsername() + " not exist!"));
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setSurname(userDTO.getSurname());
        user.setDob(LocalDate.parse(userDTO.getDob()));
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        userRepository.save(user);
        log.debug("update user by username:" + userDTO.getUsername() + " in service");
    }

    @Override
    public void delete(String username) {
        log.debug("delete user by username:" + username + " in service");
        userRepository.deleteByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username = " + username + " not exist!"));
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
        log.debug("load UserDetails username:" + username + " role:" + user.getRole().name() + " in service");
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                roles
        );
    }
}
