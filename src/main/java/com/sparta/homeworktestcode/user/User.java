package com.sparta.homeworktestcode.user;

import com.sparta.homeworktestcode.post.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "gender", nullable = false)
    private String gender;
    @Column(name = "introduction")
    private String introduction;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "nickname", unique = true, nullable = false)
    private String nickname;

    @OneToMany(mappedBy = "user")
    private List<Post> postList = new ArrayList<>();

    public User(SignupRequestDto signupRequestDto, String encodedPassword){
        this.username = signupRequestDto.getUsername();
        this.gender = signupRequestDto.getGender();
        this.nickname = signupRequestDto.getNickname();
        this.email = signupRequestDto.getEmail();
        this.password = encodedPassword;
    }


    public void update(String encodedPassword, UserRequestDto requestDto) {
        this.password= encodedPassword;
        this.introduction= requestDto.getIntroduction();
    }
}
