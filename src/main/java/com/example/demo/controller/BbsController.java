package com.example.demo.controller;

import com.example.demo.domain.calendar.CalendarService;
import com.example.demo.domain.calendar.DateDto;
import com.example.demo.domain.calendar.WeekDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Calendar;
import java.util.List;

@Slf4j
@Controller
public class BbsController {
    @GetMapping("/map")
    public String map(){
        return "/map";
    }

    @GetMapping("/calendar")
    public String calendar(@ModelAttribute("date") DateDto date, Model model) {
        // 네비게이션의 년, 월을 받아오는 것으로 수정하기 -> 없으면 현재 년, 월로 Setting
        Calendar cal = Calendar.getInstance();
        Integer year = date.getYear();
        Integer month = date.getMonth();

        if (year == null && month == null) {
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH) + 1;
            date.setYear(year);
            date.setMonth(month);
        }

        // 달력 날짜 받아오기
        List<WeekDto> weeks = CalendarService.makeCalendar(year, month);
        model.addAttribute("weeks", weeks);

        return "/calendar";
    }

    @PostMapping("/calendar")
    public String prvNxtCalendar(@ModelAttribute("date") DateDto date,
                                 @RequestParam("action") String action,
                                 RedirectAttributes redirectAttributes) {

        if (action.equals("prv")) {
            DateDto prvDate = CalendarService.getPrvMonth(date);
            date.setYear(prvDate.getYear());
            date.setMonth(prvDate.getMonth());
        } else if (action.equals("nxt")) {
            DateDto nxtDate = CalendarService.getNxtMonth(date);
            date.setYear(nxtDate.getYear());
            date.setMonth(nxtDate.getMonth());
        }
        redirectAttributes.addFlashAttribute("date", date);

        return "redirect:/calendar";
    }
}
