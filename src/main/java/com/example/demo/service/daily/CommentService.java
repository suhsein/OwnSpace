package com.example.demo.service.daily;

import com.example.demo.domain.daily.Comment;
import com.example.demo.domain.daily.Daily;
import com.example.demo.repository.daily.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    @Transactional
    public void save(Daily daily, Comment comment) {
        comment.setDaily(daily);
        daily.getCommentList().add(comment);
        commentRepository.save(comment);
    }

    public Optional<Comment> findOne(Long commentId){
        return commentRepository.findById(commentId);
    }

    @Transactional
    public void remove(Long commentId){
        commentRepository.delete(findOne(commentId).get());
    }
}
