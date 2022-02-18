package spring.first.fitness;

import com.fasterxml.jackson.databind.ObjectMapper;


import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import spring.first.fitness.entity.ElasticPost;
import spring.first.fitness.entity.Post;
import spring.first.fitness.repos.ElasticPostRepository;
import spring.first.fitness.repos.PostRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest()
public class SpringBootInitialLoadIntegrationTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ElasticPostRepository elasticPostRepository;

    @Test
    @Sql(scripts = {"file:src/test/resources/post.sql"},
            config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void testLoadDataForTestClass() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream stream = SpringBootInitialLoadIntegrationTest.class.getResourceAsStream("/post.json");
        String jsonText = IOUtils.toString(
                stream,
                String.valueOf(StandardCharsets.UTF_8));
        List<ElasticPost> posts = objectMapper.readValue(jsonText, TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, ElasticPost.class));
        if (!posts.isEmpty()) elasticPostRepository.saveAll(posts);
        assertEquals(3, postRepository.findAll().size());
    }
}
