package com.example.demo.domain.calendar;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class WeekDto {
    List<WeekDayDto> week = new ArrayList<>();
}
