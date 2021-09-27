package ru.itmo.blss1.service;

import bitronix.tm.BitronixTransactionManager;
import bitronix.tm.TransactionManagerServices;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.blss1.data.dto.DashboardDTO;
import ru.itmo.blss1.data.dto.UserDTO;
import ru.itmo.blss1.data.entity.Dashboard;
import ru.itmo.blss1.data.entity.Pin;
import ru.itmo.blss1.data.entity.User;
import ru.itmo.blss1.data.repository.DashboardRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class DashboardService {
    UserService userService;
    DashboardRepository dashboardRepository;
    PinService pinService;

    public Dashboard newDashboard(DashboardDTO dashboardDTO) {
        Dashboard dashboard = new Dashboard();
        dashboard.setName(dashboardDTO.name);
        dashboard.setOwner(userService.getById(dashboardDTO.ownerId));
        Integer count = dashboardRepository.countDashboardsByOwnerAndName(userService.getById(dashboardDTO.ownerId), dashboardDTO.name);
        if (count > 0)
            throw new IllegalArgumentException("Dashboard with name " + dashboardDTO.name
                    + " already exists");
        return dashboardRepository.save(dashboard);
    }

    public List<Dashboard> getDashboardsByUser(String userId) {
        return dashboardRepository.getAllByOwner(userService.getById(userId));
    }

    public Dashboard getById(int dashboardId) {
        return dashboardRepository.getById(dashboardId);
    }

    public Dashboard addToDashboard(String user_id, int pin_id, String dashboard_name) {
        BitronixTransactionManager btm = TransactionManagerServices.getTransactionManager();
        try {
            btm.begin();
            if (dashboardRepository.countDashboardsByOwnerAndName(userService.getById(user_id), dashboard_name) == 0) {
                newDashboard(new DashboardDTO(dashboard_name, user_id));
            }
            Dashboard dashboard = dashboardRepository.getDashboardsByOwnerAndName(userService.getById(user_id), dashboard_name).get(0);
            dashboard.getPins().add(pinService.getById(pin_id));
            dashboardRepository.deleteById(dashboard.getId());
            Dashboard res =dashboardRepository.save(dashboard);
            btm.commit();
            return res;
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                btm.rollback();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
