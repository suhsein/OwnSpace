package com.example.demo.domain.calendar;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 캘린더 틀에 넣을 날짜 데이터를 저장
 */
@Service
@RequiredArgsConstructor
public class CalendarService {
    private final ToDoRepository toDoRepository;

    public List<WeekDto> makeCalendar(int year, int month){
        LocalDate curDate = LocalDate.of(year, month, 1); // 해당 달력
        int day = curDate.getDayOfWeek().getValue() % 7; // curDate 의 1일에 해당하는 요일, sunday=0 ~ saturday=6
        int lengthOfMonth = curDate.lengthOfMonth();

        int cnt = 0;
        List<WeekDto> weeks = new ArrayList<>();
        List<WeekDayDto> weekDate = new ArrayList<>();

        for (int i = 0 ; i < day; i++, cnt++) {
            weekDate.add(new WeekDayDto(0, 0));
        }
        for (int i = 1; i <= lengthOfMonth; i++, cnt++) {
            weekDate.add(new WeekDayDto(i, toDoRepository.findByDate(year, month, i).size()));
            addWeek(weeks, weekDate);
        }
        int calSize = (day ==0 && lengthOfMonth == 28) ? 28 : 35;
        int lastDays =  calSize - cnt;

        for (int i = 0; i < lastDays; i++) {
            weekDate.add(new WeekDayDto(0, 0));
            addWeek(weeks, weekDate);
        }

        return weeks;
    }

    public static YearMonthDto getPrvMonth(YearMonthDto date) {
        Integer year = date.getYear();
        Integer month = date.getMonth();

        if (month == 1) {
            date.setYear(year - 1);
            date.setMonth(12);
        } else{
            date.setMonth(month - 1);
        }
        return date;
    }

    public static YearMonthDto getNxtMonth(YearMonthDto date){
        Integer year = date.getYear();
        Integer month = date.getMonth();

        if (month == 12) {
            date.setYear(year + 1);
            date.setMonth(1);
        } else{
            date.setMonth(month + 1);
        }
        return date;
    }

    private static void addWeek(List<WeekDto> weeks, List<WeekDayDto> weekDate) {
        if(weekDate.size() == 7) {
            List<WeekDayDto> copy = new ArrayList<>();
            copy.addAll(weekDate);
            WeekDto week = new WeekDto();
            week.setWeek(copy);
            weeks.add(week);
            weekDate.clear();
        }
    }
}
