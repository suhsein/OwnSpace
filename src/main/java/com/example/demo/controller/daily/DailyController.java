package com.example.demo.controller.daily;

import com.example.demo.domain.daily.Daily;
import com.example.demo.domain.members.Member;
import com.example.demo.service.daily.DailyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/daily")
@RequiredArgsConstructor
public class DailyController {
    private final DailyService dailyService;

    @GetMapping
    public String daily(@PageableDefault(size = 3, sort="id",
                        direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) { // paging id 기준 내림차 정렬(default 오름차)
        Page<Daily> dailyPages = dailyService.paging(pageable);
        model.addAttribute("dailyPages", dailyPages);
        return "daily/daily";
    }

    @GetMapping("/addPost")
    public String createPostForm(@ModelAttribute("form") DailyDto form) {
        return "daily/add-post";
    }

    @PostMapping("/addPost")
    public String createPost(@Validated @ModelAttribute("form") DailyDto form,
                             BindingResult bindingResult,
                             HttpServletRequest request) {
        if (!StringUtils.hasText(form.getTitle())) {
            bindingResult.reject("noTitle");
            return "/daily/add-post";
        }
        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute("loginMember");

        Daily daily = Daily.builder()
                .writer(loginMember == null ? "익명" : loginMember.getUsername())
                .title(form.getTitle())
                .content(form.getContent())
                .createDate(LocalDateTime.now())
                .views(1L)
                .build();

        dailyService.save(daily);

        return "redirect:/daily";
    }

    @GetMapping("/dailyView/{id}")
    public String dailyView(@PathVariable("id") Long id, Model model){
        dailyService.increaseView(id);
        Daily daily = dailyService.findOne(id).get();
        daily.setContent(daily.getContent().replace("\r\n", "<br>"));
        // 출력 시에 \r\n을 <br>로 바꿔서 출력. 타임리프 unescaped text 로 출력
        model.addAttribute("daily", daily);
        return "daily/daily-view";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id,
                       @ModelAttribute("form") DailyDto form) {
        Daily daily = dailyService.findOne(id).get();
        form.setTitle(daily.getTitle());
        form.setContent(daily.getContent());
        return "daily/edit-post";
    }

    @PostMapping("/edit/{id}")
    public String editForm(@Validated @ModelAttribute("form") DailyDto form,
                           BindingResult bindingResult,
                           @PathVariable("id") Long id,
                           RedirectAttributes redirectAttributes){
        if (!StringUtils.hasText(form.getTitle())) {
            bindingResult.reject("noTitle");
            return "/daily/edit-post";
        }

        dailyService.editDaily(id, form);
        Daily daily = dailyService.findOne(id).get();

        redirectAttributes.addFlashAttribute("daily", daily);
        return "redirect:/daily/dailyView/{id}";
    }

    @GetMapping("/delete/{id}")
    public String deleteDaily(@PathVariable("id") Long id) {
        dailyService.remove(id);
        return "redirect:/daily";
    }
}
