package com.sarakhman.blog.controller;

import com.sarakhman.blog.entity.Post;
import com.sarakhman.blog.servise.PostServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "/api/v1/posts")
public class PostController {

    @Autowired
    private PostServices postServices;

    @GetMapping
    public List<Post> getAllPosts() {
        return postServices.getAllPosts();
    }


    @PostMapping
    public Post addNewPost(@RequestBody Post post) {
        return postServices.addNewPost(post);
    }

    @PutMapping("{id}")
    public Post editPost(@PathVariable("id") long idPost, @RequestBody Post post) {
        return postServices.editPost(idPost, post);
    }

    @DeleteMapping("{id}")
    public void deletePost(@PathVariable("id") long idPost) {
        postServices.deletePost(idPost);
    }

    @GetMapping("?title=:{title}")
    public List<Post> searchByTitle(@PathVariable("title") String title) {
        return postServices.searchByTitle(title);
    }

    @GetMapping("?sort=title")
    public List<Post> sortByTitle() {
        return postServices.sortedByTitle();

    }
}
