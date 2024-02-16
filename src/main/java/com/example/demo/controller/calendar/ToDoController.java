package com.example.demo.controller.calendar;

import com.example.demo.domain.calendar.MyDate;
import com.example.demo.domain.calendar.ToDo;
import com.example.demo.domain.calendar.ToDoDto;
import com.example.demo.domain.calendar.ToDoStatus;
import com.example.demo.service.calendar.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/toDo/{year}/{month}/{day}")
public class ToDoController {
    private final ToDoService toDoService;
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
        MyDate myDate = MyDate.builder()
                .year(year)
                .month(month)
                .day(day).build();

        ToDo toDoResult = ToDo.builder()
                .myDate(myDate)
                .status(ToDoStatus.ACTIVE)
                .title(toDo.getTitle())
                .place(toDo.getPlace())
                .time(toDo.getTime())
                .description(toDo.getDescription())
                .build();

        toDoService.save(toDoResult);
        return "redirect:/toDo/{year}/{month}/{day}/list";
    }

    @GetMapping("/list")
    public String toDoList(@PathVariable("year") Integer year,
                           @PathVariable("month") Integer month,
                           @PathVariable("day") Integer day,
                           Model model){
        MyDate myDate = MyDate.builder()
                .year(year)
                .month(month)
                .day(day).build();

        List<ToDo> toDoList = toDoService.findByDate(myDate);
        model.addAttribute("toDoList", toDoList);
        return "/calendar/to-do-list";
    }

    @GetMapping("/delete/{id}")
    public String toDoDelete(@PathVariable("year") Integer year,
                             @PathVariable("month") Integer month,
                             @PathVariable("day") Integer day,
                             @PathVariable("id") Long id) {

        toDoService.remove(id);
        return "redirect:/toDo/{year}/{month}/{day}/list";
    }

    @GetMapping("/complete/{id}")
    public String toDoComplete(@PathVariable("year") Integer year,
                               @PathVariable("month") Integer month,
                               @PathVariable("day") Integer day,
                               @PathVariable("id") Long id){
        ToDo toDo = toDoService.findOne(id);
        toDo.setStatus(ToDoStatus.COMPLETED);
        return "redirect:/toDo/{year}/{month}/{day}/list";
    }

    @GetMapping("/active/{id}")
    public String toDoActive(@PathVariable("year") Integer year,
                               @PathVariable("month") Integer month,
                               @PathVariable("day") Integer day,
                               @PathVariable("id") Long id){
        ToDo toDo = toDoService.findOne(id);
        toDo.setStatus(ToDoStatus.ACTIVE);
        return "redirect:/toDo/{year}/{month}/{day}/list";
    }

}
