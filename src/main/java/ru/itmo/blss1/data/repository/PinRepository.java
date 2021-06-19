package ru.itmo.blss1.data.repository;

import org.springframework.data.repository.CrudRepository;
import ru.itmo.blss1.data.entity.Dashboard;
import ru.itmo.blss1.data.entity.Pin;

public interface PinRepository extends CrudRepository<Pin, Integer> {

}
