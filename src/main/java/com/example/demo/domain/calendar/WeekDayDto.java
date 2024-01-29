package com.example.demo.domain.calendar;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class WeekDayDto {
    Integer day;
    Integer toDoSize;

    public WeekDayDto(Integer day, Integer toDoSize) {
        this.day = day;
        this.toDoSize = toDoSize;
    }
}
