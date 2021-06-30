package spring.first.fitness.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.first.fitness.entity.Post;
import spring.first.fitness.services.PostService;

import java.util.List;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping(value = "/api")
public class HomeController {

    @Autowired
    private PostService postService;

    @GetMapping(value = "/posts")
    public List<Post> getAllPosts(){
        return postService.getAllPosts();
    }


    @GetMapping(value = "/post")
    public Post getPost(@RequestParam(name = "id") Long id){
        return postService.getPost(id);
    }


}
