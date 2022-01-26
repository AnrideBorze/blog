package com.sarakhman.blog.servise;

import com.sarakhman.blog.entity.Post;
import com.sarakhman.blog.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PostServicesTest {

    @Mock
    private PostServices postServices;

    @Mock
    private PostRepository postRepository;


    @Test
    void getOnEmptyDBReturnEmptyList() {
        List<Post> posts = new ArrayList<>();
        Mockito.when(postRepository.findAll()).thenReturn(posts);

        List<Post> actualPosts = postServices.getAllPosts();

        assertEquals(0, actualPosts.size());
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


        Mockito.when(postRepository.findAll()).thenReturn(posts);

        List<Post> actualPosts = postServices.getAllPosts();

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


        Mockito.when(postRepository.save(post)).thenReturn(post);

        Post actualPost = postServices.addNewPost(post);

        assertEquals(post, actualPost);

        assertEquals("name", actualPost.getTitle());
        assertEquals("content", actualPost.getContent());
        assertEquals(1, actualPost.getId());
    }

    // переработать чтобы тест был коректным
    @Test
    void addNewPostWorkCorrectlyOnDBWithPosts() {
        Post post = Post.builder()
                .title("name")
                .content("content")
                .build();
        post.setId(1L);

        Post post2 = Post.builder()
                .title("name2")
                .content("content2")
                .build();
        post.setId(2L);


        Mockito.when(postRepository.save(post)).thenReturn(post);
        Mockito.when(postRepository.save(post2)).thenReturn(post2);


        Post actualPost = postServices.addNewPost(post);

        assertEquals("name", actualPost.getTitle());
        assertEquals("content", actualPost.getContent());
        assertEquals(1, actualPost.getId());

        Post anotherActualPost = postServices.addNewPost(post2);

        assertEquals("name2", anotherActualPost.getTitle());
        assertEquals("content2", anotherActualPost.getContent());
        assertEquals(2, anotherActualPost.getId());


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

        Mockito.when(postRepository.save(post)).thenReturn(post);
        Mockito.when(postRepository.save(newPost)).thenReturn(newPost);

        Post oldPost = postServices.addNewPost(post);

        assertEquals("name", oldPost.getTitle());
        assertEquals("content", oldPost.getContent());
        assertEquals(1, oldPost.getId());

        Post actualPost = postServices.editPost(1, newPost);

        assertEquals("newName", actualPost.getTitle());
        assertEquals("new actual content", actualPost.getContent());
        assertEquals(2, actualPost.getId());
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

        Mockito.when(postRepository.save(post)).thenReturn(post);
        Mockito.when(postRepository.save(newPost)).thenReturn(newPost);

        Post oldPost = postServices.addNewPost(post);

        assertEquals("name", oldPost.getTitle());
        assertEquals("content", oldPost.getContent());
        assertEquals(1, oldPost.getId());


        Post actualPost = postServices.editPost(2, newPost);

        assertEquals("newName", actualPost.getTitle());
        assertEquals("new actual content", actualPost.getContent());
        assertEquals(2, actualPost.getId());
    }

    // перед тестом поставить анотации для запуска теста с пустой базой данных
    @Test
    void deletePostWorkCorrectly() {

        Post post = Post.builder()
                .title("name")
                .content("content")
                .build();
        post.setId(1L);


        postServices.addNewPost(post);
        List<Post> postsBeforeDelete = postServices.getAllPosts();
        postServices.deletePost(1);
        List<Post> postsAfterDelete = postServices.getAllPosts();
        assertEquals(1, postsBeforeDelete.size());
        assertEquals(0, postsAfterDelete.size());
    }

    @Test
    void searchFindPostWhenTitleExist() {
        List<Post> mockedPosts = new ArrayList<>();

        Post post = Post.builder()
                .title("name")
                .content("content")
                .build();
        post.setId(1L);

        mockedPosts.add(post);

        Mockito.when(postRepository.findAll()).thenReturn(mockedPosts);

        List<Post> actualPost = postServices.searchByTitle("name");

        assertEquals("name", actualPost.get(0).getTitle());
        assertEquals("content", actualPost.get(0).getContent());
        assertEquals("1", actualPost.get(0).getId());

    }

    @Test
    void searchReturnEmptyListWhenTitleDoesNotExist() {
        List<Post> mockedPosts = new ArrayList<>();

        Post post = Post.builder()
                .title("name")
                .content("content")
                .build();
        post.setId(1L);

        mockedPosts.add(post);
        Mockito.when(postRepository.findAll()).thenReturn(mockedPosts);


        List<Post> actualPost = postServices.searchByTitle("name2");

        assertTrue(actualPost.isEmpty());
    }

    @Test
    void sortReturnAllPostsInGoodPositions() {

        List<Post> mockedPosts = new ArrayList<>();

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

        mockedPosts.add(post);
        mockedPosts.add(post2);
        mockedPosts.add(post3);
        mockedPosts.add(post4);
        mockedPosts.add(post5);

        Mockito.when(postRepository.findAll()).thenReturn(mockedPosts);

        List<Post> actualPosts = postServices.sortedByTitle();

        assertEquals("E", actualPosts.get(4).getTitle());
        assertEquals("D", actualPosts.get(2).getTitle());
        assertEquals("A", actualPosts.get(0).getTitle());
        assertEquals("C", actualPosts.get(2).getTitle());
        assertEquals("B", actualPosts.get(1).getTitle());


    }

    @Test
    public void addStarForPostAddedStarTest() {
        Post post = Post.builder()
                .title("B")
                .content("content")
                .star(false)
                .build();
        post.setId(1L);

        Mockito.when(postRepository.getById(1L)).thenReturn(post);

        postServices.addStarForPost(1L);

        assertTrue(post.isStar());
    }

    @Test
    public void deleteStarForPostAddedStarTest() {
        Post post = Post.builder()
                .title("B")
                .content("content")
                .star(true)
                .build();
        post.setId(1L);

        Mockito.when(postRepository.getById(1L)).thenReturn(post);

        postServices.deleteStarFromPost(1L);

        assertFalse(post.isStar());
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
        Post post3 = Post.builder()
                .title("name3")
                .content("content3")
                .star(false)
                .build();
        post.setId(3L);
        allPosts.add(post);
        allPosts.add(post2);
        allPosts.add(post3);

        Mockito.when(postRepository.findAll()).thenReturn(allPosts);
        List<Post> posts = postServices.getAllTopPosts();

        assertTrue(posts.isEmpty());
        assertEquals(0,posts.size());
    }

    @Test
    public void getAllTopPostsReturnEmptyListOnEmptyDB() {
        Mockito.when(postRepository.findAll()).thenReturn(new ArrayList<>());
        List<Post> posts = postServices.getAllTopPosts();

        assertTrue(posts.isEmpty());
        assertEquals(0,posts.size());
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
                .star(true)
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


        Mockito.when(postRepository.findAll()).thenReturn(allPosts);

        List<Post> posts = postServices.getAllTopPosts();


        assertEquals("name2",posts.get(0).getTitle());
        assertEquals("content2",posts.get(0).getContent());
        assertEquals(2,posts.get(0).getId());
        assertTrue(posts.get(0).isStar());

        assertEquals("name3",posts.get(1).getTitle());
        assertEquals("content3",posts.get(1).getContent());
        assertEquals(3,posts.get(1).getId());
        assertTrue(posts.get(1).isStar());
    }
}
