package spring.first.fitness.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.first.fitness.dto.PostDTO;
import spring.first.fitness.services.PostService;

import java.util.Map;

@Slf4j
@RestController
@CrossOrigin
@Api(value = "Api for posts")
@RequestMapping(value = "/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/{id}")
    @ApiOperation(value = "Get post by ID")
    public ResponseEntity<PostDTO> getPost(@PathVariable final Long id) {
        return ResponseEntity.ok(postService.getPost(id));
    }

    @PostMapping(value = "/filter")
    @ApiOperation(value = "Get posts")
    public ResponseEntity<Page<PostDTO>> getAll(
            @RequestBody(required = false) Map<String, Object> filter,
            @PageableDefault Pageable pageable) {
        log.info("Received filter: /api/post/filter ={}", filter);
        return ResponseEntity.ok(postService.getAllPosts(filter, pageable));
    }

    @PostMapping
    @ApiOperation(value = "Save post or if there id update post")
    public ResponseEntity<PostDTO> save(@RequestBody PostDTO post) {
        return ResponseEntity.ok(postService.savePost(post));
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete post by id")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
