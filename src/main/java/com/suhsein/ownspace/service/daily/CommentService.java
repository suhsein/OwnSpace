package com.suhsein.ownspace.service.daily;

import com.suhsein.ownspace.controller.daily.dto.CommentDto;
import com.suhsein.ownspace.domain.daily.Comment;
import com.suhsein.ownspace.domain.daily.CommentStatus;
import com.suhsein.ownspace.domain.daily.Daily;
import com.suhsein.ownspace.domain.members.Member;
import com.suhsein.ownspace.repository.daily.CommentRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    @Transactional
    public void save(Daily daily, Comment comment) {
        commentRepository.save(comment);
        daily.addComment(comment);
    }

    @Transactional
    public void save(Daily daily, Comment comment, Comment reply) {
        commentRepository.save(reply);
        daily.addComment(reply);
        comment.addChildComment(reply);
    }

    public Optional<Comment> findOne(Long commentId){
        return commentRepository.findById(commentId);
    }

    public List<Comment> findRootComments(Long dailyId){
        return commentRepository.findRootComments(dailyId);
    }
    @Transactional
    public void editComment(Long commentId, CommentDto form, LocalDateTime updateDate){
        commentRepository.updateById(commentId, form, updateDate);
    }

    @Transactional
    public void remove(Long commentId){
        Comment comment = findOne(commentId).get();
        if(comment.getChildList().isEmpty()){
            commentRepository.delete(findDeletableAncestorComment(comment));
        } else{
            comment.setStatus(CommentStatus.DELETE);
        }
    }

    private Comment findDeletableAncestorComment(Comment comment){
        Comment parent = comment.getParent();
        if (parent != null && parent.getChildList().size() == 1 && parent.getStatus() == CommentStatus.DELETE) {
            return findDeletableAncestorComment(parent);
        }
        return comment;
        // 삭제할 수 있는 가장 상위의 댓글을 찾는다. orphanRemoval = true 이므로 상위 댓글 삭제 시, 자손 댓글들도 모두 삭제됨
        // 없다면 자기 자신을 반환
    }

    /**
     * Authentication method
     */
    public boolean authenticate(HttpServletRequest request, Model model, Comment comment) {
        HttpSession session = request.getSession();
        Member loginMember = (Member)session.getAttribute("loginMember");

        if (loginMember == null || loginMember.getId() != comment.getWriter().getId()) {
            model.addAttribute("msg", "접근할 수 없는 페이지입니다.");
            return false;
        }
        return true;
    }
}
