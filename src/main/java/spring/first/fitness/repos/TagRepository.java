package spring.first.fitness.repos;


import org.springframework.data.jpa.repository.JpaRepository;
import spring.first.fitness.entity.Tags;

public interface TagRepository extends JpaRepository<Tags, Long> {
}
