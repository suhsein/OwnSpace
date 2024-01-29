package com.example.demo.domain.calendar;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * 캘린더의 상단의 날짜를 받아옴
 */
@Getter
@Setter
@RequiredArgsConstructor
public class YearMonthDto {
    private Integer year;
    private Integer month;
}
