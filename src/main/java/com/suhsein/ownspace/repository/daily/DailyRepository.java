package com.suhsein.ownspace.repository.daily;

import com.suhsein.ownspace.controller.daily.DailyDto;
import com.suhsein.ownspace.domain.daily.Daily;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface DailyRepository extends JpaRepository<Daily, Long> {
    @Modifying
    @Query("update Daily d set d.title = :#{#form.title}, d.content = :#{#form.content}, d.updateDate = :#{#updateDate} where d.id = :id")
    int updateDailyById(@Param("id") Long id,
                        @Param("form") DailyDto form,
                        @Param("updateDate") LocalDateTime updateDate);

    @Query(value = "select * from daily where daily_id = " +
            "(select prev_id from " +
            "(select daily_id, lag(daily_id) over(order by daily_id) as prev_id from daily) " +
            "find_prev_id where daily_id = :id)", nativeQuery = true)
    Daily findPrevDaily(@Param("id") Long id);

    @Query(value = "select * from daily where daily_id = " +
            "(select nxt_id from " +
            "(select daily_id, lead(daily_id) over(order by daily_id) as nxt_id from daily) " +
            "find_nxt_id where daily_id = :id)", nativeQuery = true)
    Daily findNxtDaily(@Param("id") Long id);

    @Query("select d from Daily d where d.title like concat('%', :keyword, '%') or d.content like concat('%', :keyword, '%')")
    Page<Daily> findAllByTitleAndContent(@Param("pageable") Pageable pageable, @Param("keyword") String keyword);
    @Query("select d from Daily d where d.title like concat('%', :keyword, '%')")
    Page<Daily> findAllByTitle(@Param("pageable") Pageable pageable, @Param("keyword") String keyword);
    @Query("select d from Daily d where d.content like concat('%', :keyword, '%')")
    Page<Daily> findAllByContent(@Param("pageable") Pageable pageable, @Param("keyword") String keyword);
    @Query("select d from Daily d where d.writer.username like concat('%', :keyword, '%')")
    Page<Daily> findAllByWriter(@Param("pageable") Pageable pageable, @Param("keyword") String keyword);
}
