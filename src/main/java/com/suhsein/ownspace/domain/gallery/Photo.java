package com.suhsein.ownspace.domain.gallery;

import com.suhsein.ownspace.domain.members.Member;
import com.suhsein.ownspace.domain.s3.AwsS3;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Data
@Entity
@NoArgsConstructor
public class Photo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    @OneToMany(mappedBy = "photo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AwsS3> awsS3List = new ArrayList<>();
    private String title;
    @Lob
    private String content;

    // == 연관관계 편의 메서드 == //
    public void addAwsS3(AwsS3 awsS3){
        awsS3List.add(awsS3);
        awsS3.setPhoto(this);
    }

    @Builder
    public Photo(String title, String content, List<AwsS3> awsS3List, Member writer){
        this.title = title;
        this.content = content;
        for (AwsS3 awsS3 : awsS3List) {
            this.addAwsS3(awsS3);
        }
        this.writer = writer;
    }
}
