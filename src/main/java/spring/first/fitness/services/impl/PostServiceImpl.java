package spring.first.fitness.services.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import spring.first.fitness.dto.PostDTO;
import spring.first.fitness.entity.ElasticPost;
import spring.first.fitness.entity.Post;
import spring.first.fitness.helpers.ElasticConstants;
import spring.first.fitness.helpers.ElasticHelper;
import spring.first.fitness.repos.ElasticPostRepository;
import spring.first.fitness.repos.PostRepository;
import spring.first.fitness.services.PostService;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ElasticPostRepository elasticPostRepository;

    private final Lock saveLock = new ReentrantLock(true);
    private final Lock deleteLock = new ReentrantLock(true);

    @Override
    public PostDTO savePost(PostDTO post) {
        saveLock.lock();
        try {
            log.info("Post: {}", post);
            Post post1 = Post.builder()
                    .id(post.getId())
                    .access(post.getAccess())
                    .cover(post.getCover())
                    .dateOfCreation(post.getDateOfCreation() != null ? post.getDateOfCreation() : LocalDateTime.now())
                    .users(post.getUsers())
                    .build();
            post1 = postRepository.save(post1);

            ElasticPost elPost = ElasticPost.builder()
                    .postId(post1.getId())
                    .brief(post.getBrief())
                    .title(post.getTitle())
                    .description(post.getDescription())
                    .build();

            elasticPostRepository.save(elPost);

            elasticPostRepository.refresh();
            post.setId(post1.getId());
            return post;
        } finally {
            saveLock.unlock();
        }

    }

    @Override
    public Page<PostDTO> getAllPosts(Map<String, Object> filter, Pageable pageable) {
        log.info("Filter: {}", filter);

        NativeSearchQueryBuilder nativeSearchQueryBuilder = ElasticHelper.nativeQueryBuilder(filter, pageable, getIndex());

        log.info("Query: {}", nativeSearchQueryBuilder);

        Page<ElasticPost> postModels = elasticPostRepository.search(nativeSearchQueryBuilder.build());
        Page<PostDTO> dto = postModels.map(entity -> {
            Post post1 = postRepository.findById(entity.getPostId())
                    .orElseThrow(() -> new NoSuchElementException("ошибка сервера, нет поста с таким id"));
            return PostDTO.builder()
                    .id(post1.getId())
                    .access(post1.getAccess())
                    .cover(post1.getCover())
                    .dateOfCreation(post1.getDateOfCreation())
                    .users(post1.getUsers())
                    .brief(entity.getBrief())
                    .title(entity.getTitle())
                    .description(entity.getDescription())
                    .build();
        });

        log.info("data: " + dto);

        return dto;
    }


    String getIndex() {
        return ElasticConstants.POST_INDEX;
    }


    @Override
    public PostDTO getPost(Long id) {
        AtomicReference<PostDTO> dto = new AtomicReference<>();
        postRepository.findById(id).ifPresent(post1 -> {
            ElasticPost elPost = elasticPostRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("ошибка сервера, нет поста с таким id"));
            dto.set(PostDTO.builder()
                    .id(post1.getId())
                    .access(post1.getAccess())
                    .cover(post1.getCover())
                    .dateOfCreation(post1.getDateOfCreation())
                    .users(post1.getUsers())
                    .brief(elPost.getBrief())
                    .title(elPost.getTitle())
                    .description(elPost.getDescription())
                    .build());

        });

        log.info("data: " + dto.get());
        return dto.get();
    }

    @Override
    public void deletePost(Long id) {
        deleteLock.lock();
        try {
            postRepository.deleteById(id);
            elasticPostRepository.deleteById(id);
        } finally {
            deleteLock.unlock();
        }
    }
}
