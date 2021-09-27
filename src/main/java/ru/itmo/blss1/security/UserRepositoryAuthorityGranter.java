package ru.itmo.blss1.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import ru.itmo.blss1.data.repository.UsersRepository;

import java.security.Principal;
import java.util.Set;

@RequiredArgsConstructor
public class UserRepositoryAuthorityGranter implements AuthorityGranter {

    private final UsersRepository usersRepository;

    @Override
    public Set<String> grant(Principal principal) {
        final var role = usersRepository.findRoleByLogin(principal.getName());
        return Set.of(role.toString());
    }
}
