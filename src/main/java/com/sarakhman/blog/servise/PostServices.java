package com.sarakhman.blog.servise;


import com.sarakhman.blog.entity.Post;
import com.sarakhman.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Post editPost(long postId,Post post) {
        Post postForWork = getById(postId);

        if(Objects.nonNull(post.getTitle())&&!post.getTitle().equals("")){
            postForWork.setTitle(post.getTitle());
        }
        if(Objects.nonNull(post.getContent())&&!post.getContent().equals("")){
            postForWork.setContent(post.getContent());
        }
        return postRepository.save(postForWork);
    }

    public Post getById(long id){
        return postRepository.getById(id);
    }

    public void deletePost(long idPost) {
        postRepository.deleteById(idPost);
    }
}
