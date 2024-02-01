package com.example.demo.domain.gallery;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PhotoDto {
    private Long id;
    private List<MultipartFile> imageFiles;
    private String title;
    private String description;
}
