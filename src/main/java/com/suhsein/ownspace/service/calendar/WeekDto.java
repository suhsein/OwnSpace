package com.suhsein.ownspace.service.calendar;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class WeekDto {
    List<WeekDayDto> week = new ArrayList<>();
}
