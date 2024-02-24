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

@Data
@Entity
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "daily_id")
    private Daily daily;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id") // 주의 - 자기 자신을 참조하게 되는 경우 join column 명은 id의 column 명과 달라야 함
    private Comment parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> childList = new ArrayList<>();

    @Lob
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;

    @Enumerated(EnumType.STRING)
    private CommentStatus status;

    public void addChildComment(Comment child){
        childList.add(child);
        child.setParent(this);
    }

    @Builder
    public Comment(Member writer, LocalDateTime createDate, Daily daily, String content, CommentStatus status) {
        this.writer = writer;
        this.createDate = createDate;
        this.daily = daily;
        this.content = content;
        this.status = status;
    }
}
