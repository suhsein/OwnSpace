package com.example.demo.service.gallery;

import com.example.demo.domain.s3.AwsS3;
import com.example.demo.service.s3.AwsS3Service;
import com.example.demo.domain.gallery.Photo;
import com.example.demo.repository.gallery.PhotoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhotoService {
    private final PhotoRepository photoRepository;
    private final AwsS3Service awsS3Service;

    /**
     * CREATE
     */
    @Transactional
    public void save(Photo photo){
        photoRepository.save(photo);
    }

    @Transactional
    public List<AwsS3> uploadFiles(List<MultipartFile> imageFiles) throws IOException {
        List<AwsS3> awsS3List = new ArrayList<>();
        for (MultipartFile imageFile : imageFiles) {
            AwsS3 awsS3 = awsS3Service.upload(imageFile, "upload");
            awsS3List.add(awsS3);
        }
        return awsS3List;
    }

    /**
     * READ
     */
    public Photo findOne(Long photoId){
        return photoRepository.findOne(photoId);
    }

    public List<Photo> findAll(){
        return photoRepository.findAll();
    }

    /**
     * UPDATE
     */
    @Transactional
    public void editPhoto(PhotoDto form,
                          Long id,
                          List<MultipartFile> imageFiles,
                          List<Long> deleteFilesId) throws IOException {
        Photo photo = photoRepository.findOne(id);
        photo.setTitle(form.getTitle());
        photo.setDescription(form.getDescription());
        List<AwsS3> awsS3List = photo.getAwsS3List(); // original awsS3List

        if(!checkNull(imageFiles)){
            for (MultipartFile file : imageFiles) {
                AwsS3 awsS3 = awsS3Service.upload(file, "upload");
                awsS3List.add(awsS3);
            }
        }

        awsS3Service.removeAll(deleteFilesId);
    }


    /**
     * DELETE
     */
    @Transactional
    public void remove(Long photoId){
        photoRepository.remove(photoId);
    }
    @Transactional
    public void removeFiles(Photo photo){
        List<AwsS3> awsS3List = photo.getAwsS3List();
        for (AwsS3 awsS3 : awsS3List) {
            removeFile(awsS3);
        }
    }

    @Transactional
    public void removeFile(AwsS3 awsS3){
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
