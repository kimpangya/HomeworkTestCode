package com.sparta.homeworktestcode.service;

import com.sparta.homeworktestcode.common.ResponseDto;
import com.sparta.homeworktestcode.post.PostRequestDto;
import com.sparta.homeworktestcode.post.PostResponseDto;
import com.sparta.homeworktestcode.post.PostService;
import com.sparta.homeworktestcode.user.User;
import com.sparta.homeworktestcode.user.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostServiceTest {
    @Autowired
    PostService postService;
    @Autowired
    UserRepository userRepository;

    User user;
    PostResponseDto createdPost = null;

    @Test
    @Order(1)
    @DisplayName("게시글 작성")
    void test1(){
        //given
        String title = "1일 1먹 신제품";
        String contents = "초코크림빵";
        PostRequestDto requestDto = new PostRequestDto(
                title,
                contents
        );
        user=userRepository.findById(1L).orElse(null);

        //when
        PostResponseDto post = postService.createPost(requestDto, user);

        //then
        assertNotNull(post.getPostId());
        assertEquals(title, post.getTitle());
        assertEquals(contents, post.getContents());
        createdPost=post;
    }

}
