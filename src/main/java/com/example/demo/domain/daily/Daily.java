package com.example.demo.domain.daily;

import com.example.demo.domain.members.Member;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Data
@NoArgsConstructor
public class Daily {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "daily_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    @OneToMany(mappedBy = "daily", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"daily"})
    private List<Comment> commentList = new ArrayList<>();

    private String title;

    @Lob
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;

    private Long views;

    public void addComment(Comment comment) {
        commentList.add(comment);
        comment.setDaily(this);
    }

    @Builder
    public Daily(Member writer, String title, String content, LocalDateTime createDate, Long views) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.views = views;
    }
}
