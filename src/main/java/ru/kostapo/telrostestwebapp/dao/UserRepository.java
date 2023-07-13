package ru.kostapo.telrostestwebapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kostapo.telrostestwebapp.entity.Role;
import ru.kostapo.telrostestwebapp.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long> {
    List<User> findAllByRole(Role role);
    Optional<User> findByUsername(String username);

    @Modifying
    @Query("DELETE FROM User where username = :username")
    void deleteByUsername(@Param("username") String username);
}
