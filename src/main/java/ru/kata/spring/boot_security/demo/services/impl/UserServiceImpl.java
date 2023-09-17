package ru.kata.spring.boot_security.demo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.Collection;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDAO userDAOImpl;
    private final RoleService roleServiceImpl;

    private final RoleHierarchy roleHierarchy;

    @Autowired
    public UserServiceImpl(UserDAO userDAOImpl, RoleService roleServiceImpl, RoleHierarchy roleHierarchy) {
        this.roleHierarchy = roleHierarchy;
        this.userDAOImpl = userDAOImpl;
        this.roleServiceImpl = roleServiceImpl;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAOImpl.findUserByUserName(username);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
        );
    }

    @Override
    @Transactional
    public void createUser(User user, String userRole) {
        Role role = roleServiceImpl.findRoleByAuthority(userRole);
        user.setRole(role);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userDAOImpl.createUser(user);
    }

    @Override
    public List<User> findAllUsers() {
        return userDAOImpl.findAllUsers();
    }

    @Override
    public User findUserById(Long id) {
        return userDAOImpl.findUserById(id);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        userDAOImpl.deleteUserById(id);
    }


    @Override
    @Transactional
    public void updateUser(User user, String userRole) {
        Role role = roleServiceImpl.findRoleByAuthority(userRole);
        user.setRole(role);
        if (user.getPassword().isEmpty()) {
            userDAOImpl.updateUserWithoutPassword(user);
        } else {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userDAOImpl.updateUserWithPassword(user);
        }
    }

    @Override
    @Transactional
    public User findUserByUserName(String userName) {

        return userDAOImpl.findUserByUserName(userName);
    }

    @Override
    public String getAllRolesFromCurrentUser(Authentication authentication) {
        StringBuilder userRoles = new StringBuilder();
        Collection<String> roles = AuthorityUtils.authorityListToSet(roleHierarchy.getReachableGrantedAuthorities(authentication.getAuthorities()));
        for (String auto : roles) {
            userRoles.append(auto.replaceAll("ROLE_","")).append(" ");
        }
        return userRoles.toString();
    }
}
