package ru.itmo.blss1.data.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

import static javax.persistence.EnumType.STRING;

@Data
@Entity
@Table(name = "users")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "login")
@OnDelete(action = OnDeleteAction.CASCADE)
public class User {
    @Id
    private String login;

    @JsonIgnore
    private String password;

    @CreationTimestamp
    private LocalDateTime whenRegistered;

    @Enumerated(STRING)
    @Column(nullable = false)
    private Role role;

    @Email
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return  login.equals(user.login) &&
                password.equals(user.password) &&
                Objects.equals(whenRegistered, user.whenRegistered);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, whenRegistered);
    }

}
