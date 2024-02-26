package com.suhsein.ownspace.service.calendar;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WeekDayDto {
    Integer day;
    Integer toDoSize;

    public WeekDayDto(Integer day, Integer toDoSize) {
        this.day = day;
        this.toDoSize = toDoSize;
    }
}
