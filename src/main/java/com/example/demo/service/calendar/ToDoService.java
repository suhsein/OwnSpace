package com.example.demo.service.calendar;

import com.example.demo.domain.calendar.MyDate;
import com.example.demo.domain.calendar.ToDo;
import com.example.demo.repository.calendar.ToDoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ToDoService {
    private final ToDoRepository toDoRepository;

    @Transactional
    public void save(ToDo toDo) {
        toDoRepository.save(toDo);
    }

    public ToDo findOne(Long id) {
        return toDoRepository.findOne(id);
    }

    public List<ToDo> findAll() {
        return toDoRepository.findAll();
    }

    public List<ToDo> findByDate(MyDate myDate) {
        return toDoRepository.findByDate(myDate);
    }

    @Transactional
    public void remove(Long toDoId){
        toDoRepository.remove(toDoId);
    }
}
