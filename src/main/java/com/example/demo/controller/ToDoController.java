package com.example.demo.controller;

import com.example.demo.domain.calendar.ToDoDto;
import com.example.demo.domain.calendar.ToDoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/toDo/{year}/{month}/{day}")
public class ToDoController {
    private final ToDoRepository toDoRepository;
    @GetMapping
    public String toDo(@PathVariable("year") Integer year,
                       @PathVariable("month") Integer month,
                       @PathVariable("day") Integer day,
                       @ModelAttribute("toDo") ToDoDto toDo){
        return "/calendar/to-do";
    }

    @PostMapping
    public String toDoAdd(@Validated @ModelAttribute("toDo") ToDoDto toDo,
                          BindingResult bindingResult,
                          @PathVariable("year") Integer year,
                          @PathVariable("month") Integer month,
                          @PathVariable("day") Integer day) {
        if(bindingResult.hasErrors()){
            return "/calendar/to-do";
        }
        toDo.setStatus("active"); // 일정 상태 -> active
        toDoRepository.save(toDo);
        return "redirect:/toDo/{year}/{month}/{day}/list";
    }

    @GetMapping("/list")
    public String toDoList(@PathVariable("year") Integer year,
                           @PathVariable("month") Integer month,
                           @PathVariable("day") Integer day,
                           Model model){
        List<ToDoDto> toDoList = toDoRepository.findByDate(year, month, day);
        model.addAttribute("toDoList", toDoList);
        return "/calendar/to-do-list";
    }

    @GetMapping("/delete/{id}")
    public String toDoDelete(@PathVariable("year") Integer year,
                             @PathVariable("month") Integer month,
                             @PathVariable("day") Integer day,
                             @PathVariable("id") Long id){
        ToDoDto toDo = toDoRepository.findById(id);
        toDo.setStatus("deleted");
        return "redirect:/toDo/{year}/{month}/{day}/list";
    }

    @GetMapping("/complete/{id}")
    public String toDoComplete(@PathVariable("year") Integer year,
                               @PathVariable("month") Integer month,
                               @PathVariable("day") Integer day,
                               @PathVariable("id") Long id){
        ToDoDto toDo = toDoRepository.findById(id);
        toDo.setStatus("completed");
        return "redirect:/toDo/{year}/{month}/{day}/list";
    }

    @GetMapping("/active/{id}")
    public String toDoActive(@PathVariable("year") Integer year,
                               @PathVariable("month") Integer month,
                               @PathVariable("day") Integer day,
                               @PathVariable("id") Long id){
        ToDoDto toDo = toDoRepository.findById(id);
        toDo.setStatus("active");
        return "redirect:/toDo/{year}/{month}/{day}/list";
    }

}
