package com.example.demo.service.calendar;

import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class ConvertTimeService {
    public LocalTime convertTime(String timeStr){
        int idx = timeStr.indexOf(":");
        int hour = Integer.parseInt(timeStr.substring(0, idx));
        int minute = Integer.parseInt(timeStr.substring(idx+1));
        return LocalTime.of(hour, minute);
    }
}
