package spring.first.fitness.services;


import org.springframework.security.core.userdetails.UserDetailsService;
import spring.first.fitness.entity.Users;

import java.util.List;

public interface UserService extends UserDetailsService {
    Users getUserByEmail(String email);
    Users createUser(Users user);

    void addAndSaveUser(Users user);
    List<Users> getAllUsers();
    Users getUser(Long id);
    void deleteUser(Long id);
}
