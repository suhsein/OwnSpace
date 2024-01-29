package com.example.demo.domain.calendar;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ToDoRepository {
    private Map<Long, ToDoDto> store = new HashMap<>();
    private static long sequence = 0L;
    public ToDoDto save(ToDoDto toDo){
        toDo.setId(++sequence);
        store.put(toDo.getId(), toDo);
        return toDo;
    }
    public ToDoDto findById(Long id){
        return  store.get(id);
    }
    public List<ToDoDto> findByDate(Integer year, Integer month, Integer day){
        return findAll().stream()
                .filter(t -> t.getYear().equals(year) &&
                        t.getMonth().equals(month) &&
                        t.getDay().equals(day) &&
                        !t.getStatus().equals("deleted"))
                .collect(Collectors.toList());
    }
    public List<ToDoDto> findAll(){
        return new ArrayList<>(store.values());
    }
    public void clearStore(){
        store.clear();
    }
}
