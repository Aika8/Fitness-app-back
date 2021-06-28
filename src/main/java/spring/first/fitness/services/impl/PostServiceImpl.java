package spring.first.fitness.services.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.first.fitness.entity.Post;
import spring.first.fitness.repos.PostRepository;
import spring.first.fitness.services.PostService;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public void addAndSavePost(Post post) {
        postRepository.save(post);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByPriority();
    }

    @Override
    public Post getPost(Long id) {
        return postRepository.findById(id).get();
    }

    @Override
    public void deletePost(Long id) {
            postRepository.deleteById(id);
    }
}
