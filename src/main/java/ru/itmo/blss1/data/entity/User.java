package ru.itmo.blss1.data.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
@Table(name = "users")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    private String login;
    private String password;
    @CreationTimestamp
    private LocalDateTime whenRegistered;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return  id.equals(user.id) &&
                login.equals(user.login) &&
                password.equals(user.password) &&
                Objects.equals(whenRegistered, user.whenRegistered);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, whenRegistered);
    }

}
