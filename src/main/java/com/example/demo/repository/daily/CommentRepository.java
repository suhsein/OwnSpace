package com.example.demo.repository.daily;

import com.example.demo.controller.daily.CommentDto;
import com.example.demo.domain.daily.Comment;
import com.example.demo.domain.daily.CommentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Modifying
    @Query("update Comment c set c.content = :#{#form.content}, c.updateDate = :#{#updateDate} where c.id = :id")
    int updateById(@Param("id") Long id,
                   @Param("form")CommentDto form,
                   @Param("updateDate")LocalDateTime updateDate);

    @Query("select c from Comment c join c.daily d where d.id = :dailyId and c.parent is null order by c.id")
    List<Comment> findRootComments(@Param("dailyId") Long dailyId);

    Long countByDailyIdAndStatus(Long dailyId, CommentStatus status);
}
