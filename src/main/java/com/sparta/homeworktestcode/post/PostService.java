package com.sparta.homeworktestcode.post;

import com.sparta.homeworktestcode.user.User;
import com.sparta.homeworktestcode.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    //게시글 리스트 조회
    public List<PostResponseDto> getPostList() {
        List<Post> postList = postRepository.findAll();
        return postList.stream().map(PostResponseDto::new).toList();
    }

    //게시글 작성
    public PostResponseDto createPost(PostRequestDto postRequestDto, User user) {
        Post post = new Post(postRequestDto, user);
        postRepository.save(post);
        return new PostResponseDto(post);
    }

    //게시글 수정
    @Transactional
    public PostResponseDto modifyPost(Long postId, PostRequestDto modifyPostRequestDto, User user) {
        Post post = postRepository.findById(postId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 포스트 입니다."));
        if(post.getUser().getUserId() != user.getUserId()){
            throw new AuthorizationServiceException("작성자만 수정 권한이 있습니다.");
        }

        String title = modifyPostRequestDto.getTitle();
        String contents = modifyPostRequestDto.getContents();

        post.setTitle(title);
        post.setContents(contents);

        return new PostResponseDto(post);
    }

    public List<PostResponseDto> getUserPostList(String nickname) {
        User targetUser = userRepository.findByNickname(nickname).orElseThrow(()->new IllegalArgumentException("존재하지 않는 유저입니다."));
        List<Post> userPostList = postRepository.findAllByUser(targetUser);
        return userPostList.stream().map(PostResponseDto::new).toList();
    }

    public PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 포스트입니다."));
        return new PostResponseDto(post);
    }

    public void deletePost(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 포스트 입니다."));
        if(post.getUser().getUserId() != user.getUserId()){
            throw new AuthorizationServiceException("작성자만 삭제 권한이 있습니다.");
        }
        postRepository.delete(post);
    }

}
