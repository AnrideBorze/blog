package com.sarakhman.blog.servise;

import com.sarakhman.blog.entity.Post;

import java.util.List;

public interface PostServicesInterface {
    public List<Post> getAllPosts();

    public Post addNewPost(Post post);
    public Post editPost(long postId,Post post);
    public void deletePost(long idPost);

    }

