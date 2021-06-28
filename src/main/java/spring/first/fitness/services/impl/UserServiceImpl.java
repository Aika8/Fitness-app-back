package spring.first.fitness.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import spring.first.fitness.entity.Role;
import spring.first.fitness.entity.Users;
import spring.first.fitness.repos.RoleRepository;
import spring.first.fitness.repos.UserRepository;
import spring.first.fitness.services.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;


    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Users myUser = userRepository.findByEmail(s);
        if(myUser != null){

            User secUser = new User(myUser.getEmail(), myUser.getPassword(), myUser.getRoles());
            return secUser;
        }

        throw new UsernameNotFoundException("User Not Found");
    }

    @Override
    public Users getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public Users createUser(Users user) {

        Users checkUser = userRepository.findByEmail(user.getEmail());

        if(checkUser == null){
            Role role =  roleRepository.findByRole("ROLE_USER");

            if(role != null){
                ArrayList<Role> roles = new ArrayList<>();
                roles.add(role);
                user.setRoles(roles);
//                user.setPassword(passwordEncoder.encode(user.getPassword()));
                return userRepository.save(user);
            }
        }

        return null;

    }

    @Override
    public void addAndSaveUser(Users user) {
        userRepository.save(user);
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Users getUser(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

