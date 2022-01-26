package com.sarakhman.blog.servise;


import com.sarakhman.blog.entity.Post;
import com.sarakhman.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class PostServices implements PostServicesInterface {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post addNewPost(Post post) {
        return postRepository.save(post);
    }

    public Post editPost(long postId, Post post) {
        Post postForWork = getById(postId);

        if (Objects.nonNull(post.getTitle()) && !post.getTitle().equals("")) {
            postForWork.setTitle(post.getTitle());
        }
        if (Objects.nonNull(post.getContent()) && !post.getContent().equals("")) {
            postForWork.setContent(post.getContent());
        }
        return postRepository.save(postForWork);
    }

    public Post getById(long id) {
        return postRepository.getById(id);
    }

    public void deletePost(long idPost) {
        postRepository.deleteById(idPost);
    }

    public List<Post> sortedByTitle() {
        List<Post> posts = postRepository.findAll();
        List<String> postsTitle = new ArrayList<>();
        int number = 0;
        for (Post post : posts) {
            postsTitle.add(number, post.getTitle());
            number++;
        }
        Collections.sort(postsTitle);
        List<Post> postsCopyForWork = new ArrayList<>();
        Collections.copy(postsCopyForWork, posts);
        List<Post> sortedList = new ArrayList<>();
        int size = 0;
        for (Post post : postsCopyForWork) {
            if (Objects.equals(postsTitle.get(size), post.getTitle())) {
                sortedList.add(post);
                postsCopyForWork.remove(post);
                size++;
            }
        }

        return sortedList;
    }

    public List<Post> searchByTitle(@PathVariable(":title") String title) {
        List<Post> posts = postRepository.findAll();
        List<Post> postWithTitle = new ArrayList<>();
        for (Post post : posts) {
            if (Objects.equals(post.getTitle(), title)) {
                postWithTitle.add(post);
            }
        }
        return postWithTitle;
    }

    public List<Post> getAllTopPosts() {
        List<Post> allPosts = getAllPosts();
        List<Post> postsWithStar = new ArrayList<>();
        for (Post currentPost : allPosts) {
            if (currentPost.isStar()) {
                postsWithStar.add(currentPost);
            }
        }
        return postsWithStar;
    }

    public boolean addStarForPost(Long id) {
        Post post = postRepository.getById(id);
        if (post.isStar()) {
            return true;
        }
        post.setStar(true);
        editPost(id, post);
        return true;
    }

    public boolean deleteStarFromPost(Long id) {
        Post post = postRepository.getById(id);
        if (!post.isStar()) {
            return false;
        }
        post.setStar(false);
        editPost(id, post);
        return false;
    }
}
