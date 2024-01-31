package com.example.demo.domain.gallery;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PhotoDto {
    private Long id;
    private MultipartFile imageFile;
    private String title;
    private String description;
}
