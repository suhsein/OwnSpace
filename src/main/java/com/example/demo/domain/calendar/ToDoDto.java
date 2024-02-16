package com.example.demo.domain.calendar;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@RequiredArgsConstructor
public class ToDoDto {
    private Integer year;
    private Integer month;
    private Integer day;
    private String title;
    private String place;
    private LocalDate time;
    private String description;
    private String status;
}
