package com.sparta.homeworktestcode.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.homeworktestcode.post.PostResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {
    private String msg;
    private Integer statusCode;
    private PostResponseDto responseDto;
}
