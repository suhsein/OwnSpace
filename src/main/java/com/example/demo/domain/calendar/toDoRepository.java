package com.example.demo.domain.calendar;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class toDoRepository {
    private Map<Long, toDoDto> store = new HashMap<>();
    private static long sequence = 0L;
    public toDoDto save(toDoDto toDo){
        toDo.setId(++sequence);
        store.put(toDo.getId(), toDo);
        return toDo;
    }
    public toDoDto findById(Long id){
        return  store.get(id);
    }
    public List<toDoDto> findAll(){
        return new ArrayList<>(store.values());
    }
    public void clearStore(){
        store.clear();
    }
}
