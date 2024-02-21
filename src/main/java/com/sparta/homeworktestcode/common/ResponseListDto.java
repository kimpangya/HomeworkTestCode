package com.sparta.homeworktestcode.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.homeworktestcode.post.PostResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseListDto {
    private String msg;
    private Integer statusCode;
    private List<PostResponseDto> responseDto;
}
