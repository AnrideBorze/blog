package com.sarakhman.blog.controller;

import com.sarakhman.blog.entity.Post;
import com.sarakhman.blog.servise.PostServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostServices postServices;

    @GetMapping("/api/v1/posts")
    public List<Post> getAllPosts() {
        return postServices.getAllPosts();
    }


    @PutMapping("/api/v1/posts")
    public Post addNewPost(@RequestBody Post post) {
        return postServices.addNewPost(post);
    }

    @PostMapping("/api/v1/posts/{id}")
    public Post editPost(@PathVariable("id") long idPost, @RequestBody Post post) {
        return postServices.editPost(idPost, post);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public void deletePost(@PathVariable("id") long idPost) {
        postServices.deletePost(idPost);
    }
}
