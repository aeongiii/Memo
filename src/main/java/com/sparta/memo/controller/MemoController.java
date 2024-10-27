package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.*;

// html이 아니라 ajax로만 하면 되기 때문에 @RestController 사용
@RestController
@RequestMapping("/api")
public class MemoController {

    // 아직 데이터베이스를 연결하지 않았으므로, map 자료구조를 활용하여 만들어본다.
    private final Map<Long, Memo> memoList = new HashMap<>();





    // 1. 메모 생성하기
    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {

        // 1) RequestDto -> 데이터베이스와 소통하는 Entity클래스로 수정한다.
        Memo memo = new Memo(requestDto);

        // 2) Memo의 Max Id를 찾는다.        // 현재 가장 큰 id값에 +1한만큼의 새 id를 만든다.
        Long maxId = memoList.size() > 0 ? Collections.max(memoList.keySet()) + 1 : 1;
        memo.setId(maxId);

        // 3) DB 저장
        memoList.put(memo.getId(), memo);

        // 4) Entity -> 바로 반환하지 않고, ResponseDto로 변환하여 반환한다.
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

         // 5) 반환
        return memoResponseDto;

    }




    // 2. 메모 조회하기
    @GetMapping("/memos")
          // 여러개의 MemoResponseDto, 즉 memo들을 List로 반환
    public List<MemoResponseDto> getMemos(Memo memo) {

        // Map을 List로 변경한다.
        List<MemoResponseDto> responseList = memoList.values().stream()
                .map(MemoResponseDto::new).toList();    // memoList 안에 있는 모든 Memo들을 불러온다
                                                        // .stream() : memoList.values()를 하나씩 for문 돌려주고,
                                                        // MemoResponseDto::new를 통해 MemoResponseDto 생성자가 각각 호출된다.

        return responseList;



    }

    // 3.메모 수정하기
    @PutMapping("/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        // 해당 메모가 DB에 존재하는지 확인
        if(memoList.containsKey(id)) {
            // 해당 메모 가져오기
            Memo memo = memoList.get(id);

            // memo 수정
            memo.update(requestDto);
            return memo.getId();
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    // 4. 메모 삭제하기
    @DeleteMapping("/memos/{id}")
    public Long deleteMemo(@PathVariable Long id) {
        // 해당 메모가 DB에 존재하는지 확인
        if(memoList.containsKey(id)) {
            // 해당 메모 삭제하기
            memoList.remove(id);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }


}
