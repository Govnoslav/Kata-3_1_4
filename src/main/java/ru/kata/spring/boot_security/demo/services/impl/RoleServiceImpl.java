package ru.kata.spring.boot_security.demo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDAO;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.services.RoleService;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    private final RoleDAO roleDAOImpl;

    @Autowired
    public RoleServiceImpl(RoleDAO roleDAO) {
        this.roleDAOImpl = roleDAO;
    }

    @Override
    public Role findRoleByAuthority(String authority) {
        return roleDAOImpl.findRoleByAuthority(authority);
    }
}
