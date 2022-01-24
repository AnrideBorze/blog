package com.sarakhman.blog;

import com.sarakhman.blog.controller.PostController;
import com.sarakhman.blog.entity.Post;
import com.sarakhman.blog.servise.PostServices;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PostControllerTest {

    @Mock
    private PostController postController;

    @Mock
    private PostServices postServices;

    @Test
        //добавить конфигурацию с пустой бд
    void getOnEmptyDBReturnEmptyList() {
        List<Post> posts = new ArrayList<>();
        Mockito.when(postServices.getAllPosts()).thenReturn(posts);

        List<Post> actualPosts = postController.getAllPosts();

        assertEquals(posts, actualPosts);
        assertTrue(actualPosts.isEmpty());

    }


    @Test
        //добавить конфигурацию с не пустой бд
    void getOnNotEmptyDBReturnListWithPosts() {
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(1, "name", "content"));
        Mockito.when(postServices.getAllPosts()).thenReturn(posts);

        List<Post> actualPosts = postController.getAllPosts();

        assertEquals(posts, actualPosts);
        assertFalse(actualPosts.isEmpty());
    }

    @Test
        //добавить конфигурацию с пустой бд
    void addNewPostWorkCorrectlyOnEmptyDB() {
        Post post = new Post(1, "name", "content");

        Mockito.when(postServices.addNewPost(post)).thenReturn(post);

        Post actualPost = postController.addNewPost(post);

        assertEquals(post, actualPost);


    }

    @Test
        // добавить конфигурацию с не пустой базой данных
    void addNewPostWorkCorrectlyOnDBWithPosts() {
        Post post = new Post(1, "name", "content");

        Mockito.when(postServices.addNewPost(post)).thenReturn(post);

        Post actualPost = postController.addNewPost(post);

        assertEquals(post, actualPost);


    }


    @Test
        //добавить конфигурацию с пустой бд
    void editPostWorkCorrectlyWhenPostExist() {
        Post post = new Post(1, "name", "content");
        Post newPost = new Post(1, "newName", "new actual content");

        Mockito.when(postServices.addNewPost(post)).thenReturn(post);
        Mockito.when(postServices.editPost(1, newPost)).thenReturn(newPost);

        Post oldPost = postController.addNewPost(post);

        assertEquals(post, oldPost);

        Post actualPost = postController.editPost(1, newPost);

        assertEquals(newPost, actualPost);
    }

    @Test
        //добавить конфигурацию с пустой бд
    void editPostWorkCorrectlyWhenPostDoesNotExist() {
        Post post = new Post(1, "name", "content");
        Post newPost = new Post(2, "newName", "new actual content");

        Mockito.when(postServices.addNewPost(post)).thenReturn(post);
        Mockito.when(postServices.editPost(2, newPost)).thenReturn(newPost);

        Post oldPost = postController.addNewPost(post);

        assertEquals(post, oldPost);

        Post actualPost = postController.editPost(2, newPost);

        assertEquals(newPost, actualPost);
    }

    @Test
        //добавить конфигурацию с пустой бд
    void deletePostWorkCorrectly() {
        Post post = new Post(1, "name", "content");


        postController.addNewPost(post);
        List<Post> postsBeforeDelete = postController.getAllPosts();
        postController.deletePost(1);
        List<Post> postsAfterDelete = postController.getAllPosts();
        assertEquals(1, postsBeforeDelete.size());
        assertEquals(0, postsAfterDelete.size());
    }

    @Test   //добавить конфигурацию с пустой бд
    void searchFindPostWhenTitleExist() {
        Post post = new Post(1, "name", "content");
        postController.addNewPost(post);

        List<Post> actualPost = postController.searchByTitle("name");

        assertEquals(post, actualPost.get(0));
    }

    @Test   //добавить конфигурацию с пустой бд
    void searchReturnNullWhenTitleDoesNotExist() {
        Post post = new Post(1, "name", "content");
        postController.addNewPost(post);

        List<Post> actualPost = postController.searchByTitle("name2");

        assertNull(actualPost);
    }

    @Test   //добавить конфигурацию с пустой бд
    void sortReturnAllPostsInGoodPositions() {
        Post post = new Post(1, "E", "content");
        Post post2 = new Post(1, "D", "content");
        Post post3 = new Post(1, "A", "content");
        Post post4 = new Post(1, "C", "content");
        Post post5 = new Post(1, "B", "content");

        postController.addNewPost(post);
        postController.addNewPost(post2);
        postController.addNewPost(post3);
        postController.addNewPost(post4);
        postController.addNewPost(post5);

        List<Post> actualPosts = postController.sortByTitle();

        assertEquals(post,actualPosts.get(4));
        assertEquals(post2,actualPosts.get(2));
        assertEquals(post3,actualPosts.get(0));
        assertEquals(post4,actualPosts.get(2));
        assertEquals(post5,actualPosts.get(1));


    }

}
