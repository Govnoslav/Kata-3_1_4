package ru.kata.spring.boot_security.demo.dao.impl;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.dao.RoleDAO;
import ru.kata.spring.boot_security.demo.entity.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RoleDAOImpl implements RoleDAO {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    @Order
    public Role findRoleByAuthority(String authority) {
        return (Role) entityManager.createQuery("FROM Role r where r.authority = :authority").setParameter("authority", authority).getSingleResult();
    }
}
