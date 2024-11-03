package com.sparta.memo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// JPA Auditing 기능을 사용하려면, 메인메서드가 있는 부분에 @EnableJpaAuditing 달아줘야 한다.

@Getter
@MappedSuperclass // 추상 클래스에 선언한 멤버 변수를 Column으로 인식(Entity클래스에서의 상속)
@EntityListeners(AuditingEntityListener.class) // AuditingEntityListener = Timestamped클래스에 Auditing 기능 포함시킴
public abstract class Timestamped {
       // 다른 Entity 클래스에 상속하기 위해 만들어진 클래스이므로 abstract

    @CreatedDate // Entity 객체 생성, 저장될때 만들어진 시간도 저장
    @Column(updatable = false)  // @CreateDate를 통해 시간 저장될때 "최초시간"만 저장됨. 매번 시간 업데이트 되는 것 방지
    @Temporal(TemporalType.TIMESTAMP)  // @Temporal : 자바의 Date, Time, Calender와 같은 날짜 데이터 매핑 시 사용
    private LocalDateTime createdAt;

    @LastModifiedDate // 조회한 Entity 값을 변경할 때, 변경된 시간을 자동으로 저장
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt;
}