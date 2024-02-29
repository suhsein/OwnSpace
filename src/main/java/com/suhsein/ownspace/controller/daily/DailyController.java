package com.suhsein.ownspace.controller.daily;

import com.suhsein.ownspace.service.CheckLogin;
import com.suhsein.ownspace.controller.daily.dto.CommentDto;
import com.suhsein.ownspace.controller.daily.dto.DailyDto;
import com.suhsein.ownspace.controller.daily.dto.DailySearchDto;
import com.suhsein.ownspace.domain.daily.*;
import com.suhsein.ownspace.domain.members.Member;
import com.suhsein.ownspace.service.daily.CommentService;
import com.suhsein.ownspace.service.daily.DailyService;
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

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/daily")
@RequiredArgsConstructor
public class DailyController {
    private final DailyService dailyService;
    private final CommentService commentService;
    private final SearchCodes searchCodes;

    /**
     * READ
     */
    @GetMapping
    public String daily(@PageableDefault(size = 3, sort="id",
                        direction = Sort.Direction.DESC) Pageable pageable,
                        @ModelAttribute("dailySearch") DailySearchDto dailySearch,
                        Model model) { // paging id 기준 내림차 정렬(default 오름차)
        Page<Daily> dailyPages = dailyService.paging(pageable);
        model.addAttribute("dailyPages", dailyPages);
        model.addAttribute("searchCodes", searchCodes.getSearchCodes());
        model.addAttribute("onSearch", false);

        return "daily/daily";
    }

    @GetMapping("/dailyView/{id}")
    public String dailyView(@PathVariable("id") Long id,
                            @ModelAttribute("commentForm") CommentDto commentDto,
                            @RequestParam(value = "code", required = false) SearchCodeName code,
                            @RequestParam(value = "keyword", required = false) String keyword,
                            Model model){
        dailyService.increaseView(id);
        setDailyViewModel(id, model);
        addSearchAttributes(code, keyword, model);
        return "daily/daily-view";
    }

    private void addSearchAttributes(SearchCodeName code, String keyword, Model model) {
        if(code == null && keyword == null){
            model.addAttribute("onSearch", false);
        } else{
            model.addAttribute("keyword", keyword);
            model.addAttribute("code", code);
            model.addAttribute("onSearch", true);
        }
    }

    private void setDailyViewModel(Long id, Model model) {
        Daily daily = dailyService.findOne(id).get();
        model.addAttribute("daily", daily);

        List<Comment> commentList = commentService.findRootComments(daily.getId());

        Daily prevDaily = dailyService.findPrevDaily(id);
        Daily nxtDaily = dailyService.findNxtDaily(id);

        model.addAttribute("commentList", commentList);
        model.addAttribute("prevDaily", prevDaily);
        model.addAttribute("nxtDaily", nxtDaily);
    }

    /**
     * CREATE
     */
    @GetMapping("/addPost")
    public String createPostForm(@ModelAttribute("form") DailyDto form,
                                 HttpServletRequest request,
                                 Model model) {
        if(!CheckLogin.checkLoginRedirect(request, model)){
            return "/alert/redirect";
        }
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
                .writer(loginMember == null ? null : loginMember)
                .title(form.getTitle())
                .content(form.getContent())
                .createDate(LocalDateTime.now())
                .build();

        dailyService.save(daily);

        return "redirect:/daily";
    }

    /**
     * UPDATE
     */
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id,
                       @ModelAttribute("form") DailyDto form,
                       HttpServletRequest request,
                       Model model) {
        Daily daily = dailyService.findOne(id).get();
        if (!dailyService.authenticate(request, model, daily)) {
            return "/alert/back";
        }

        form.setTitle(daily.getTitle());
        form.setContent(daily.getContent());
        return "daily/edit-post";
    }

    @PostMapping("/edit/{id}")
    public String edit(@Validated @ModelAttribute("form") DailyDto form,
                           BindingResult bindingResult,
                           @PathVariable("id") Long id){
        if (!StringUtils.hasText(form.getTitle())) {
            bindingResult.reject("noTitle");
            return "/daily/edit-post";
        }

        dailyService.editDaily(id, form, LocalDateTime.now());
        return "redirect:/daily/dailyView/{id}";
    }

    /**
     * DELETE
     */
    @GetMapping("/delete/{id}")
    public String deleteDaily(@PathVariable("id") Long id,
                              HttpServletRequest request,
                              Model model) {
        if (!dailyService.authenticate(request, model, dailyService.findOne(id).get())) {
            return "/alert/back";
        }
        dailyService.remove(id);
        return "redirect:/daily";
    }

}
