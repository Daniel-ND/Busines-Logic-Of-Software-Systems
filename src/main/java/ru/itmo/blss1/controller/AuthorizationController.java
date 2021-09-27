package ru.itmo.blss1.controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.blss1.data.dto.AuthorizationDTO;
import ru.itmo.blss1.service.AuthorizationService;
import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Api(tags = {"authorization"}, description = "Авторизация")
public class AuthorizationController {
    private final AuthorizationService authorizationService;
    @PostMapping(path = "/login",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("permitAll()")
    public AuthorizationDTO authorize(final @RequestBody @Valid AuthorizationDTO authorizationDto)
            throws Exception {
        return authorizationService.authorize(authorizationDto);
    }
}
