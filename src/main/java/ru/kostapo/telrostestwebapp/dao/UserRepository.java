package ru.kostapo.telrostestwebapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kostapo.telrostestwebapp.entity.Role;
import ru.kostapo.telrostestwebapp.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long> {
    List<User> findAllByRole(Role role);
    Optional<User> findByUsername(String username);
}
