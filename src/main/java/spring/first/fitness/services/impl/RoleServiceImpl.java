package spring.first.fitness.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.first.fitness.entity.Role;
import spring.first.fitness.repos.RoleRepository;
import spring.first.fitness.services.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void addAndSaveRole(Role role) {
        roleRepository.save(role);
    }


    @Override
    public Role getRole(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}
