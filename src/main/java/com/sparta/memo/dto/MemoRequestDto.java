package com.sparta.memo.dto;

import lombok.Getter;

// @Getter를 만들고 Memo 엔티티에서 getUsername()과 같이 사용한다.
@Getter
public class MemoRequestDto {
    private String username;
    private String contents;
}