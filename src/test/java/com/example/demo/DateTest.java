package com.example.demo;

import com.example.demo.service.calendar.WeekDayDto;
import com.example.demo.service.calendar.WeekDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Slf4j
public class DateTest {
    @Test
    public void getDate(){
        Calendar cal = Calendar.getInstance();
        int year= cal.get(Calendar.YEAR) - 1;
        int month = cal.get(Calendar.MONTH) + 2; // 0부터 시작하므로 +1

        log.info("year={}, month={}", year, month);

        LocalDate curDate = LocalDate.of(year, month, 1); // 해당 달력
        int day = curDate.getDayOfWeek().getValue() % 7; // curDate 의 1일에 해당하는 요일 => 일요일 = 0 ~ 토요일 = 6

        log.info("first day of the month={}", day);
        int lengthOfMonth = curDate.lengthOfMonth();

        int cnt = 0;
        List<WeekDto> weeks = new ArrayList<>();
        List<WeekDayDto> weekDate = new ArrayList<>();

        for (int i = 0 ; i < day; i++, cnt++) {
            weekDate.add(null);
        }
        for (int i = 1; i <= lengthOfMonth; i++, cnt++) {
            weekDate.add(new WeekDayDto(i, i));
            if (weekDate.size() == 7) {
                List<WeekDayDto> copy = new ArrayList<>();
                copy.addAll(weekDate);
                WeekDto week = new WeekDto();
                week.setWeek(copy);
                weeks.add(week);
                weekDate.clear();
            }
        }

        int calSize = (day ==0 && lengthOfMonth == 28) ? 28 : 35;
        int lastDays =  calSize - cnt;

        for (int i = 0; i < lastDays; i++) {
            weekDate.add(null);
            if(weekDate.size() == 7) {
                List<WeekDayDto> copy = new ArrayList<>();
                copy.addAll(weekDate);
                WeekDto week = new WeekDto();
                week.setWeek(copy);
                weeks.add(week);
                weekDate.clear();
            }
        }
        for (WeekDto week : weeks) {
            for (WeekDayDto weekDay : week.getWeek()) {
                if(weekDay != null) {
                    log.info("weekDay={}, {}", weekDay.getDay(), weekDay.getToDoSize());
                }
            }
        }
    }
}
