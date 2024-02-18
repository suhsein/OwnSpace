package com.example.demo.domain.calendar;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
public class ToDoDto {
    private Integer year;
    private Integer month;
    private Integer day;
    private String title;
    private String place;
    private String time;
    private String description;
    private String status;
}
