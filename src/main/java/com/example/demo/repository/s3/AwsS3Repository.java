package com.example.demo.repository.s3;

import com.example.demo.domain.s3.AwsS3;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AwsS3Repository {
    private Map<Long, AwsS3> store = new HashMap<>();
    private Long sequence = 0L;
    // edit 시 사용하기 위한 id 생성

    public AwsS3 store(AwsS3 awsS3) {
        awsS3.setId(++sequence);
        store.put(awsS3.getId(), awsS3);
        return awsS3;
    }

    public AwsS3 findById(Long id){
        return store.get(id);
    }

    public List<AwsS3> findAllById(List<Long> ids) {
        List<AwsS3> result = new ArrayList<>();
        if(ids != null){
            for (Map.Entry<Long, AwsS3> entry : store.entrySet()) {
                if(ids.contains(entry.getKey())){
                    result.add(entry.getValue());
                }
            }
        }
        return result;
    }

    public void delete(Long id) {
        store.remove(id);
    }

    public void clearAll(){
        store.clear();
    }
}
