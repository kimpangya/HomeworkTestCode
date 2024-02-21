package com.sparta.homeworktestcode.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequestDto signupRequestDto) {
        String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());
        //중복 확인
        Optional<User> checkNickname = userRepository.findByNickname(signupRequestDto.getNickname());
        if(checkNickname.isPresent()){
            throw new IllegalArgumentException("해당 닉네임은 이미 존재합니다.");
        }
        Optional<User> checkEmail = userRepository.findByEmail(signupRequestDto.getEmail());
        if(checkEmail.isPresent()){
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }

        User user = new User(signupRequestDto, encodedPassword);
        userRepository.save(user);
    }

    public void checkLoginEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다");
        }
    }

    public void checkNickname(String nickname) {
        if (userRepository.findByNickname(nickname).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다");
        }
    }

    @Transactional
    public void updateUserProfile(UserRequestDto requestDto, User user) {
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        User u = userRepository.findByEmail(user.getEmail()).orElseThrow();
        u.update(encodedPassword,requestDto);
    }
}
