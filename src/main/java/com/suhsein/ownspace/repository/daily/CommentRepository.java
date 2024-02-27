package com.suhsein.ownspace.repository.daily;

import com.suhsein.ownspace.controller.daily.dto.CommentDto;
import com.suhsein.ownspace.domain.daily.Comment;
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

}
