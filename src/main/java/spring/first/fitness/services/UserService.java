package spring.first.fitness.services;


import org.springframework.security.core.userdetails.UserDetailsService;
import spring.first.fitness.entity.Users;

public interface UserService extends UserDetailsService {
    Users getUserByEmail(String email);

    Users createUser(Users user);

    void addAndSaveUser(Users user);

    Users getUser(Long id);

    void deleteUser(Long id);
}
