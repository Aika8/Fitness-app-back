package spring.first.fitness.repos;


import org.springframework.data.jpa.repository.JpaRepository;
import spring.first.fitness.entity.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByPriority();
}
