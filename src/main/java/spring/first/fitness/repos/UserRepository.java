package spring.first.fitness.repos;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.first.fitness.entity.Users;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByEmail(String email);
}
