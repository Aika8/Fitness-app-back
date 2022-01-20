package spring.first.fitness.repos;



import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import spring.first.fitness.entity.ElasticPost;

@Repository
public interface ElasticPostRepository extends ElasticsearchRepository<ElasticPost, Long> {
}
