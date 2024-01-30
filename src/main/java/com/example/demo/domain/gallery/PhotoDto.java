package com.example.demo.domain.gallery;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PhotoDto {
    private Long id;
    @NotNull
    private MultipartFile imageFile;
    private String description;
}
