package ru.itmo.blss1.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.blss1.data.dto.UserDTO;
import ru.itmo.blss1.data.entity.User;
import ru.itmo.blss1.data.repository.UsersRepository;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {
    private final UsersRepository usersRepository;

    public User getById(int id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    public User newUser(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.login);
        user.setPassword(userDTO.password);
        return usersRepository.save(user);
    }
}
