package ru.itmo.blss1.data.dto;

import lombok.Data;

@Data
public class UserDTO {
    public String login;
    public String password;
    public String email;
}
