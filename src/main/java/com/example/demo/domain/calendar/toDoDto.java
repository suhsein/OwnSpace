package com.example.demo.domain.calendar;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@RequiredArgsConstructor
public class toDoDto {
    private Long id;
    private Integer year;
    private Integer month;
    private Integer day;
    private String toDoTitle;
    private String toDoPlace;
    private LocalTime toDoTime;
    private String toDoDesc;
}
