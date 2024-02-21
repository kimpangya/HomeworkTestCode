package com.sparta.homeworktestcode.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    private String title;
    private String contents;
}
