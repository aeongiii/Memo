package com.sparta.memo.entity;

import com.sparta.memo.dto.MemoRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Memo {
    private Long id;  // 메모끼리 구분
    private String username;
    private String contents;


    // 클라이언트로부터 받아온 requestDto의 값들을 Memo의 username, contents에 넣어서 memo 객체를 만드는 생성자.
    public Memo(MemoRequestDto requestDto) {

        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
    }

}