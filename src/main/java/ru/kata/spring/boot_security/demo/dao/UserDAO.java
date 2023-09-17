package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserDAO {

    void createUser(User user);

    List<User> findAllUsers();


    User findUserById(Long id);

    User findUserByUserName(String userName);

    void deleteUserById(Long id);

    void updateUser(User user);

    void updateUserWithPassword(User user);

    void updateUserWithoutPassword(User user);
}
