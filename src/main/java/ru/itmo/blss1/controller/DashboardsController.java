package ru.itmo.blss1.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itmo.blss1.data.dto.DashboardDTO;
import ru.itmo.blss1.data.dto.PinToDashboardDTO;
import ru.itmo.blss1.data.dto.UserDTO;
import ru.itmo.blss1.data.entity.Dashboard;
import ru.itmo.blss1.data.entity.User;
import ru.itmo.blss1.service.DashboardService;

import java.util.List;

@RestController
@RequestMapping("/dashboards")
@AllArgsConstructor
@Api(tags = {"dashboards"}, description = "Управление dashboards")
public class DashboardsController {
    DashboardService dashboardService;

    @PostMapping
    @ApiOperation("Создать доску")
    public Dashboard newDashboard(@RequestBody DashboardDTO dashboardDTO) {
        return dashboardService.newDashboard(dashboardDTO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Получить dashboard по id")
    public Dashboard getDashboardById(@PathVariable int id){
        return dashboardService.getById(id);
    }

    @GetMapping("/by_owner/{owner_id}")
    @ApiOperation("Получить все dashboard пользователя")
    public Iterable<Dashboard> getAllDashboardsByOwner(@PathVariable int owner_id){
        return dashboardService.getDashboardsByUser(owner_id);
    }

    @PostMapping("/add")
    @ApiOperation("Добавить пин на доску")
    public Dashboard newDashboard(@RequestBody PinToDashboardDTO pinToDashboardDTO) {
        return dashboardService.addToDashboard(pinToDashboardDTO.user_id, pinToDashboardDTO.pin_id, pinToDashboardDTO.dashboardName);
    }
}