package ru.itmo.blss1.data.repository;

import org.springframework.data.repository.CrudRepository;
import ru.itmo.blss1.data.entity.Dashboard;
import ru.itmo.blss1.data.entity.User;

import java.util.List;

public interface DashboardRepository extends CrudRepository<Dashboard, Integer> {
    List<Dashboard> getAllByOwner(User owner);
    Dashboard getById(int id);
    Integer countDashboardsByOwnerAndName(User owner, String name);
    List<Dashboard> getDashboardsByOwnerAndName(User owner, String name);
}
