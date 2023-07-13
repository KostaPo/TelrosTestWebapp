package ru.kostapo.telrostestwebapp.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicUpdate
@Entity
@Table (indexes = @Index(columnList = "username"), name = "users" )
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank (message = "USERNAME CAN'T BE BLANK")
    @Column(unique=true)
    private String username;

    @NotBlank (message = "PASSWORD CAN'T BE BLANK")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String firstname;

    private String lastname;

    private String surname;

    private LocalDate dob;

    private String email;

    private String phone;
}
