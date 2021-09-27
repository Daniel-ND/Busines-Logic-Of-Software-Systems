package ru.itmo.blss1.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itmo.blss1.data.dto.UserDTO;
import ru.itmo.blss1.data.entity.User;
import ru.itmo.blss1.service.UserService;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Api(tags = {"users"}, description = "Управление пользователями")
public class UsersController {
    private final UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated() and (hasRole('ADMIN') or hasRole('USER'))")
    @ApiOperation("Получить пользователя по id")
    public User getUserById(@PathVariable String login) {
        return userService.getById(login);
    }

    @PostMapping
    @ApiOperation("Создать пользователя")
    @PreAuthorize("isAuthenticated() and (hasRole('ADMIN') or hasRole('USER'))")
    public User newUser(@RequestBody UserDTO userDTO) {
        return userService.newUser(userDTO);
    }
}
