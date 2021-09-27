package ru.itmo.blss1.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itmo.blss1.data.dto.DashboardDTO;
import ru.itmo.blss1.data.dto.PinDTO;
import ru.itmo.blss1.data.entity.Pin;
import ru.itmo.blss1.data.entity.User;
import ru.itmo.blss1.service.PinService;

@RestController
@RequestMapping("/pins")
@AllArgsConstructor
@Api(tags = {"pins"}, description = "Управление pins")
public class PinsController {
    PinService pinService;

    @PostMapping
    @ApiOperation("Создать пин")
    @PreAuthorize("isAuthenticated() and (hasRole('ADMIN') or hasRole('USER'))")
    public Pin newPin(@RequestBody PinDTO pinDTO) {
        return pinService.newPin(pinDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated() and (hasRole('ADMIN') or hasRole('USER'))")
    @ApiOperation("Получить пин по id")
    public Pin getPinById(@PathVariable int id) {
        return pinService.getById(id);
    }

    @GetMapping("/get_all")
    @PreAuthorize("isAuthenticated() and (hasRole('ADMIN') or hasRole('USER'))")
    @ApiOperation("Получить все пины")
    public Iterable<Pin> getAll(){
        return pinService.getAll();
    }

}
