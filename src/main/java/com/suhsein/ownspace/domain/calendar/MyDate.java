package com.suhsein.ownspace.domain.calendar;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter @Setter
@NoArgsConstructor
public class MyDate {
    private Integer year;
    private Integer month;
    private Integer day;

    @Builder
    public MyDate(Integer year, Integer month, Integer day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
}
