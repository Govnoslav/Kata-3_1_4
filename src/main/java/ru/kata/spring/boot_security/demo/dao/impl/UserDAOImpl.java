package ru.kata.spring.boot_security.demo.dao.impl;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findAllUsers() {

        return entityManager.createQuery("From User").getResultList();
    }

    @Override
    public void createUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public User findUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findUserByUserName(String userName) {
        return entityManager.createQuery("SELECT u FROM User u JOIN FETCH u.role role where u.username = :name ", User.class).setParameter("name", userName).getSingleResult();

    }

    @Override
    public void deleteUserById(Long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public void updateUser(User user) {
        User userToUpdate = entityManager.find(User.class, user.getId());
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setAge(user.getAge());

    }

    @Override
    public void updateUserWithPassword(User user) {
        entityManager.merge(user);
    }

    @Override
    public void updateUserWithoutPassword(User user) {
        User userToUpdate = entityManager.find(User.class, user.getId());
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setAge(user.getAge());
        userToUpdate.setRole(user.getRole());
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setAccountNonExpired(user.isAccountNonExpired());
        userToUpdate.setAccountNonLocked(user.isAccountNonLocked());
        userToUpdate.setCredentialsNonExpired(user.isCredentialsNonExpired());
        userToUpdate.setEnabled(user.isEnabled());

    }
}
