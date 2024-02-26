package com.example.demo.controller.daily;

import com.example.demo.domain.daily.*;
import com.example.demo.domain.members.Member;
import com.example.demo.service.daily.CommentService;
import com.example.demo.service.daily.DailyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class DailyController {
    private final DailyService dailyService;
    private final CommentService commentService;
    private final SearchCodes searchCodes;

    @GetMapping
    public String daily(@PageableDefault(size = 3, sort="id",
                        direction = Sort.Direction.DESC) Pageable pageable,
                        @ModelAttribute("dailySearch") DailySearchDto dailySearch,
                        Model model) { // paging id 기준 내림차 정렬(default 오름차)
        Page<Daily> dailyPages = dailyService.paging(pageable);
        model.addAttribute("dailyPages", dailyPages);
        model.addAttribute("searchCodes", searchCodes.getSearchCodes());

        return "daily/daily";
    }

    @PostMapping("/search")
    public String search(@ModelAttribute("dailySearch") DailySearchDto dailySearch,
                         Model model) {

        return "daily/daily-search";
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
                .writer(loginMember == null ? null : loginMember)
                .title(form.getTitle())
                .content(form.getContent())
                .createDate(LocalDateTime.now())
                .build();

        dailyService.save(daily);

        return "redirect:/daily";
    }

    @GetMapping("/dailyView/{id}")
    public String dailyView(@PathVariable("id") Long id,
                            @ModelAttribute("commentForm") CommentDto commentDto,
                            Model model){
        dailyService.increaseView(id);
        setDailyViewModel(id, model);
        return "daily/daily-view";
    }

    @PostMapping("/addComment/{id}")
    public String createComment(@PathVariable("id") Long id,
                               @ModelAttribute("commentForm") CommentDto commentDto,
                               HttpServletRequest request) {

        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute("loginMember");

        Comment comment = Comment.builder()
                .writer(loginMember == null ? null : loginMember)
                .createDate(LocalDateTime.now())
                .content(commentDto.getContent())
                .status(CommentStatus.ACTIVE).build();

        Daily daily = dailyService.findOne(id).get();
        commentService.save(daily, comment);
        dailyService.increaseComment(id);

        return "redirect:/daily/dailyView/{id}";
    }

    @PostMapping("/addReply/{id}/{comment_id}")
    public String createReply(@PathVariable("id") Long id,
                              @PathVariable("comment_id") Long commentId,
                              @ModelAttribute("commentForm") CommentDto commentDto,
                              HttpServletRequest request) {
        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute("loginMember");

        Daily daily = dailyService.findOne(id).get();
        Comment comment = commentService.findOne(commentId).get();

        Comment reply = Comment.builder()
                .writer(loginMember == null ? null : loginMember)
                .createDate(LocalDateTime.now())
                .content(commentDto.getContent())
                .status(CommentStatus.ACTIVE).build();

        commentService.save(daily, comment, reply);
        dailyService.increaseComment(id);
        return "redirect:/daily/dailyView/{id}";
    }

    @GetMapping("/deleteComment/{id}/{comment_id}")
    public String deleteComment(@PathVariable("id") Long id,
                                @PathVariable("comment_id") Long commentId) {
        commentService.remove(commentId);
        dailyService.decreaseComment(id);
        return "redirect:/daily/dailyView/{id}";
    }

    @GetMapping("/editComment/{id}/{comment_id}")
    public String editCommentForm(@PathVariable("id") Long id,
                              @PathVariable("comment_id") Long commentId,
                              @ModelAttribute("commentForm") CommentDto form,
                              Model model){
        Comment comment = commentService.findOne(commentId).get();
        form.setContent(comment.getContent());

        setDailyViewModel(id, model);
        return "daily/edit-comment";
    }

    @PostMapping("/editComment/{id}/{comment_id}")
    public String editComment(@PathVariable("id") Long id,
                              @PathVariable("comment_id") Long commentId,
                              @ModelAttribute("commentForm") CommentDto form){
        commentService.editComment(commentId, form, LocalDateTime.now());
        return "redirect:/daily/dailyView/{id}";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id,
                       @ModelAttribute("form") DailyDto form) {
        Daily daily = dailyService.findOne(id).get();
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

    @GetMapping("/delete/{id}")
    public String deleteDaily(@PathVariable("id") Long id) {
        dailyService.remove(id);
        return "redirect:/daily";
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
}
