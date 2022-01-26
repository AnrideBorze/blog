package com.sarakhman.blog.controller;

import com.sarakhman.blog.entity.Post;
import com.sarakhman.blog.servise.PostServices;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
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
    void getOnEmptyDBReturnEmptyList() {
        List<Post> posts = new ArrayList<>();
        Mockito.when(postServices.getAllPosts()).thenReturn(posts);

        List<Post> actualPosts = postController.getAllPosts();

        assertEquals(posts, actualPosts);
        assertTrue(actualPosts.isEmpty());

    }


    @Test
    void getOnNotEmptyDBReturnListWithPosts() {
        List<Post> posts = new ArrayList<>();
        Post post = Post.builder()
                .title("name")
                .content("content")
                .build();
        post.setId(1L);

        posts.add(post);


        Mockito.when(postServices.getAllPosts()).thenReturn(posts);

        List<Post> actualPosts = postController.getAllPosts();

        assertEquals(1, actualPosts.get(0).getId());
        assertEquals("name", actualPosts.get(0).getTitle());
        assertEquals("content", actualPosts.get(0).getContent());

        assertFalse(actualPosts.isEmpty());
    }

    @Test
    void addNewPostWorkCorrectlyOnEmptyDB() {
        Post post = Post.builder()
                .title("name")
                .content("content")
                .build();
        post.setId(1L);


        Mockito.when(postServices.addNewPost(post)).thenReturn(post);

        Post actualPost = postController.addNewPost(post);

        assertEquals(post, actualPost);


    }

    @Test
    void addNewPostWorkCorrectlyOnDBWithPosts() {
        Post post = Post.builder()
                .title("name")
                .content("content")
                .build();
        post.setId(1L);


        Mockito.when(postServices.addNewPost(post)).thenReturn(post);

        Post actualPost = postController.addNewPost(post);

        assertEquals(post, actualPost);


    }


    @Test
    void editPostWorkCorrectlyWhenPostExist() {
        Post post = Post.builder()
                .title("name")
                .content("content")
                .build();
        post.setId(1L);

        Post newPost = Post.builder()
                .title("newName")
                .content("new actual content")
                .build();
        newPost.setId(1L);


        Mockito.when(postServices.addNewPost(post)).thenReturn(post);
        Mockito.when(postServices.editPost(1, newPost)).thenReturn(newPost);

        Post oldPost = postController.addNewPost(post);

        assertEquals(post, oldPost);

        Post actualPost = postController.editPost(1, newPost);

        assertEquals(newPost, actualPost);
    }

    @Test
    void editPostWorkCorrectlyWhenPostDoesNotExist() {

        Post post = Post.builder()
                .title("name")
                .content("content")
                .build();
        post.setId(1L);

        Post newPost = Post.builder()
                .title("newName")
                .content("new actual content")
                .build();
        newPost.setId(2L);

        Mockito.when(postServices.addNewPost(post)).thenReturn(post);
        Mockito.when(postServices.editPost(2, newPost)).thenReturn(newPost);

        Post oldPost = postController.addNewPost(post);

        assertEquals(post, oldPost);

        Post actualPost = postController.editPost(2, newPost);

        assertEquals(newPost, actualPost);
    }

    @Test
    void deletePostWorkCorrectly() {

        Post post = Post.builder()
                .title("name")
                .content("content")
                .build();
        post.setId(1L);


        postController.addNewPost(post);
        List<Post> postsBeforeDelete = postController.getAllPosts();
        postController.deletePost(1);
        List<Post> postsAfterDelete = postController.getAllPosts();
        assertEquals(1, postsBeforeDelete.size());
        assertEquals(0, postsAfterDelete.size());
    }

    @Test
    void searchFindPostWhenTitleExist() {
        Post post = Post.builder()
                .title("name")
                .content("content")
                .build();
        post.setId(1L);
        postController.addNewPost(post);

        List<Post> actualPost = postController.searchByTitle("name");

        assertEquals(post, actualPost.get(0));
    }

    @Test
    void searchReturnEmptyListWhenTitleDoesNotExist() {
        Post post = Post.builder()
                .title("name")
                .content("content")
                .build();
        post.setId(1L);

        postController.addNewPost(post);

        List<Post> actualPost = postController.searchByTitle("name2");

        assertTrue(actualPost.isEmpty());
    }

    @Test
    void sortReturnAllPostsInGoodPositions() {
        Post post = Post.builder()
                .title("E")
                .content("content")
                .build();
        post.setId(1L);
        Post post2 = Post.builder()
                .title("D")
                .content("content")
                .build();
        post2.setId(2L);
        Post post3 = Post.builder()
                .title("A")
                .content("content")
                .build();
        post3.setId(3L);
        Post post4 = Post.builder()
                .title("C")
                .content("content")
                .build();
        post4.setId(4L);
        Post post5 = Post.builder()
                .title("B")
                .content("content")
                .build();
        post5.setId(5L);


        postController.addNewPost(post);
        postController.addNewPost(post2);
        postController.addNewPost(post3);
        postController.addNewPost(post4);
        postController.addNewPost(post5);

        List<Post> actualPosts = postController.sortByTitle();

        assertEquals("E", actualPosts.get(4).getTitle());
        assertEquals("D", actualPosts.get(2).getTitle());
        assertEquals("A", actualPosts.get(0).getTitle());
        assertEquals("C", actualPosts.get(2).getTitle());
        assertEquals("B", actualPosts.get(1).getTitle());


    }

    @Test
    public void addStarForPostAddedStarTest() {

        Mockito.when(postServices.addStarForPost(1L)).thenReturn(true);

        boolean test = postController.addStarForPost(1L);

        assertEquals(true, test);

    }

    @Test
    public void deleteStarForPostAddedStarTest() {

        Mockito.when(postServices.deleteStarFromPost(1L)).thenReturn(false);

        boolean test = postController.deleteStarFromPost(1L);

        assertEquals(false, test);

    }

    @Test
    public void getAllTopPostsReturnEmptyListWhenAllPostsAreWithoutStars() {
        List<Post> allPosts = new ArrayList<>();

        Post post = Post.builder()
                .title("name")
                .content("content")
                .star(false)
                .build();
        post.setId(1L);
        Post post2 = Post.builder()
                .title("name2")
                .content("content2")
                .star(false)
                .build();
        post.setId(2L);
        allPosts.add(post);
        allPosts.add(post2);


        Mockito.when(postServices.getAllPosts()).thenReturn(allPosts);
        Mockito.when(postServices.getAllTopPosts()).thenReturn(allPosts);

        List<Post> actualPosts = postController.getAllTopPosts();
        assertTrue(actualPosts.isEmpty());
        assertEquals(0, actualPosts.size());
    }

    @Test
    public void getAllTopPostsReturnEmptyListOnEmptyDB() {
        List<Post> allPosts = new ArrayList<>();
        Mockito.when(postServices.getAllTopPosts()).thenReturn(allPosts);
        List<Post> posts = postController.getAllTopPosts();

        assertTrue(posts.isEmpty());
        assertEquals(0, posts.size());
    }

    @Test
    public void getAllTopPostsReturnOnlyPostsWithStar() {
        List<Post> allPosts = new ArrayList<>();

        Post post = Post.builder()
                .title("name")
                .content("content")
                .star(false)
                .build();
        post.setId(1L);
        Post post2 = Post.builder()
                .title("name2")
                .content("content2")
                .star(false)
                .build();
        post.setId(2L);
        Post post3 = Post.builder()
                .title("name3")
                .content("content3")
                .star(true)
                .build();
        post.setId(3L);
        allPosts.add(post);
        allPosts.add(post2);
        allPosts.add(post3);


        Mockito.when(postServices.getAllPosts()).thenReturn(allPosts);
        Mockito.when(postServices.getAllTopPosts()).thenReturn(allPosts);

        List<Post> actualPosts = postController.getAllTopPosts();

        assertFalse(actualPosts.isEmpty());
        assertEquals(1, actualPosts.size());
        assertEquals("name3", actualPosts.get(0).getTitle());
        assertEquals("content3", actualPosts.get(0).getContent());
        assertEquals(3L, actualPosts.get(0).getId());
        assertTrue(actualPosts.get(0).isStar());
    }
}
