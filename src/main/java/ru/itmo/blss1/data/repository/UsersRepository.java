package ru.itmo.blss1.data.repository;

import org.springframework.data.repository.CrudRepository;
import ru.itmo.blss1.data.entity.User;

public interface UsersRepository extends CrudRepository<User, Integer> {
}

