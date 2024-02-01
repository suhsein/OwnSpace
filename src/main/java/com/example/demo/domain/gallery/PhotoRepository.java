package com.example.demo.domain.gallery;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PhotoRepository {
    private final Map<Long, Photo> store = new HashMap<>();
    private long sequence = 0L;

    public Photo save(Photo Photo){
        Photo.setId(++sequence);
        store.put(Photo.getId(), Photo);
        return Photo;
    }

    public Photo findById(Long id){
        return store.get(id);
    }

    public void deleteById(Long id){
        store.remove(id);
    }
    public List<Photo> findAll(){
        return new ArrayList<>(store.values());
    }

}
