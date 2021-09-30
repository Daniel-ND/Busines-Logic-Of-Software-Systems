package ru.itmo.blss1.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itmo.blss1.data.dto.UserDTO;
import ru.itmo.blss1.data.entity.Role;
import ru.itmo.blss1.data.entity.User;
import ru.itmo.blss1.data.repository.UsersRepository;

@Service
@AllArgsConstructor
public class UserService {
    private final UsersRepository usersRepository;

    public User getById(String id) {
        return usersRepository.getByLogin(id);
    }

    public User newUser(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.login);
        user.setPassword(userDTO.password);
        user.setRole(Role.ROLE_USER);
        return usersRepository.save(user);
    }

    public void deleteUser(String login) {
        usersRepository.deleteByLogin(login);
    }

    public User loadUserByUsername(String s) {
        return usersRepository.getByLogin(s);
    }
}
