package com.suhsein.ownspace.service.calendar;

import lombok.Getter;
import lombok.Setter;

/**
 * 캘린더의 상단의 날짜를 받아옴
 */
@Getter @Setter
public class YearMonthDto {
    private Integer year;
    private Integer month;
}
