package com.example.demo.repository.calendar;

import com.example.demo.domain.calendar.MyDate;
import com.example.demo.domain.calendar.ToDo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ToDoRepository {
    private final EntityManager em;
    public void save(ToDo toDo){
        em.persist(toDo);
    }
    public ToDo findOne(Long id){
        return em.find(ToDo.class, id);
    }

    public List<ToDo> findAll(){
        return em.createQuery("select t from ToDo t", ToDo.class)
                .getResultList();
    }

    public List<ToDo> findByDate(MyDate myDate){
        return em.createQuery("select t from ToDo t where t.myDate.year = :year and t.myDate.month = :month and t.myDate.day = :day", ToDo.class)
                .setParameter("year", myDate.getYear())
                .setParameter("month", myDate.getMonth())
                .setParameter("day", myDate.getDay())
                .getResultList();
    }

    public void remove(Long id){
        em.remove(findOne(id));
    }
}
