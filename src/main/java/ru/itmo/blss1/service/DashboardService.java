package ru.itmo.blss1.service;

import bitronix.tm.BitronixTransactionManager;
import bitronix.tm.TransactionManagerServices;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.blss1.controller.DashboardsController;
import ru.itmo.blss1.data.dto.DashboardDTO;
import ru.itmo.blss1.data.dto.PinToDashboardDTO;
import ru.itmo.blss1.data.entity.Dashboard;
import ru.itmo.blss1.data.repository.DashboardRepository;

import javax.transaction.SystemException;
import javax.transaction.Transaction;
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
        dashboard.setOwner(userService.getById(dashboardDTO.getOwnerLogin()));
        Integer count = dashboardRepository.countDashboardsByOwnerAndName(userService.getById(dashboardDTO.getOwnerLogin()), dashboardDTO.name);
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

//    @Transactional
    public Dashboard addToDashboard(PinToDashboardDTO pinToDashboardDTO) {
        Logger logger = LoggerFactory.getLogger(DashboardService.class);
//        BitronixTransactionManager btm = TransactionManagerServices.getTransactionManager();
        Transaction trx = null;
        try {
            trx = TransactionManagerServices.getTransactionManager().getTransaction();
        } catch (SystemException e) {
            e.printStackTrace();
        }
        try {
            if (dashboardRepository.countDashboardsByOwnerAndName(userService.getById(pinToDashboardDTO.getUserLogin()), pinToDashboardDTO.getDashboardName()) == 0) {
                logger.info("dashboard not found");
                newDashboard(new DashboardDTO(pinToDashboardDTO.getDashboardName(), pinToDashboardDTO.getUserLogin()));
            }
            Dashboard dashboard = dashboardRepository
                    .getDashboardsByOwnerAndName(userService.getById(pinToDashboardDTO.getUserLogin()),
                            pinToDashboardDTO.getDashboardName()).get(0);
            dashboard.getPins().add(pinService.getById(pinToDashboardDTO.getPinId()));
//            dashboardRepository.deleteById(dashboard.getId());
            dashboard.setName("joppa2");
            Dashboard res = dashboardRepository.save(dashboard);
            trx.commit();
            return res;
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                trx.rollback();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
