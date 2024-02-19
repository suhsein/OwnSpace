package com.example.demo.repository.daily;

import com.example.demo.controller.daily.DailyDto;
import com.example.demo.domain.daily.Daily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface DailyRepository extends JpaRepository<Daily, Long> {
    @Modifying
    @Query("update Daily d set d.title = :#{#form.title}, d.content = :#{#form.content} where d.id = :id")
    int updateDailyById(@Param("id") Long id, @Param("form") DailyDto form);
}
