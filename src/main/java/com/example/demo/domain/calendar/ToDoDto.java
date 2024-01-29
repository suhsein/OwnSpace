package com.example.demo.domain.calendar;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

/**
 * 일정 상태
 * => active
 * => completed
 * => deleted
 */
@Getter
@Setter
@RequiredArgsConstructor
public class ToDoDto {
    private Long id;
    private Integer year;
    private Integer month;
    private Integer day;
    @NotBlank
    private String toDoTitle;
    private String toDoPlace;
    private LocalTime toDoTime;
    private String toDoDesc;
    private String status;
}
