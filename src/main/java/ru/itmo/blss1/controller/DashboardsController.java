package ru.itmo.blss1.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itmo.blss1.config.jwt.JwtProvider;
import ru.itmo.blss1.data.dto.DashboardDTO;
import ru.itmo.blss1.data.dto.PinToDashboardDTO;
import ru.itmo.blss1.data.entity.Dashboard;
import ru.itmo.blss1.service.DashboardService;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/dashboards")
@AllArgsConstructor
@Api(tags = {"dashboards"}, description = "Управление dashboards")
public class DashboardsController {
    DashboardService dashboardService;
    //Logger logger;

//    @PostConstruct
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping
    @PreAuthorize("isAuthenticated() and (hasRole('ROLE_ADMIN') or hasRole('ROLE_USER'))")
    @ApiOperation("Создать доску")
    public Dashboard newDashboard(@RequestHeader HttpHeaders headers, @RequestBody DashboardDTO dashboardDTO) {
        dashboardDTO.setOwnerLogin(jwtProvider.getLoginFromToken(headers.getFirst(HttpHeaders.AUTHORIZATION)));
        return dashboardService.newDashboard(dashboardDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated() and (hasRole('ROLE_ADMIN') or hasRole('ROLE_USER'))")
    @ApiOperation("Получить dashboard по id")
    public Dashboard getDashboardById(@PathVariable int id){
        return dashboardService.getById(id);
    }

    @GetMapping("/by_owner/{owner_login}")
    @PreAuthorize("isAuthenticated() and (hasRole('ROLE_ADMIN') or hasRole('ROLE_USER'))")
    @ApiOperation("Получить все dashboard пользователя")
    public Iterable<Dashboard> getAllDashboardsByOwner(@PathVariable String owner_login){
        return dashboardService.getDashboardsByUser(owner_login);
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated() and (hasRole('ROLE_ADMIN') or hasRole('ROLE_USER'))")
    @ApiOperation("Добавить пин на доску")
    public Dashboard newDashboard(@RequestHeader HttpHeaders headers, @RequestBody PinToDashboardDTO pinToDashboardDTO) {
        pinToDashboardDTO.setUserLogin(jwtProvider.getLoginFromToken(headers.getFirst(HttpHeaders.AUTHORIZATION)));
        Logger logger = LoggerFactory.getLogger(DashboardsController.class);
        logger.info("dashboard: " + pinToDashboardDTO.getDashboardName());
        logger.info("user: " + pinToDashboardDTO.getUserLogin());
        logger.info("pin id: " + String.valueOf(pinToDashboardDTO.getPinId()));
        return dashboardService.addToDashboard(pinToDashboardDTO);
    }
}
