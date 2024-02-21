package com.sparta.homeworktestcode.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.homeworktestcode.common.ResponseDto;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Slf4j(topic = "JWT 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request
            , HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = jwtUtil.resolveToken(request);

        if (Objects.nonNull(token)) {//진짜 검증 과정
            if (jwtUtil.validateToken(token)) {
                Claims info = jwtUtil.getUserInfoFromToken(token);
                //인증정보에 유저정보(loginEmail) 넣기
                String loginEmail = info.getSubject();
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
//                loginEmail -> user조회해서 userDetails에 담음
                UserDetails userDetails = userDetailsService.loadUserByUsername(loginEmail);
//                -> authentication의 principal에 담음
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                -> securityContext에 담음
                securityContext.setAuthentication(authentication);
//                -> SecurityContextHolder 에 setAuthentication
                SecurityContextHolder.setContext(securityContext);
                // 이제 @AuthenticationPrincipal로 조회가능!
            }else{
                //토큰이 잘못되었을때
                ResponseDto<Void> responseDto = new ResponseDto<>("토큰이 유효하지 않습니다.", HttpStatus.BAD_REQUEST.value(),null);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType("application/json; charset=UTF-8");
                response.getWriter().write(objectMapper.writeValueAsString(responseDto));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}