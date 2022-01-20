package spring.first.fitness.services;


import spring.first.fitness.entity.Role;

public interface RoleService {

    void addAndSaveRole(Role role);

    Role getRole(Long id);

    void deleteRole(Long id);
}
