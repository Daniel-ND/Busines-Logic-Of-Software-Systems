package ru.itmo.blss1.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.blss1.data.dto.UserDTO;
import ru.itmo.blss1.data.entity.Role;
import ru.itmo.blss1.data.entity.User;
import ru.itmo.blss1.data.repository.UsersRepository;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class UserService {
    private final UsersRepository usersRepository;

    public User getById(String id) {
        return usersRepository.getById(id);
    }

    public User newUser(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.login);
        user.setPassword(userDTO.password);
        user.setRole(Role.USER);
        return usersRepository.save(user);
    }
}
