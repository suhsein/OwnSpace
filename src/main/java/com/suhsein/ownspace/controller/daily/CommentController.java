package com.suhsein.ownspace.controller.daily;

import com.suhsein.ownspace.controller.daily.dto.CommentDto;
import com.suhsein.ownspace.domain.daily.Comment;
import com.suhsein.ownspace.domain.daily.CommentStatus;
import com.suhsein.ownspace.domain.daily.Daily;
import com.suhsein.ownspace.domain.members.Member;
import com.suhsein.ownspace.service.daily.CommentService;
import com.suhsein.ownspace.service.daily.DailyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/daily")
@RequiredArgsConstructor
public class CommentController {
    private final DailyService dailyService;
    private final CommentService commentService;

    /**
     * CREATE
     */
    @PostMapping("/addComment/{id}")
    public String createComment(@PathVariable("id") Long id,
                                @ModelAttribute("commentForm") CommentDto commentDto,
                                HttpServletRequest request) {

        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute("loginMember");

        Comment comment = Comment.builder()
                .writer(loginMember)
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
                .writer(loginMember)
                .createDate(LocalDateTime.now())
                .content(commentDto.getContent())
                .status(CommentStatus.ACTIVE).build();

        commentService.save(daily, comment, reply);
        dailyService.increaseComment(id);
        return "redirect:/daily/dailyView/{id}";
    }

    /**
     * UPDATE
     */
    @GetMapping("/editComment/{id}/{comment_id}")
    public String editCommentForm(@PathVariable("id") Long id,
                                  @PathVariable("comment_id") Long commentId,
                                  @ModelAttribute("commentForm") CommentDto form,
                                  HttpServletRequest request,
                                  Model model) {
        Comment comment = commentService.findOne(commentId).get();
        if (!commentService.authenticate(request, model, comment)) {
            return "alert/back";
        }

        form.setContent(comment.getContent());

        setDailyViewModel(id, model);
        return "daily/edit-comment";
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

    @PostMapping("/editComment/{id}/{comment_id}")
    public String editComment(@PathVariable("id") Long id,
                              @PathVariable("comment_id") Long commentId,
                              @ModelAttribute("commentForm") CommentDto form) {
        commentService.editComment(commentId, form, LocalDateTime.now());
        return "redirect:/daily/dailyView/{id}";
    }

    /**
     * DELETE
     */
    @GetMapping("/deleteComment/{id}/{comment_id}")
    public String deleteComment(@PathVariable("id") Long id,
                                @PathVariable("comment_id") Long commentId,
                                HttpServletRequest request,
                                Model model) {
        if (!commentService.authenticate(request, model, commentService.findOne(commentId).get())) {
            return "alert/back";
        }
        commentService.remove(commentId);
        dailyService.decreaseComment(id);
        return "redirect:/daily/dailyView/{id}";
    }
}
