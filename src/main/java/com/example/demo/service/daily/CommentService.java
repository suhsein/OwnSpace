package com.example.demo.service.daily;

import com.example.demo.controller.daily.CommentDto;
import com.example.demo.domain.daily.Comment;
import com.example.demo.domain.daily.Daily;
import com.example.demo.repository.daily.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        commentRepository.delete(findOne(commentId).get());
    }
}
