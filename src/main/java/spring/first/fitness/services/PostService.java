package spring.first.fitness.services;



import spring.first.fitness.entity.Post;

import java.util.List;

public interface PostService {

    void addAndSavePost(Post role);
    List<Post> getAllPosts();
    Post getPost(Long id);
    void deletePost(Long id);
}
