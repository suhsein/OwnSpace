package com.example.demo.domain.gallery;

import com.example.demo.domain.s3.AwsS3;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Photo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Long id;

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

    // == 생성 메서드 == //
    @Builder
    public static Photo createPhoto(String title, String content, List<AwsS3> awsS3List){
        Photo photo = new Photo();
        photo.setTitle(title);
        photo.setContent(content);
        for (AwsS3 awsS3 : awsS3List) {
            photo.addAwsS3(awsS3);
        }
        return photo;
    }

}
