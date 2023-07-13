package ru.kostapo.telrostestwebapp.dto;

import lombok.*;
import ru.kostapo.telrostestwebapp.entity.Role;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String username;
    private String password;
    private Role role;
    private String firstname;
    private String lastname;
    private String surname;
    private String dob;
    private String email;
    private String phone;
}
