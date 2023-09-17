package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entity.Role;

@Service
public interface RoleService {

    Role findRoleByAuthority(String authority);
}
