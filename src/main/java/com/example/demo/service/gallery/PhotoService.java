package com.example.demo.service.gallery;

import com.example.demo.domain.s3.AwsS3;
import com.example.demo.repository.s3.AwsS3Repository;
import com.example.demo.service.s3.AwsS3Service;
import com.example.demo.domain.gallery.Photo;
import com.example.demo.domain.gallery.PhotoDto;
import com.example.demo.repository.gallery.PhotoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PhotoService {
    private final PhotoRepository photoRepository;
    private final AwsS3Repository awsS3Repository;
    private final AwsS3Service awsS3Service;

    /**
     * CREATE
     */
    public List<AwsS3> uploadFiles(List<MultipartFile> imageFiles) throws IOException {
        List<AwsS3> awsS3List = new ArrayList<>();
        for (MultipartFile imageFile : imageFiles) {
            AwsS3 awsS3 = awsS3Service.upload(imageFile, "upload");
            awsS3List.add(awsS3);
        }
        return awsS3List;
    }

    /**
     * UPDATE
     */
    public void editPhoto(PhotoDto form,
                          Long id,
                          List<MultipartFile> imageFiles,
                          List<Long> deleteFilesId) throws IOException {
        Photo photo = photoRepository.findById(id);
        photo.setTitle(form.getTitle());
        photo.setDescription(form.getDescription());
        List<AwsS3> awsS3List = photo.getAwsS3List(); // original awsS3List

        if(!checkNull(imageFiles)){
            for (MultipartFile file : imageFiles) {
                AwsS3 awsS3 = awsS3Service.upload(file, "upload");
                awsS3List.add(awsS3);
            }
        }

        List<AwsS3> deleted = awsS3Repository.findAllById(deleteFilesId); // deleted awsS3List
        awsS3List.removeAll(deleted); // 차집합
        photo.setAwsS3List(awsS3List);
    }


    /**
     * DELETE
     */
    public void removeFiles(Photo photo){
        List<AwsS3> awsS3List = photo.getAwsS3List();
        for (AwsS3 awsS3 : awsS3List) {
            removeFile(awsS3);
        }
    }

    public void removeFile(AwsS3 awsS3){
        awsS3Repository.delete(awsS3.getId());
        awsS3Service.remove(awsS3);
    }

    /**
     * VALIDATION
     */

    public boolean checkNull(List<MultipartFile> imageFiles){
        if(imageFiles.size() == 0){
            return true;
        }
        for (MultipartFile imageFile : imageFiles) {
            if (imageFile.getSize() == 0) {
                return true;
            }
        }
        return false;
    }
}
