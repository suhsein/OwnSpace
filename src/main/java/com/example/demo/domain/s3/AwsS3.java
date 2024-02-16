package com.example.demo.domain.s3;

import com.example.demo.domain.gallery.Photo;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "aws_s3")
public class AwsS3 {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aws_s3_id")
    private Long id;
    @Lob
    private String originalName; // edit-photo 에서 보여줄 이름
    @Lob
    private String s3Key;
    @Lob
    private String path;

    @ManyToOne
    @JoinColumn(name = "photo_id")
    private Photo photo;

    @Builder
    public AwsS3(String originalName, String s3Key, String path){
        this.originalName = originalName;
        this.s3Key = s3Key;
        this.path = path;
    }
}
