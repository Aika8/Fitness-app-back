package spring.first.fitness.services;



import spring.first.fitness.entity.Role;

import java.util.List;

public interface RoleService {

    void addAndSaveRole(Role role);
    List<Role> getAllRoles();
    Role getRole(Long id);
    void deleteRole(Long id);
}
