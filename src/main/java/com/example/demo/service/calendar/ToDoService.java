package com.example.demo.service.calendar;

import com.example.demo.domain.calendar.MyDate;
import com.example.demo.domain.calendar.ToDo;
import com.example.demo.domain.calendar.ToDoStatus;
import com.example.demo.repository.calendar.ToDoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
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

    public List<ToDo> findByDate(int year, int month, int day) {
        MyDate myDate = MyDate.builder()
                        .year(year)
                        .month(month)
                        .day(day).build();
        return toDoRepository.findByDate(myDate);
    }

    @Transactional
    public void remove(Long toDoId){
        toDoRepository.remove(toDoId);
    }

    @Transactional
    public void makeCompleted(Long toDoId){
        ToDo toDo = findOne(toDoId);
        toDo.setStatus(ToDoStatus.COMPLETED);
    }

    @Transactional
    public void makeActive(Long toDoId){
        ToDo toDo = findOne(toDoId);
        toDo.setStatus(ToDoStatus.ACTIVE);
    }
}
