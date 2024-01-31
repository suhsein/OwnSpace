package com.example.demo.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AwsS3Service {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // MultipartFile 을 File 로 변환한 후, bucket 내부의 dirName 을 가진 directory 에 저장됨
    public AwsS3 upload(MultipartFile multipartFile, String dirName) throws IOException {
        File file = convertMultipartFileToFile(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("Converting MultipartFile to File Failure"));
        return upload(file, dirName);
    }

    // key => filename, path => s3에 저장된 path
    private AwsS3 upload(File file, String dirName) {
        String key = createFileName(file, dirName);
        String path = putS3(file, key);
        removeFile(file);
        return new AwsS3(key, path);
    }

    // s3 버킷에 이미지 저장 후, getS3로 path 를 return
    private String putS3(File file, String fileName) {
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
        return getS3(fileName);
    }

    // 버킷에 파일이 저장된 경로 반환
    private String getS3(String fileName){
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    private void removeFile(File file){
        file.delete();
    }

    public void remove(AwsS3 awsS3){
        if(!amazonS3.doesObjectExist(bucket, awsS3.getKey())){
            throw new AmazonS3Exception("Object" + awsS3.getKey() + "does not Exist");
        }
        amazonS3.deleteObject(bucket, awsS3.getKey());
    }

    private String createFileName(File file, String dirName){ // 업로드 파일명을 구분하기 위해서 random UUID 를 부여한다.
        return dirName + "/" + UUID.randomUUID() + file.getName();
    }

    public Optional<File> convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(System.getProperty("user.dir") + "/" + multipartFile.getOriginalFilename());
        if(file.createNewFile()){
            try(FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(multipartFile.getBytes());
            }
            return Optional.of(file);
        }
        return Optional.empty();
    }
}