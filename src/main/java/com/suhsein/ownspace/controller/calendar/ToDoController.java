package com.suhsein.ownspace.controller.calendar;

import com.suhsein.ownspace.domain.calendar.MyDate;
import com.suhsein.ownspace.domain.calendar.ToDo;
import com.suhsein.ownspace.domain.calendar.ToDoStatus;
import com.suhsein.ownspace.service.calendar.ConvertTimeService;
import com.suhsein.ownspace.service.calendar.ToDoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/toDo/{year}/{month}/{day}")
@Slf4j
public class ToDoController {
    private final ToDoService toDoService;
    private final ConvertTimeService convertTimeService;
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
        log.info("time ={}", toDo.getTime());

        ToDo toDoResult = ToDo.builder()
                .myDate(myDate)
                .status(ToDoStatus.ACTIVE)
                .title(toDo.getTitle())
                .place(toDo.getPlace())
                .time(StringUtils.hasText(toDo.getTime()) ? convertTimeService.convertTime(toDo.getTime()) : null)
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
        List<ToDo> toDoList = toDoService.findByDate(year, month, day);
        model.addAttribute("toDoList", toDoList);
        return "/calendar/to-do-list";
    }

    @GetMapping("/delete/{id}")
    public String toDoDelete(@PathVariable("id") Long id) {
        toDoService.remove(id);
        return "redirect:/toDo/{year}/{month}/{day}/list";
    }

    @GetMapping("/completed/{id}")
    public String toDoComplete(@PathVariable("id") Long id){
        toDoService.makeCompleted(id);
        return "redirect:/toDo/{year}/{month}/{day}/list";
    }

    @GetMapping("/active/{id}")
    public String toDoActive(@PathVariable("id") Long id){
        toDoService.makeActive(id);
        return "redirect:/toDo/{year}/{month}/{day}/list";
    }

}
