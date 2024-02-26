package com.example.demo.service.daily;

import com.example.demo.controller.daily.CommentDto;
import com.example.demo.domain.daily.Comment;
import com.example.demo.domain.daily.CommentStatus;
import com.example.demo.domain.daily.Daily;
import com.example.demo.repository.daily.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
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
}
