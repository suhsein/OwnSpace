package com.example.demo.domain.daily;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Daily {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "daily_id")
    private Long id;

    private String writer;
    private String title;
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    private Long views;

    @Builder
    public Daily(String writer, String title, String content, LocalDateTime createDate, Long views) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.views = views;
    }
}
