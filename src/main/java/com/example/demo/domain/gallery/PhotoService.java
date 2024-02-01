package com.example.demo.domain.gallery;

import com.example.demo.aws.s3.AwsS3;
import com.example.demo.aws.s3.AwsS3Repository;
import com.example.demo.aws.s3.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoService {
    private final PhotoRepository photoRepository;
    private final AwsS3Repository awsS3Repository;
    private final AwsS3Service awsS3Service;

    /**
     * CREATE
     */
    public List<AwsS3> uploadFiles(PhotoDto form) throws IOException {
        List<MultipartFile> imageFiles = form.getImageFiles();
        List<AwsS3> awsS3List = new ArrayList<>();
        for (MultipartFile imageFile : imageFiles) {
            AwsS3 awsS3 = awsS3Service.upload(imageFile, "upload");
            awsS3List.add(awsS3);
        }
        return awsS3List;
    }

    /**
     * DELETE
     */
    public void removeFiles(Photo photo){
        List<AwsS3> awsS3List = photo.getAwsS3List();
        for (AwsS3 awsS3 : awsS3List) {
            awsS3Service.remove(awsS3);
        }
    }
    public void editPhoto(PhotoDto form,
                          Long id,
                          List<MultipartFile> files,
                          List<Long> deleteFilesId) throws IOException {
        Photo photo = photoRepository.findById(id);
        photo.setTitle(form.getTitle());
        photo.setDescription(form.getDescription());
        List<AwsS3> awsS3List = photo.getAwsS3List();

        if(files != null){
            for (MultipartFile file : files) {
                AwsS3 awsS3 = awsS3Service.upload(file, "upload");
                awsS3List.add(awsS3);
            }
        }

        List<AwsS3> deleted = awsS3Repository.findAllById(deleteFilesId);
        awsS3List.removeAll(deleted);
        photo.setAwsS3List(awsS3List);
    }
}
